package org.muyie.framework.config.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Customize the MuyieTaskExecutor.
 */
public class MuyieTaskExecutor {

  private final MuyieAsyncTaskExecutor taskExecutor;
  private final ScheduledExecutorService scheduledExecutor;

  public MuyieTaskExecutor(final MuyieAsyncTaskExecutor taskExecutor,
                           final ScheduledExecutorService scheduledExecutor) {
    this.taskExecutor = taskExecutor;
    this.scheduledExecutor = scheduledExecutor;
  }

  public void execute(final Runnable task) {
    taskExecutor.execute(task);
  }

  public void execute(final Runnable task, final long startTimeout) {
    taskExecutor.execute(task, startTimeout);
  }

  public Future<?> submit(final Runnable task) {
    return taskExecutor.submit(task);
  }

  public <T> Future<T> submit(final Callable<T> task) {
    return taskExecutor.submit(task);
  }

  public ScheduledFuture<?> schedule(final Runnable task, final long delay, final TimeUnit unit) {
    return scheduledExecutor.schedule(task, delay, unit);
  }

  public <T> ScheduledFuture<T> schedule(final Callable<T> task, final long delay, final TimeUnit unit) {
    return scheduledExecutor.schedule(task, delay, unit);
  }

  public ScheduledFuture<?> scheduleAtFixedRate(final Runnable task, final long initialDelay, final long period,
                                                final TimeUnit unit) {
    return scheduledExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
  }

  public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable task, final long initialDelay, final long delay,
                                                   final TimeUnit unit) {
    return scheduledExecutor.scheduleWithFixedDelay(task, initialDelay, delay, unit);
  }

}
