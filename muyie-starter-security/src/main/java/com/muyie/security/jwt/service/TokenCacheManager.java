package com.muyie.security.jwt.service;

/**
 * Token缓存管理
 *
 * @author larry.qi
 * @since 2.7.14
 */
public interface TokenCacheManager {

  /**
   * 设置缓存
   *
   * @param token Token令牌
   */
  default void setCache(String token) {

  }

  /**
   * 验证缓存
   *
   * @param token Token令牌
   * @return true 表示通过
   */
  default boolean validateCache(String token) {
    return true;
  }

  /**
   * 清除缓存
   *
   * @param token Token令牌
   */
  default void removeCache(String token) {

  }

}
