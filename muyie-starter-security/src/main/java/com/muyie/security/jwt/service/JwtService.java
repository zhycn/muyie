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
   * @param rememberMe  是否记住登录
   * @param authorities 权限列表
   * @return token
   */
  String login(String username, String password, boolean rememberMe, String... authorities);

  /**
   * 基于用户名登录（自行校验用户信息）
   *
   * @param username    用户名
   * @param rememberMe  是否记住登录
   * @param authorities 权限列表
   * @return token
   */
  String login(String username, boolean rememberMe, String... authorities);

  /**
   * 退出登录
   */
  void logout();

}
