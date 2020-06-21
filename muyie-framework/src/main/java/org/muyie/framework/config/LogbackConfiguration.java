package org.muyie.framework.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.hutool.core.util.IdUtil;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter({ MuyieProperties.class })
@Order(Ordered.LOWEST_PRECEDENCE)
public class LogbackConfiguration implements WebMvcConfigurer {

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerInterceptor() {

      @Override
      public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
          final Object handler) throws Exception {
        final String traceId = request.getHeader(MuyieConstants.TRACE_ID_HEADER);
        final String tid = StringUtils.defaultIfBlank(traceId, IdUtil.fastSimpleUUID());
        MDC.put(MuyieConstants.LOG_TRACE_ID, tid);
        return true;
      }

      @Override
      public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
          final Object handler, final Exception ex) throws Exception {
        MDC.remove(MuyieConstants.LOG_TRACE_ID);
      }
    });
  }

}
