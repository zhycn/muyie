package org.muyie.framework.web.filter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.config.MuyieProperties;
import org.muyie.framework.web.filter.CachingHttpHeadersFilter;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.muyie.framework.web.filter.CachingHttpHeadersFilter.DEFAULT_DAYS_TO_LIVE;

public class CachingHttpHeadersFilterTest {

  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private FilterChain chain;
  private MuyieProperties properties;
  private CachingHttpHeadersFilter filter;

  @BeforeEach
  public void setup() {
    request = new MockHttpServletRequest();
    response = spy(new MockHttpServletResponse());
    chain = spy(new MockFilterChain());
    properties = new MuyieProperties();
    filter = new CachingHttpHeadersFilter(properties);
  }

  @AfterEach
  public void teardown() {
    filter.destroy();
  }

  @Test
  public void testWithoutInit() {
    final int daysToLive = DEFAULT_DAYS_TO_LIVE;
    final long secsToLive = TimeUnit.DAYS.toMillis(daysToLive);

    long before = System.currentTimeMillis();
    before -= before % 1000L;

    final Throwable caught = catchThrowable(() -> {
      filter.doFilter(request, response, chain);
      verify(chain).doFilter(request, response);
    });

    long after = System.currentTimeMillis();
    after += 1000L - (after % 1000L);

    verify(response).setHeader("Cache-Control", "max-age=" + secsToLive + ", public");
    verify(response).setHeader("Pragma", "cache");
    verify(response).setDateHeader(eq("Expires"), anyLong());
    assertThat(response.getDateHeader("Expires")).isBetween(before + secsToLive, after + secsToLive);
    assertThat(caught).isNull();
  }

  @Test
  public void testWithInit() {
    final int daysToLive = DEFAULT_DAYS_TO_LIVE >>> 2;
    final long secsToLive = TimeUnit.DAYS.toMillis(daysToLive);
    properties.getHttp().getCache().setTimeToLiveInDays(daysToLive);

    long before = System.currentTimeMillis();
    before -= before % 1000L;

    final Throwable caught = catchThrowable(() -> {
      filter.init(null);
      filter.doFilter(request, response, chain);
      verify(chain).doFilter(request, response);
    });

    long after = System.currentTimeMillis();
    after += 1000L - (after % 1000L);

    verify(response).setHeader("Cache-Control", "max-age=" + secsToLive + ", public");
    verify(response).setHeader("Pragma", "cache");
    verify(response).setDateHeader(eq("Expires"), anyLong());
    assertThat(response.getDateHeader("Expires")).isBetween(before + secsToLive, after + secsToLive);
    assertThat(caught).isNull();
  }
}
