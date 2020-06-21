package org.muyie.framework.security;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

public class AjaxAuthenticationSuccessHandlerTest {

  private HttpServletResponse response;
  private AjaxAuthenticationSuccessHandler handler;

  @Captor
  private ArgumentCaptor<Integer> intCaptor;

  @Captor
  private ArgumentCaptor<String> stringCaptor;

  @BeforeEach
  public void setup() {
    response = spy(HttpServletResponse.class);
    handler = new AjaxAuthenticationSuccessHandler();
  }

  @Test
  public void testOnAuthenticationSuccess() {
    final Throwable caughtException = catchThrowable(() -> {
      handler.onAuthenticationSuccess(null, response, null);
      verify(response).setStatus(SC_OK);
    });
    assertThat(caughtException).isNull();
  }
}
