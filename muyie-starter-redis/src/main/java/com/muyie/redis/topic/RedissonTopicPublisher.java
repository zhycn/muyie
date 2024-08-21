package com.muyie.redis.topic;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedissonTopicPublisher {

  private final RedissonClient redissonClient;

  /**
   * 同步发布消息
   *
   * @param topic  主题名称
   * @param object 对象
   */
  public void publish(String topic, Object object) {
    redissonClient.getTopic(topic).publish(object);
  }

  /**
   * 异步发布消息
   *
   * @param topic  主题名称
   * @param object 对象
   */
  public void publishAsync(String topic, Object object) {
    redissonClient.getTopic(topic).publishAsync(object);
  }

}
