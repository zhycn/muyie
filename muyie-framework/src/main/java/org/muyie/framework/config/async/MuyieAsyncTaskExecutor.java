package org.muyie.framework.config.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;

/**
 * Customize the AsyncTaskExecutor.
 */
public class MuyieAsyncTaskExecutor implements AsyncTaskExecutor, InitializingBean, DisposableBean {

  private static final Logger log = LoggerFactory.getLogger(MuyieAsyncTaskExecutor.class);

  static final String EXCEPTION_MESSAGE = "Caught async exception";

  private final AsyncTaskExecutor executor;

  /**
   * <p>
   * Constructor for MuyieAsyncTaskExecutor.
   * </p>
   *
   * @param executor a {@link org.springframework.core.task.AsyncTaskExecutor}
   *                 object.
   */
  public MuyieAsyncTaskExecutor(final AsyncTaskExecutor executor) {
    this.executor = executor;
  }

  /** {@inheritDoc} */
  @Override
  public void execute(final Runnable task) {
    executor.execute(createWrappedRunnable(task));
  }

  /** {@inheritDoc} */
  @Override
  public void execute(final Runnable task, final long startTimeout) {
    executor.execute(createWrappedRunnable(task), startTimeout);
  }

  private <T> Callable<T> createCallable(final Callable<T> task) {
    return TtlCallable.get(() -> {
      try {
        return task.call();
      } catch (final Exception e) {
        handle(e);
        throw e;
      }
    });
  }

  private Runnable createWrappedRunnable(final Runnable task) {
    return TtlRunnable.get(() -> {
      try {
        task.run();
      } catch (final Exception e) {
        handle(e);
      }
    });
  }

  /**
   * <p>
   * handle.
   * </p>
   *
   * @param e a {@link java.lang.Exception} object.
   */
  protected void handle(final Exception e) {
    log.error(EXCEPTION_MESSAGE, e);
  }

  /** {@inheritDoc} */
  @Override
  public Future<?> submit(final Runnable task) {
    return executor.submit(createWrappedRunnable(task));
  }

  /** {@inheritDoc} */
  @Override
  public <T> Future<T> submit(final Callable<T> task) {
    return executor.submit(createCallable(task));
  }

  /** {@inheritDoc} */
  @Override
  public void destroy() throws Exception {
    if (executor instanceof DisposableBean) {
      final DisposableBean bean = (DisposableBean) executor;
      bean.destroy();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void afterPropertiesSet() throws Exception {
    if (executor instanceof InitializingBean) {
      final InitializingBean bean = (InitializingBean) executor;
      bean.afterPropertiesSet();
    }
  }

}
