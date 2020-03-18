package org.muyie.framework.config.logback;

import java.util.Optional;

import org.muyie.framework.config.MuyieConstants;
import org.slf4j.MDC;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.hutool.core.util.IdUtil;

public class TraceIdConverter extends ClassicConverter {

  @Override
  public String convert(ILoggingEvent event) {
    return Optional.ofNullable(MDC.get(MuyieConstants.LOG_TRACE_ID)).orElseGet(() -> {
      String traceId = IdUtil.fastSimpleUUID();
      MDC.putCloseable(MuyieConstants.LOG_TRACE_ID, traceId);
      return traceId;
    });
  }

}
