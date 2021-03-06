package org.muyie.framework.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.test.LogbackRecorder.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogbackRecorderTest {

  private static final String[] TEST_MESSAGES = { "error", "warn", "info", "debug", "trace" };
  private static final Object[] TEST_ARGUMENTS = { null, true, 1, 2D, 3F };

  private final Logger log = LoggerFactory.getLogger(LogbackRecorderTest.class);
  private final Marker marker = MarkerFactory.getMarker(log.getName());

  private final Exception exception = new RuntimeException("Eek");

  private final LogbackRecorder recorder = LogbackRecorder.forLogger(log);

  @BeforeEach
  public void setup() {
    recorder.reset();
  }

  @Test
  public void testTrace() {
    recorder.capture("TRACE");

    write();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(5);

    for (int i = 0; i < events.size(); i++) {
      final Event event = events.get(i);
      assertThat(event.getMarker()).isEqualTo(marker);
      assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[i].toUpperCase());
      assertThat(event.getMessage()).startsWith(TEST_MESSAGES[i]);
      assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[i]);
      assertThat(event.getThrown()).isNull();
    }

    recorder.release();
  }

  @Test
  public void testTraceWithException() {
    recorder.capture("TRACE");

    writeWithException();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(5);

    for (int i = 0; i < events.size(); i++) {
      final Event event = events.get(i);
      assertThat(event.getMarker()).isEqualTo(marker);
      assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[i].toUpperCase());
      assertThat(event.getMessage()).startsWith(TEST_MESSAGES[i]);
      assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[i]);
      assertThat(event.getThrown()).isEqualTo(exception.toString());
    }

    recorder.release();
  }

  @Test
  public void testDebug() {
    recorder.capture("DEBUG");

    write();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(4);

    for (int i = 0; i < events.size(); i++) {
      final Event event = events.get(i);
      assertThat(event.getMarker()).isEqualTo(marker);
      assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[i].toUpperCase());
      assertThat(event.getMessage()).startsWith(TEST_MESSAGES[i]);
      assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[i]);
      assertThat(event.getThrown()).isNull();
    }

    recorder.release();
  }

  @Test
  public void testDebugWithException() {
    recorder.capture("DEBUG");

    writeWithException();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(4);

    for (int i = 0; i < events.size(); i++) {
      final Event event = events.get(i);
      assertThat(event.getMarker()).isEqualTo(marker);
      assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[i].toUpperCase());
      assertThat(event.getMessage()).startsWith(TEST_MESSAGES[i]);
      assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[i]);
      assertThat(event.getThrown()).isEqualTo(exception.toString());
    }

    recorder.release();
  }

  @Test
  public void testInfo() {
    recorder.capture("INFO");

    write();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(3);

    for (int i = 0; i < events.size(); i++) {
      final Event event = events.get(i);
      assertThat(event.getMarker()).isEqualTo(marker);
      assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[i].toUpperCase());
      assertThat(event.getMessage()).startsWith(TEST_MESSAGES[i]);
      assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[i]);
      assertThat(event.getThrown()).isNull();
    }

    recorder.release();
  }

  @Test
  public void testInfoWithException() {
    recorder.capture("INFO");

    writeWithException();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(3);

    for (int i = 0; i < events.size(); i++) {
      final Event event = events.get(i);
      assertThat(event.getMarker()).isEqualTo(marker);
      assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[i].toUpperCase());
      assertThat(event.getMessage()).startsWith(TEST_MESSAGES[i]);
      assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[i]);
      assertThat(event.getThrown()).isEqualTo(exception.toString());
    }

    recorder.release();
  }

  @Test
  public void testWarn() {
    recorder.capture("WARN");

    write();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(2);

    for (int i = 0; i < events.size(); i++) {
      final Event event = events.get(i);
      assertThat(event.getMarker()).isEqualTo(marker);
      assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[i].toUpperCase());
      assertThat(event.getMessage()).startsWith(TEST_MESSAGES[i]);
      assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[i]);
      assertThat(event.getThrown()).isNull();
    }

    recorder.release();
  }

  @Test
  public void testWarnWithException() {
    recorder.capture("WARN");

    writeWithException();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(2);

    for (int i = 0; i < events.size(); i++) {
      final Event event = events.get(i);
      assertThat(event.getMarker()).isEqualTo(marker);
      assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[i].toUpperCase());
      assertThat(event.getMessage()).startsWith(TEST_MESSAGES[i]);
      assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[i]);
      assertThat(event.getThrown()).isEqualTo(exception.toString());
    }

    recorder.release();
  }

  @Test
  public void testError() {
    recorder.capture("ERROR");

    write();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(1);

    final Event event = events.get(0);
    assertThat(event.getMarker()).isEqualTo(marker);
    assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[0].toUpperCase());
    assertThat(event.getMessage()).startsWith(TEST_MESSAGES[0]);
    assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[0]);
    assertThat(event.getThrown()).isNull();

    recorder.release();
  }

  @Test
  public void testErrorWithException() {
    recorder.capture("ERROR");

    writeWithException();

    final List<Event> events = recorder.play();
    assertThat(events).hasSize(1);

    final Event event = events.get(0);
    assertThat(event.getMarker()).isEqualTo(marker);
    assertThat(event.getLevel()).isEqualTo(TEST_MESSAGES[0].toUpperCase());
    assertThat(event.getMessage()).startsWith(TEST_MESSAGES[0]);
    assertThat(event.getArguments()).containsExactly(TEST_ARGUMENTS[0]);
    assertThat(event.getThrown()).isEqualTo(exception.toString());

    recorder.release();
  }

  @Test
  public void testOff() {
    recorder.capture("OFF");

    write();

    final List<Event> events = recorder.play();
    assertThat(events).isEmpty();

    recorder.release();
  }

  @Test
  public void testOffWithException() {
    recorder.capture("OFF");

    writeWithException();

    final List<Event> events = recorder.play();
    assertThat(events).isEmpty();

    recorder.release();
  }

  @Test
  public void testLogbackException() {
    final Throwable caught = catchThrowable(() -> {
      LogbackRecorder.forLogger(mock(Logger.class));
    });
    assertThat(caught).isInstanceOf(IllegalArgumentException.class);
    assertThat(caught).hasMessage(LogbackRecorder.LOGBACK_EXCEPTION_MESSAGE);
  }

  @Test
  public void testCaptureException() {
    recorder.capture("ALL");
    final Throwable caught = catchThrowable(() -> recorder.capture("ALL"));
    assertThat(caught).isInstanceOf(IllegalStateException.class);
    assertThat(caught).hasMessage(LogbackRecorder.CAPTURE_EXCEPTION_MESSAGE);
    recorder.release();
  }

  @Test
  public void testReleaseException() {
    final Throwable caught = catchThrowable(() -> recorder.release());
    assertThat(caught).isInstanceOf(IllegalStateException.class);
    assertThat(caught).hasMessage(LogbackRecorder.RELEASE_EXCEPTION_MESSAGE);
  }

  @Test
  public void testClear() {
    recorder.capture("TRACE");

    write();

    recorder.release();

    assertThat(recorder.play()).hasSize(5);
    recorder.reset();
    assertThat(recorder.play()).isEmpty();
  }

  private void write() {
    log.error(marker, TEST_MESSAGES[0] + " {}", TEST_ARGUMENTS[0]);
    log.warn(marker, TEST_MESSAGES[1] + " {}", TEST_ARGUMENTS[1]);
    log.info(marker, TEST_MESSAGES[2] + " {}", TEST_ARGUMENTS[2]);
    log.debug(marker, TEST_MESSAGES[3] + " {}", TEST_ARGUMENTS[3]);
    log.trace(marker, TEST_MESSAGES[4] + " {}", TEST_ARGUMENTS[4]);
  }

  private void writeWithException() {
    log.error(marker, TEST_MESSAGES[0] + " {}", TEST_ARGUMENTS[0], exception);
    log.warn(marker, TEST_MESSAGES[1] + " {}", TEST_ARGUMENTS[1], exception);
    log.info(marker, TEST_MESSAGES[2] + " {}", TEST_ARGUMENTS[2], exception);
    log.debug(marker, TEST_MESSAGES[3] + " {}", TEST_ARGUMENTS[3], exception);
    log.trace(marker, TEST_MESSAGES[4] + " {}", TEST_ARGUMENTS[4], exception);
  }
}
