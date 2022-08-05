package org.muyie.framework.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Returns a 401 error code (Unauthorized) to the client, when Ajax authentication fails.
 */
public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  /**
   * Constant <code>UNAUTHORIZED_MESSAGE="Authentication failed"</code>
   */
  public static final String UNAUTHORIZED_MESSAGE = "Authentication failed";

  /**
   * {@inheritDoc}
   */
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_MESSAGE);
  }
}
