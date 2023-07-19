package com.muyie.apollo;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 解决使用 `@ConfigurationProperties` 注解时，配置属性不自动更新的问题。（应用重启也能解决这个问题）
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "apollo.bootstrap.enabled", havingValue = "true")
public class MuyieApolloAutoConfiguration extends BaseConfigChangeListener {

  /**
   * 默认只能自动更新 `application` 命名空间的配置属性，其他命名空间的配置属性更新可参考该方法实现。
   *
   * @param configChangeEvent 配置变更事件
   * @since 2.7.13
   */
  @Override
  @ApolloConfigChangeListener
  public void onChange(ConfigChangeEvent configChangeEvent) {
    refreshConfigChange(configChangeEvent);
  }

}
