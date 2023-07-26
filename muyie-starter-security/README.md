## muyie-starter-security

Spring Boot Security 基于 JWT 的登录认证集成，开箱即用。

- [Spring Security - 官网](https://docs.spring.io/spring-security/reference/5.8/index.html)

## 快速开始

1）在 Spring Boot 项目中添加依赖配置，并开启 @EnableJwtSecurity 配置。

```xml

<dependency>
  <groupId>com.github.zhycn</groupId>
  <artifactId>muyie-starter-security</artifactId>
</dependency>
```

2）添加属性配置参数（可通过 JwtTokenProvider.getJwtSecretKey() 生成一个 secret 或 base64Secret）：

```properties
## JWT Security Configuration
muyie.security.authentication.jwt.secret=
muyie.security.authentication.jwt.base64Secret=
muyie.security.authentication.jwt.tokenValidityInSeconds=1800
muyie.security.authentication.jwt.tokenValidityInSecondsForRememberMe=2592000
```

登录之前，先了解几个重要配置：

1. JwtSecurityConfiguration - 核心配置类，可自定义修改
2. JwtService - 提供登录管理服务
3. TokenCacheManager - 增强令牌保持，可实现该接口。（默认机制下，Token一旦生成，只要不过期，就能一直使用。）
4. JwtSecurityUtils - 可获取登录用户名、token等

3）通过 JwtService 完成登录并获取 Token 令牌。如果需要通过密码登录，则要配置 UserDetailsService 接口：

```
@Bean
public UserDetailsService jwtUserDetailsService() {
  return username -> {
  // 根据用户名查询数据库并返回一个 UserDetails
  return UserDetails
  };
}
```

4）接口请求时上送请求头信息：

```
Authorization: Bearer {TOKEN}
```

注意：API拦截规则，可以查看 JwtSecurityConfiguration 中的定义，也可以轻松自定义一个新的规则。默认会拦截所有 `/api/**` 的请求。
