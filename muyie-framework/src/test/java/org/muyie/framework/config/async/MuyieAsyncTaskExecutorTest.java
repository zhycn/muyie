package org.muyie.framework.config.async;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.test.LogbackRecorder;
import org.muyie.framework.test.LogbackRecorder.Event;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

public class MuyieAsyncTaskExecutorTest {

  private static final RuntimeException exception = new RuntimeException("Eek");
  private static final int testResult = 42;

  private boolean done;
  private Exception handled;
  private MockAsyncTaskExecutor task;
  private MuyieAsyncTaskExecutor executor;
  private LogbackRecorder recorder;

  @BeforeEach
  public void setup() {
    done = false;
    handled = null;
    task = spy(new MockAsyncTaskExecutor());
    executor = new TestMuyieAsyncTaskExecutor(task);
    recorder = LogbackRecorder.forClass(MuyieAsyncTaskExecutor.class).reset().capture("ALL");
  }

  @AfterEach
  public void teardown() {
    recorder.release();
  }

  @Test
  public void testExecuteWithoutException() {
    final Runnable runnable = spy(new MockRunnableWithoutException());
    Throwable caught = null;
    try {
      synchronized (executor) {
        executor.execute(runnable);
        executor.wait(100);
      }
    } catch (final InterruptedException x) {
      // This should never happen
      throw new Error(x);
    } catch (final Exception x) {
      caught = x;
    }
    assertThat(done).isEqualTo(true);
    verify(runnable).run();
    assertThat(caught).isNull();
    assertThat(handled).isNull();

    final List<Event> events = recorder.play();
    assertThat(events).isEmpty();
  }

  @Test
  public void testExecuteWithException() {
    final Runnable runnable = spy(new MockRunnableWithException());
    Throwable caught = null;
    try {
      synchronized (executor) {
        executor.execute(runnable, AsyncTaskExecutor.TIMEOUT_INDEFINITE);
        executor.wait(100);
      }
    } catch (final InterruptedException x) {
      // This should never happen
      throw new Error(x);
    } catch (final Exception x) {
      caught = x;
    }
    assertThat(done).isEqualTo(true);
    verify(runnable).run();
    assertThat(caught).isNull();
    assertThat(handled).isEqualTo(exception);

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(1);
    final Event event = events.get(0);
    assertThat(event.getLevel()).isEqualTo("ERROR");
    assertThat(event.getMessage()).isEqualTo(MuyieAsyncTaskExecutor.EXCEPTION_MESSAGE);
    assertThat(event.getThrown()).isEqualTo(exception.toString());
  }

  @Test
  public void testSubmitRunnableWithoutException() {
    final Runnable runnable = spy(new MockRunnableWithoutException());
    final Future<?> future = executor.submit(runnable);
    final Throwable caught = catchThrowable(() -> future.get());
    assertThat(done).isEqualTo(true);
    verify(runnable).run();
    assertThat(caught).isNull();
    assertThat(handled).isNull();

    final List<Event> events = recorder.play();
    assertThat(events).isEmpty();
  }

  @Test
  public void testSubmitRunnableWithException() {
    final Runnable runnable = spy(new MockRunnableWithException());
    final Future<?> future = executor.submit(runnable);
    final Throwable caught = catchThrowable(() -> future.get());
    assertThat(done).isEqualTo(true);
    verify(runnable).run();
    assertThat(caught).isNull();
    assertThat(handled).isEqualTo(exception);

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(1);
    final Event event = events.get(0);
    assertThat(event.getLevel()).isEqualTo("ERROR");
    assertThat(event.getMessage()).isEqualTo(MuyieAsyncTaskExecutor.EXCEPTION_MESSAGE);
    assertThat(event.getThrown()).isEqualTo(exception.toString());
  }

