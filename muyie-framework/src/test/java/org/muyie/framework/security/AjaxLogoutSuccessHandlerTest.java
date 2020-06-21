package org.muyie.framework.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AjaxLogoutSuccessHandlerTest {

  private HttpServletResponse response;
  private AjaxLogoutSuccessHandler handler;

  @BeforeEach
  public void setup() {
    response = spy(HttpServletResponse.class);
    handler = new AjaxLogoutSuccessHandler();
  }

  @Test
  public void testOnAuthenticationSuccess() {
    final Throwable caughtException = catchThrowable(() -> {
      handler.onLogoutSuccess(null, response, null);
      verify(response).setStatus(HttpServletResponse.SC_OK);
    });
    assertThat(caughtException).isNull();
  }
}
