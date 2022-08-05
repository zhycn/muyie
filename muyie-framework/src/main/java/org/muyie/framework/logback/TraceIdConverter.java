package org.muyie.framework.logback;

import com.google.common.base.Strings;

import org.muyie.framework.config.MuyieConstants;
import org.slf4j.MDC;

import java.util.Optional;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 获取TraceId随机字符串
 */
public class TraceIdConverter extends ClassicConverter {

  @Override
  public String convert(ILoggingEvent event) {
    String traceId = MDC.get(MuyieConstants.LOG_TRACE_ID); // 获取TraceId值
    return Optional.ofNullable(Strings.emptyToNull(traceId)).orElseGet(() -> {
      String random = TraceIdUtil.get();
      MDC.put(MuyieConstants.LOG_TRACE_ID, random); // 默认赋值
      return random;
    });
  }

}