  @Test
  public void testSubmitCallableWithoutException() {
    final Callable<Integer> callable = spy(new MockCallableWithoutException());
    final Future<Integer> future = executor.submit(callable);
    final Throwable caught = catchThrowable(() -> assertThat(future.get()).isEqualTo(42));
    assertThat(done).isEqualTo(true);
    assertThat(caught).isNull();
    assertThat(handled).isNull();

    final List<Event> events = recorder.play();
    assertThat(events).isEmpty();
  }

  @Test
  public void testSubmitCallableWithException() {
    final Callable<Integer> callable = spy(new MockCallableWithException());
    final Future<Integer> future = executor.submit(callable);
    final Throwable caught = catchThrowable(() -> future.get());
    assertThat(done).isEqualTo(true);
    assertThat(caught).isInstanceOf(ExecutionException.class);
    assertThat(caught.getCause()).isEqualTo(handled);
    assertThat(handled).isEqualTo(exception);

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(1);
    final Event event = events.get(0);
    assertThat(event.getLevel()).isEqualTo("ERROR");
    assertThat(event.getMessage()).isEqualTo(MuyieAsyncTaskExecutor.EXCEPTION_MESSAGE);
    assertThat(event.getThrown()).isEqualTo(exception.toString());
  }

  @Test
  public void testInitializingExecutor() {
    task = spy(new MockAsyncInitializingTaskExecutor());
    executor = new TestMuyieAsyncTaskExecutor(task);
    final Throwable caught = catchThrowable(() -> {
      executor.afterPropertiesSet();
      verify(task).afterPropertiesSet();
    });
    assertThat(caught).isNull();
  }

  @Test
  public void testNonInitializingExecutor() {
    final Throwable caught = catchThrowable(() -> {
      executor.afterPropertiesSet();
      verify(task, never()).afterPropertiesSet();
    });
    assertThat(caught).isNull();
  }

  @Test
  public void testDisposableExecutor() {
    task = spy(new MockAsyncDisposableTaskExecutor());
    executor = new TestMuyieAsyncTaskExecutor(task);
    final Throwable caught = catchThrowable(() -> {
      executor.destroy();
      verify(task).destroy();
    });
    assertThat(caught).isNull();
  }

  @Test
  public void testNonDisposableExecutor() {
    final Throwable caught = catchThrowable(() -> {
      executor.destroy();
      verify(task, never()).destroy();
    });
    assertThat(caught).isNull();
  }

  private class TestMuyieAsyncTaskExecutor extends MuyieAsyncTaskExecutor {

    TestMuyieAsyncTaskExecutor(final AsyncTaskExecutor executor) {
      super(executor);
    }

    @Override
    protected void handle(final Exception exception) {
      synchronized (executor) {
        handled = exception;
        super.handle(exception);
        executor.notifyAll();
      }
    }
  }

  private class MockRunnableWithoutException implements Runnable {

    @Override
    public void run() {
      synchronized (executor) {
        done = true;
        executor.notifyAll();
      }
    }
  }

  private class MockRunnableWithException implements Runnable {

    @Override
    public void run() {
      synchronized (executor) {
        done = true;
        throw exception;
      }
    }
  }

  private class MockCallableWithoutException implements Callable<Integer> {

    @Override
    public Integer call() {
      done = true;
      return testResult;
    }
  }

  private class MockCallableWithException implements Callable<Integer> {

    @Override
    public Integer call() {
      done = true;
      throw exception;
    }
  }

  @SuppressWarnings("serial")
  private class MockAsyncTaskExecutor extends SimpleAsyncTaskExecutor {

    public void afterPropertiesSet() {
    }

    public void destroy() {
    }
  }

  @SuppressWarnings("serial")
  private class MockAsyncInitializingTaskExecutor extends MockAsyncTaskExecutor implements InitializingBean {
  }

  @SuppressWarnings("serial")
  private class MockAsyncDisposableTaskExecutor extends MockAsyncTaskExecutor implements DisposableBean {
  }
}
