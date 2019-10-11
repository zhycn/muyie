package org.muyie.framework.async;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.muyie.framework.config.MuyieProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Customize the AsyncConfigurer.
 */
public class MuyieAsyncConfigurer implements AsyncConfigurer {

  private static final Logger log = LoggerFactory.getLogger(MuyieAsyncConfigurer.class);

  private static final String EXCEPTION_MESSAGE = "Caught async exception";

  private final MuyieProperties.Async properties;

  public MuyieAsyncConfigurer(MuyieProperties muyieProperties) {
    this.properties = muyieProperties.getAsync();
  }

  public AsyncTaskExecutor create() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(properties.getCorePoolSize());
    executor.setMaxPoolSize(properties.getMaxPoolSize());
    executor.setQueueCapacity(properties.getQueueCapacity());
    executor.setAllowCoreThreadTimeOut(properties.isAllowCoreThreadTimeOut());
    executor.setWaitForTasksToCompleteOnShutdown(properties.isWaitForJobsToCompleteOnShutdown());
    executor.setAwaitTerminationSeconds(properties.getAwaitTerminationSeconds());
    executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
    executor.setThreadNamePrefix(properties.getThreadNamePrefix());
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
  }

  @Override
  public Executor getAsyncExecutor() {
    return this.create();
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (e, method, params) -> handle(e);
  }

  protected void handle(Throwable e) {
    log.error(EXCEPTION_MESSAGE, e);
  }

}
