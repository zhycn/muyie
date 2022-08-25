package com.muyie.configure;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Spring ApplicationContext
 *
 * @author larry.qi
 * @since 1.2.6
 */
@Configuration(proxyBeanMethods = false)
public class SpringContextHolder implements ApplicationContextAware {

  private static ApplicationContext applicationContext = null;

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringContextHolder.applicationContext = applicationContext;
  }

  public static <T> T getBean(String name, Class<T> requiredType) {
    return getApplicationContext().getBean(name, requiredType);
  }

  public static <T> T getBean(Class<T> requiredType) {
    return getApplicationContext().getBean(requiredType);
  }

  public static boolean containsBean(String name) {
    return getApplicationContext().containsBean(name);
  }

}
