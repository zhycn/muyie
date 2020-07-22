package org.muyie.framework.web.filter.reactive;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

public class CachingHttpHeadersFilterTest {

  private final long ttl = TimeUnit.DAYS.toMillis(2);
  private final CachingHttpHeadersFilter filter = new CachingHttpHeadersFilter(ttl);

  @Test
  public void cacheHeadersSetWhenPathMatches() {
    final long now = System.currentTimeMillis();
    final WebFilterChain filterChain = (filterExchange) -> {
      try {
        final HttpHeaders headers = filterExchange.getResponse().getHeaders();
        assertThat(headers.getPragma()).isEqualTo("cache");
        assertThat(headers.getCacheControl()).isEqualTo("max-age=172800000, public");
        assertThat(headers.getExpires() - now).isBetween(ttl - 1000, ttl + 1000);
      } catch (final AssertionError ex) {
        return Mono.error(ex);
      }
      return Mono.empty();
    };
    final MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/app/foo"));
    this.filter.filter(exchange, filterChain).block();
  }

  @Test
  public void cacheHeadersNotSetWhenPathDoesntMatch() {
    final WebFilterChain filterChain = (filterExchange) -> {
      try {
        final HttpHeaders headers = filterExchange.getResponse().getHeaders();
        assertThat(headers.getPragma()).isNull();
        assertThat(headers.getCacheControl()).isNull();
        ;
        assertThat(headers.getExpires()).isEqualTo(-1);
      } catch (final AssertionError ex) {
        return Mono.error(ex);
      }
      return Mono.empty();
    };
    final MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/foo/foo"));
    this.filter.filter(exchange, filterChain).block();
  }

}
