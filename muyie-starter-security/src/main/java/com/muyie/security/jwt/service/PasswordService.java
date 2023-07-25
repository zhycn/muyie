package com.muyie.security.jwt.service;

/**
 * 明文密码加密处理
 *
 * @author larry.qi
 * @since 2.7.14
 */
public interface PasswordService {

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
