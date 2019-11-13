package org.muyie.framework.config.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class TraceIdConverter extends ClassicConverter {

  @Override
  public String convert(ILoggingEvent event) {
    return TraceId.get();
  }

}
