package org.muyie.framework.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.muyie.framework.security.AjaxAuthenticationFailureHandler.UNAUTHORIZED_MESSAGE;

public class AjaxAuthenticationFailureHandlerTest {

  private HttpServletResponse response;
  private AjaxAuthenticationFailureHandler handler;

  @BeforeEach
  public void setup() {
    response = spy(HttpServletResponse.class);
    handler = new AjaxAuthenticationFailureHandler();
  }

  @Test
  public void testOnAuthenticationFailure() {
    final Throwable caught = catchThrowable(() -> {
      handler.onAuthenticationFailure(null, response, null);
      verify(response).sendError(SC_UNAUTHORIZED, UNAUTHORIZED_MESSAGE);
    });
    assertThat(caught).isNull();
  }

  @Test
  public void testOnAuthenticationFailureWithException() {
    final IOException exception = new IOException("Eek");
    final Throwable caught = catchThrowable(() -> {
      doThrow(exception).when(response).sendError(anyInt(), anyString());
      handler.onAuthenticationFailure(null, response, null);
    });
    assertThat(caught).isEqualTo(exception);
  }
}
