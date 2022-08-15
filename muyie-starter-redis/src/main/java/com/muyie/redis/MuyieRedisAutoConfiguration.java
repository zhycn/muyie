package com.muyie.redis;

import com.muyie.redis.cache.RedisCache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author larry.qi
 * @since 1.2.5
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
@Import({RedisCache.class})
public class MuyieRedisAutoConfiguration {

  /**
   * 替换 Spring Boot 默认配置的 RedisTemplate，优化序列化设置。
   *
   * @param redisConnectionFactory 连接工厂
   * @return redisTemplate
   */
  @Bean
  @ConditionalOnMissingBean(name = "redisTemplate")
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(RedisSerializer.string());
    template.setValueSerializer(RedisSerializer.json());
    template.setHashKeySerializer(RedisSerializer.string());
    template.setHashValueSerializer(RedisSerializer.json());
    template.afterPropertiesSet();
    return template;
  }

}
