package com.muyie.redis.topic;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass(RedissonClient.class)
@Import(RedissonTopicPublisher.class)
@RequiredArgsConstructor
public class RedissonTopicAutoConfiguration implements InitializingBean {

  private final RedissonClient redissonClient;
  private final ConfigurableApplicationContext applicationContext;

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public void afterPropertiesSet() {
    // 注册消息发布订阅主题Topic
    // 找到所有实现了Redisson中MessageListener接口的bean名字
    String[] beanNamesForType = applicationContext.getBeanNamesForType(MessageListener.class);
    for (String beanName : beanNamesForType) {
      MessageListener bean = applicationContext.getBean(beanName, MessageListener.class);
      Class<?> beanClass = bean.getClass();
      // 如果bean的注解里包含我们的自定义注解RedissonTopicConsumer.class
      // 则以RedissonTopicConsumer注解的值作为name将该bean注册到bean工厂，方便在别处注入
      if (beanClass.isAnnotationPresent(RedissonTopicConsumer.class)) {
        RedissonTopicConsumer consumer = beanClass.getAnnotation(RedissonTopicConsumer.class);
        RTopic topic = redissonClient.getTopic(consumer.topic());
        topic.addListener(String.class, bean);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton(consumer.topic(), topic);
      }
    }
  }
}
