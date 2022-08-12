package com.muyie.apollo;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;

import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态刷新配置的抽象类
 *
 * @author larry.qi
 * @see MuyieApolloAutoConfiguration
 * @since 1.2.5
 */
@Slf4j
public abstract class BaseConfigChangeListener implements ApplicationContextAware, ConfigChangeListener {

  private static ApplicationContext applicationContext = null;

  protected static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
    BaseConfigChangeListener.applicationContext = applicationContext;
  }

  /**
   * 动态刷新配置的方法
   *
   * @param event 配置变更事件
   * @since 1.2.5
   */
  protected static void refreshConfigChange(@NonNull ConfigChangeEvent event) {
    event.changedKeys().forEach(key -> log.info("Apollo ConfigChangeEvent - {}", event.getChange(key)));
    getApplicationContext().publishEvent(new EnvironmentChangeEvent(event.changedKeys()));
  }

}
