package com.muyie.redis.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Spring Data Redis 工具类
 *
 * @author larry.qi
 * @since 1.2.5
 **/
@Component
public class StringRedisCache extends RedisCache2<String> {

  public StringRedisCache(RedisTemplate<String, String> redisTemplate) {
    super(redisTemplate);
  }
}
