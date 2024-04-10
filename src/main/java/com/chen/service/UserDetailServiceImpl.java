package com.chen.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.mapper.Oauth2BasicUserMapper;
import com.chen.mapper.PermMapper;
import com.chen.mapper.ThirdAccountMapper;
import com.chen.mapper.UserMapper;

import com.chen.pojo.Permissions;

import com.chen.pojo.SysAuthority;
import com.chen.pojo.SysRoleAuthority;
import com.chen.pojo.User;

import com.chen.pojo.user.Oauth2ThirdAccount;
import com.chen.pojo.user.Oauth2UserinfoResult;
import com.chen.utils.util.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.chen.utils.util.SecurityConstants.OAUTH_LOGIN_TYPE;
import static com.chen.utils.util.SecurityConstants.TOKEN_UNIQUE_ID;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl extends ServiceImpl<Oauth2BasicUserMapper,User> implements UserDetailService{

    private final UserMapper userMapper;

    private final PermMapper permMapper;

    private final ThirdAccountMapper thirdAccountMapper;

    //返回认证用户信息对象

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userMapper.findByName(username);

        if(user==null){
            throw new UsernameNotFoundException("用户未找到");
        }

        //根据uid查找角色  角色对应权限
        List<String> permissions=permMapper.getAuthority(user.getUid());

        // 通过角色菜单关联表查出对应的菜单
//        List<SysRoleAuthority> roleId = permMapper.getRole(user.getUid());
//        List<Integer> menusId = Optional.ofNullable(roleId).orElse(Collections.emptyList()).stream().map(SysRoleAuthority::getAuthorityId).collect(Collectors.toList());
//        if (ObjectUtils.isEmpty(menusId)) {
//            return user;
//        }

        //权限列表
        List<SimpleGrantedAuthority> permList=permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        user.setAuthorities(permList);

        //为用户赋予权限标识
        return user;
    }

    @Override
    public String saveByThirdAccount(Oauth2ThirdAccount thirdAccount) {
        User basicUser = new User();
        basicUser.setUname(thirdAccount.getName());
        basicUser.setUser_img(thirdAccount.getAvatarUrl());
        basicUser.setDeleted((byte) 0);
        basicUser.setSourceFrom(thirdAccount.getType());
        this.save(basicUser);
        return basicUser.getUid();
    }

    @Override
    public Oauth2UserinfoResult getLoginUserInfo() {
        Oauth2UserinfoResult result = new Oauth2UserinfoResult();

        // 获取当前认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 其它非token方式获取用户信息
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            BeanUtils.copyProperties(authentication.getPrincipal(), result);
            result.setSub(authentication.getName());
            return result;
        }

        // 获取jwt解析内容
        Jwt token = jwtAuthenticationToken.getToken();

        // 获取当前登录类型
        String loginType = token.getClaim(OAUTH_LOGIN_TYPE);
        // 获取用户唯一Id
        String uniqueId = token.getClaimAsString(TOKEN_UNIQUE_ID);
        // 基础用户信息id
        Integer basicUserId = null;

        // 获取Token中的权限列表
        List<String> claimAsStringList = token.getClaimAsStringList(SecurityConstants.AUTHORITIES_KEY);

        // 如果登录类型不为空则代表是三方登录，获取三方用户信息
        if (!ObjectUtils.isEmpty(loginType)) {
            // 根据三方登录类型与三方用户的唯一Id查询用户信息
            LambdaQueryWrapper<Oauth2ThirdAccount> wrapper = Wrappers.lambdaQuery(Oauth2ThirdAccount.class)
                    .eq(Oauth2ThirdAccount::getUniqueId, uniqueId)
                    .eq(Oauth2ThirdAccount::getType, loginType);
            Oauth2ThirdAccount oauth2ThirdAccount = thirdAccountMapper.selectOne(wrapper);
            if (oauth2ThirdAccount != null) {
                basicUserId = oauth2ThirdAccount.getUserId();
                // 复制三方用户信息
                BeanUtils.copyProperties(oauth2ThirdAccount, result);
            }
        } else {
            // 为空则代表是使用当前框架提供的登录接口登录的，转为基础用户信息
            basicUserId = Integer.parseInt(uniqueId);
        }

        if (basicUserId == null) {
            // 如果用户id为空，代表获取三方用户信息失败
            result.setSub(authentication.getName());
            return result;
        }

        // 查询基础用户信息
        User basicUser = this.getById(basicUserId);
        if (basicUser != null) {
            BeanUtils.copyProperties(basicUser, result);
        }

        // 填充权限信息
        if (!ObjectUtils.isEmpty(claimAsStringList)) {
            Set<SimpleGrantedAuthority> authorities = claimAsStringList.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
            // 否则设置为token中获取的
            result.setAuthorities(authorities);
        }

        result.setSub(authentication.getName());
        return result;
    }

}
