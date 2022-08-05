package org.muyie.framework.config.async;

import org.muyie.framework.config.MuyieProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Customize the SchedulingConfigurer.
 */
public class MuyieSchedulingConfigurer implements SchedulingConfigurer {

  private final MuyieProperties.Async properties;

  public MuyieSchedulingConfigurer(MuyieProperties muyieProperties) {
    this.properties = muyieProperties.getAsync();
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(scheduledTaskExecutor());
  }

  @Bean
  @Qualifier("scheduledTaskExecutor")
  public ScheduledExecutorService scheduledTaskExecutor() {
    int corePoolSize = properties.getCorePoolSize();
    return Executors.newScheduledThreadPool(corePoolSize);
  }

}
