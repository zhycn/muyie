package com.muyie.framework.config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;

/**
 * Spring ApplicationEventPublisher
 *
 * @author larry.qi
 * @since 1.2.6
 */
@Configuration(proxyBeanMethods = false)
public class SpringEventPublisher implements ApplicationEventPublisherAware {

  private static ApplicationEventPublisher applicationEventPublisher;

  public static ApplicationEventPublisher getApplicationEventPublisher() {
    return applicationEventPublisher;
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    SpringEventPublisher.applicationEventPublisher = applicationEventPublisher;
  }

  public static void publishEvent(Object event) {
    getApplicationEventPublisher().publishEvent(event);
  }

  public static void publishEvent(ApplicationEvent event) {
    getApplicationEventPublisher().publishEvent(event);
  }

}
