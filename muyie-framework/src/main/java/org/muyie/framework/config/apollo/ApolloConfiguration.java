package org.muyie.framework.config.apollo;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;

import org.apache.commons.lang3.StringUtils;
import org.muyie.framework.config.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
public class ApolloConfiguration
  implements
  ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  private static final Logger log = LoggerFactory.getLogger(ApolloConfiguration.class);

  private static final String APOLLO_PROFILE_ACTIVED_KEY = "apollo.profile.actived";

  /**
   * 解决 Apollo 无法自动刷新 @ConfigurationProperties 注解类的BUG
   *
   * @param event ConfigChangeEvent
   */
  @ApolloConfigChangeListener
  public void onChange(ConfigChangeEvent event) {
    refreshScope(event);
  }

  private void refreshScope(ConfigChangeEvent event) {
    event.changedKeys().forEach(key -> {
      ConfigChange configChange = event.getChange(key);
      log.info("Apollo ConfigChangeEvent - {}", configChange);
    });

    // 更新相应的 Bean 的属性值，主要是存在 @ConfigurationProperties 注解的 Bean
    SpringContextHolder.getApplicationContext()
      .publishEvent(new EnvironmentChangeEvent(event.changedKeys()));
  }

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    ConfigurableEnvironment environment = event.getEnvironment();
    String profile = environment.getProperty(APOLLO_PROFILE_ACTIVED_KEY);
    if (StringUtils.isNotBlank(profile)) {
      System.setProperty("env", profile);
      log.info("Apollo actived profile is '{}'", System.getProperty("env"));
    }
  }

}
