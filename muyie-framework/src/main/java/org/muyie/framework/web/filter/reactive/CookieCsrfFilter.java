package org.muyie.framework.web.filter.reactive;

import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import java.util.Optional;

import reactor.core.publisher.Mono;

/**
 * <p>
 * CookieCsrfFilter class.
 * </p>
 */
public class CookieCsrfFilter implements WebFilter {

  private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

  /**
   * {@inheritDoc}
   */
  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
    if (exchange.getRequest().getCookies().get(CSRF_COOKIE_NAME) != null) {
      return chain.filter(exchange);
    }
    final Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
    if (csrfToken == null) {
      return chain.filter(exchange);
    }

    return csrfToken.doOnSuccess(token -> {
      final ResponseCookie cookie = ResponseCookie.from(CSRF_COOKIE_NAME, token.getToken()).maxAge(-1).httpOnly(false)
        .path(getRequestContext(exchange.getRequest()))
        .secure(Optional.ofNullable(exchange.getRequest().getSslInfo()).isPresent()).build();
      exchange.getResponse().getCookies().add(CSRF_COOKIE_NAME, cookie);

    }).then(Mono.defer(() -> chain.filter(exchange)));
  }

  private String getRequestContext(final ServerHttpRequest request) {
    final String contextPath = request.getPath().contextPath().value();
    return StringUtils.hasLength(contextPath) ? contextPath : "/";
  }
}
