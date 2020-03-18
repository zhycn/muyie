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
@AutoConfigureAfter({MuyieProperties.class})
@Order(Ordered.LOWEST_PRECEDENCE)
public class LogbackConfiguration implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerInterceptor() {

      @Override
      public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
          Object handler) throws Exception {
        String traceId = request.getHeader(MuyieConstants.TRACE_ID_HEADER);
        String tid = StringUtils.defaultIfBlank(traceId, IdUtil.fastSimpleUUID());
        MDC.put(MuyieConstants.LOG_TRACE_ID, tid);
        return true;
      }

      @Override
      public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
          Object handler, Exception ex) throws Exception {
        MDC.remove(MuyieConstants.LOG_TRACE_ID);
      }
    });
  }

}
