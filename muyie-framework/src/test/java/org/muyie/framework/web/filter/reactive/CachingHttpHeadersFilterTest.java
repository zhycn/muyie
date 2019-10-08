package org.muyie.framework.web.filter.reactive;

import org.junit.jupiter.api.Test;
import org.muyie.framework.web.filter.reactive.CachingHttpHeadersFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class CachingHttpHeadersFilterTest {

  private long ttl = TimeUnit.DAYS.toMillis(2);
  private CachingHttpHeadersFilter filter = new CachingHttpHeadersFilter(ttl);

  @Test
  public void cacheHeadersSetWhenPathMatches() {
    long now = System.currentTimeMillis();
    WebFilterChain filterChain = (filterExchange) -> {
      try {
        HttpHeaders headers = filterExchange.getResponse().getHeaders();
        assertThat(headers.getPragma()).isEqualTo("cache");
        assertThat(headers.getCacheControl()).isEqualTo("max-age=172800000, public");
        assertThat(headers.getExpires() - now).isBetween(ttl - 1000, ttl + 1000);
      } catch (AssertionError ex) {
        return Mono.error(ex);
      }
      return Mono.empty();
    };
    MockServerWebExchange exchange =
        MockServerWebExchange.from(MockServerHttpRequest.get("/app/foo"));
    this.filter.filter(exchange, filterChain).block();
  }

  @Test
  public void cacheHeadersNotSetWhenPathDoesntMatch() {
    WebFilterChain filterChain = (filterExchange) -> {
      try {
        HttpHeaders headers = filterExchange.getResponse().getHeaders();
        assertThat(headers.getPragma()).isNull();
        assertThat(headers.getCacheControl()).isNull();;
        assertThat(headers.getExpires()).isEqualTo(-1);
      } catch (AssertionError ex) {
        return Mono.error(ex);
      }
      return Mono.empty();
    };
    MockServerWebExchange exchange =
        MockServerWebExchange.from(MockServerHttpRequest.get("/foo/foo"));
    this.filter.filter(exchange, filterChain).block();
  }

}
