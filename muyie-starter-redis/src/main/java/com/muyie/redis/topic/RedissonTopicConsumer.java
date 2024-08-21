package com.muyie.redis.topic;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
public @interface RedissonTopicConsumer {
  String topic();
}
