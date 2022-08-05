package org.muyie.framework.config;

import org.apache.commons.lang3.Validate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextHolder implements ApplicationContextAware {

  private static ApplicationContext applicationContext = null;

  public static ApplicationContext getApplicationContext() {
    assertContextInjected();
    return applicationContext;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringContextHolder.applicationContext = applicationContext;
  }

  public static <T> T getBean(String name, Class<T> requiredType) {
    assertContextInjected();
    return applicationContext.getBean(name, requiredType);
  }

  private static void assertContextInjected() {
    Validate.validState(applicationContext != null);
  }

}
