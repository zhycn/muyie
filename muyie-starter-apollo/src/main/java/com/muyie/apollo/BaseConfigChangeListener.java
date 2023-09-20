package com.muyie.apollo;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * 动态更新属性配置抽象类
 *
 * @author larry.qi
 * @since 2.7.13
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
   * 动态更新配置的通用方法，变更信息将在日志文件中打印日志记录。
   *
   * @param configChangeEvent 配置变更事件
   * @since 2.7.13
   */
  protected static void refreshConfigChange(ConfigChangeEvent configChangeEvent) {
    configChangeEvent.changedKeys().forEach(key ->
      log.info("Apollo ConfigChangeEvent - {}", configChangeEvent.getChange(key))
    );
    getApplicationContext().publishEvent(new EnvironmentChangeEvent(configChangeEvent.changedKeys()));
  }

}
