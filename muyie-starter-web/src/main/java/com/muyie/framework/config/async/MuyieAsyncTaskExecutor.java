package com.muyie.framework.config.async;

import org.springframework.lang.Nullable;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

import lombok.extern.slf4j.Slf4j;

/**
 * Customize the MuyieAsyncTaskExecutor.
 *
 * @author larry.qi
 * @since 1.2.8
 */
@Slf4j
@Component
public class MuyieAsyncTaskExecutor {

  static final String EXCEPTION_MESSAGE = "Caught async exception";

  private final ThreadPoolTaskExecutor taskExecutor;
  private final ThreadPoolTaskScheduler taskScheduler;

  public MuyieAsyncTaskExecutor(final ThreadPoolTaskExecutor taskExecutor, final ThreadPoolTaskScheduler taskScheduler) {
    this.taskExecutor = taskExecutor;
    this.taskScheduler = taskScheduler;
  }

  public ThreadPoolTaskExecutor getTaskExecutor() {
    return taskExecutor;
  }

  public ThreadPoolTaskScheduler getTaskScheduler() {
    return taskScheduler;
  }


  private <T> Callable<T> createCallable(Callable<T> task) {
    return () -> {
      try {
        return task.call();
      } catch (Exception e) {
        handle(e);
        throw e;
      }
    };
  }

  private Runnable createWrappedRunnable(Runnable task) {
    return () -> {
      try {
        task.run();
      } catch (Exception e) {
        handle(e);
      }
    };
  }

  /**
   * <p>handle.</p>
   *
   * @param e a {@link Exception} object.
   */
  protected void handle(Exception e) {
    log.error(EXCEPTION_MESSAGE, e);
  }

  public void execute(Runnable task) {
    getTaskExecutor().execute(createWrappedRunnable(task));
  }

  public Future<?> submit(Runnable task) {
    return getTaskExecutor().submit(createWrappedRunnable(task));
  }

  public <T> Future<T> submit(Callable<T> task) {
    return getTaskExecutor().submit(createCallable(task));
  }

  public ListenableFuture<?> submitListenable(Runnable task) {
    return getTaskExecutor().submitListenable(createWrappedRunnable(task));
  }

  public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
    return getTaskScheduler().submitListenable(createCallable(task));
  }

  @Nullable
  public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
    return getTaskScheduler().schedule(createWrappedRunnable(task), trigger);
  }

  public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
    return getTaskScheduler().schedule(createWrappedRunnable(task), startTime);
  }

  public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
    return getTaskScheduler().scheduleAtFixedRate(createWrappedRunnable(task), startTime, period);
  }

  public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
    return getTaskScheduler().scheduleAtFixedRate(createWrappedRunnable(task), period);
  }

  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
    return getTaskScheduler().scheduleWithFixedDelay(createWrappedRunnable(task), startTime, delay);
  }

  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
    return getTaskScheduler().scheduleWithFixedDelay(createWrappedRunnable(task), delay);
  }

  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Duration delay) {
    return scheduleWithFixedDelay(task, delay.toMillis());
  }

  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Instant startTime, Duration delay) {
    return scheduleWithFixedDelay(task, Date.from(startTime), delay.toMillis());
  }

  public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period) {
    return scheduleAtFixedRate(task, period.toMillis());
  }

  public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Instant startTime, Duration period) {
    return scheduleAtFixedRate(task, Date.from(startTime), period.toMillis());
  }

  public ScheduledFuture<?> schedule(Runnable task, Instant startTime) {
    return schedule(task, Date.from(startTime));
  }

}
