package com.muyie.redis.cache;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * StringRedisCache
 *
 * @author larry.qi
 * @since 1.2.5
 **/
public class StringRedisCache extends RedisCache<String> {

  public StringRedisCache(StringRedisTemplate redisTemplate) {
    super(redisTemplate);
  }
}
