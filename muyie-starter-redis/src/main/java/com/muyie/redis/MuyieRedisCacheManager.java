package com.muyie.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Spring Boot Cache Configuration
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Slf4j
@EnableCaching
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties.class)
@RequiredArgsConstructor
public class MuyieRedisCacheManager extends CachingConfigurerSupport {

  private final static String DEFAULT_KEY_PREFIX = "myCaches:";

  private final static Duration DEFAULT_TIME_TO_LIVE = Duration.ofMinutes(30L);

  private final CacheProperties cacheProperties;

  private final RedisConnectionFactory redisConnectionFactory;

  @Override
  @Bean
  @ConditionalOnMissingBean(name = "cacheManager")
  public CacheManager cacheManager() {
    log.info("Using MuYie RedisCacheManager.");
    String keyPrefix = cacheProperties.getRedis().getKeyPrefix();
    Duration timeToLive = cacheProperties.getRedis().getTimeToLive();
    RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
      // Disable caching null values.
      .disableCachingNullValues()
      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))
      .prefixCacheNameWith(StringUtils.hasLength(keyPrefix) ? keyPrefix : DEFAULT_KEY_PREFIX)
      .entryTtl(Objects.nonNull(timeToLive) ? timeToLive : DEFAULT_TIME_TO_LIVE);

    // initial CacheNames -> 未初始化的会动态创建
    Map<String, RedisCacheConfiguration> cacheConfigurations = new LinkedHashMap<>();
    for (String cacheName : cacheProperties.getCacheNames()) {
      cacheConfigurations.put(cacheName, defaultCacheConfiguration);
    }

    // RedisCacheManager build
    return RedisCacheManager.builder(redisConnectionFactory)
      .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
      .cacheDefaults(defaultCacheConfiguration)
      .withInitialCacheConfigurations(cacheConfigurations)
      .build();
  }

  /**
   * 在未指定 key 的情况下，会自动生成。
   *
   * @return KeyGenerator
   */
  @Override
  @Bean
  @ConditionalOnMissingBean(name = "keyGenerator")
  public KeyGenerator keyGenerator() {
    return (target, method, params) -> {
      StringBuilder sb = new StringBuilder();
      sb.append(target.getClass().getName()).append(".");
      sb.append(method.getName()).append(".");
      sb.append(SimpleKeyGenerator.generateKey(params));
      log.info("KeyGenerator={}", sb);
      return sb;
    };
  }

}
