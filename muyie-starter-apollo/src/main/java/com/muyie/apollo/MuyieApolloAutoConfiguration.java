package com.muyie.apollo;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.config.PropertySourcesProcessor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import lombok.extern.slf4j.Slf4j;

/**
 * 解决使用 `@ConfigurationProperties` 注解时，配置属性不更新的问题。
 *
 * @author larry.qi
 * @date 2022-07-13
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty({"apollo.bootstrap.enabled"})
@ConditionalOnMissingBean({PropertySourcesProcessor.class})
public class MuyieApolloAutoConfiguration implements ApplicationContextAware, ConfigChangeListener {

  private static ApplicationContext applicationContext = null;

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
    MuyieApolloAutoConfiguration.applicationContext = applicationContext;
  }

  @Override
  @ApolloConfigChangeListener
  public void onChange(@NonNull ConfigChangeEvent event) {
    event.changedKeys().forEach(key -> log.info("Apollo ConfigChangeEvent - {}", event.getChange(key)));
    getApplicationContext().publishEvent(new EnvironmentChangeEvent(event.changedKeys()));
  }

}
