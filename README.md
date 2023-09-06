# 认证和授权
## 授权

1、角色
2、权限
角色和权限在SpringSecurity都是一样的：只不过业务上做了区分
说白了都是我们指定的标识

/admin/user/save：
    角色：admin【拥有admin角色的用户可以访问】
    权限：user：save【拥有user:save权限的用户执行保存操作】

/admin/user/query
    角色：user【拥有user角色的用户可以访问
    权限：user:query【拥有user:query权限的用户可以查询】