package com.muyie.framework.config.async;

import com.muyie.framework.config.MuyieProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Task Execution and Scheduling Configuration
 * <p>
 * 一般情况下，异步线程的参数保持默认配置即可，参考文档：<a
 * href="https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.task-execution-and-scheduling">Task
 * Execution and Scheduling</a>
 *
 * @author larry.qi
 * @since 1.2.8
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(MuyieProperties.class)
@EnableAsync
@EnableScheduling
public class MuyieAsyncAutoConfiguration {

  @Autowired
  private ThreadPoolTaskScheduler taskScheduler;

  @Autowired
  @Qualifier(AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
  private ThreadPoolTaskExecutor taskExecutor;

  @Bean
  @ConditionalOnMissingBean
  public MuyieExecutorService muyieExecutorService() {
    return new MuyieExecutorService(taskExecutor, taskScheduler);
  }

}
