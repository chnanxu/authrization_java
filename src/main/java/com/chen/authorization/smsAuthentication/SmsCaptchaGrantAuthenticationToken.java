package com.chen.authorization.smsAuthentication;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.*;

public class SmsCaptchaGrantAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 本次登录申请的scope
     * -- GETTER --
     *  返回请求的scope(s)
     *
     */
    @Getter
    private final Set<String> scopes;

    /**
     * 客户端认证信息
     */
    private final Authentication clientPrincipal;

    /**
     * 当前请求的参数
     * -- GETTER --
     *  返回请求中的附加参数
     *
     */
    @Getter
    private final Map<String, Object> additionalParameters;

    /**
     * 认证方式
     * -- GETTER --
     *  返回请求中的authorization grant type
     *
     */
    @Getter
    private final AuthorizationGrantType authorizationGrantType;

    public SmsCaptchaGrantAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                              Authentication clientPrincipal,
                                              @Nullable Set<String> scopes,
                                              @Nullable Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        this.scopes = Collections.unmodifiableSet(
                scopes != null ?
                        new HashSet<>(scopes) :
                        Collections.emptySet());
        this.clientPrincipal = clientPrincipal;
        this.additionalParameters = Collections.unmodifiableMap(
                additionalParameters != null ?
                        new HashMap<>(additionalParameters) :
                        Collections.emptyMap());
        this.authorizationGrantType = authorizationGrantType;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return clientPrincipal;
    }

}