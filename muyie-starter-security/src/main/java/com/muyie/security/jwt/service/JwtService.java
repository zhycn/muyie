package com.muyie.security.jwt.service;

/**
 * @author larry.qi
 * @since 2.7.13
 */
public interface JwtService {

  /**
   * 基于用户名和密码登录（需实现 UserDetailsService 接口）
   *
   * @param username    用户名
   * @param password    密码
   * @param rememberMe  是否记住密码
   * @param authorities 权限
   * @return token
   */
  String login(String username, String password, boolean rememberMe, String... authorities);

  /**
   * 基于用户名和密码登录（自行校验用户信息）
   *
   * @param username    用户名
   * @param rememberMe  是否记住密码
   * @param authorities 权限
   * @return token
   */
  String login(String username, boolean rememberMe, String... authorities);

  /**
   * 明文密码加密处理
   *
   * @param rawPassword 明文密码
   * @return 哈希值
   */
  String getPasswordHash(String rawPassword);

  /**
   * 明文密码加密处理
   *
   * @param rawPassword 明文密码
   * @param salt        盐值
   * @return 哈希值
   */
  String getPasswordHashWithSalt(String rawPassword, String salt);

  /**
   * 明文密码 + 盐值拼接
   *
   * @param rawPassword 明文密码
   * @param salt        盐值
   * @return 明文密码 + 盐值拼接
   */
  String getPasswordWithSalt(String rawPassword, String salt);

}
