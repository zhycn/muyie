package org.muyie.framework.config.logback;

import ch.qos.logback.classic.PatternLayout;

public class TraceIdPatternLayout extends PatternLayout {

  static {
    defaultConverterMap.put("traceId", TraceIdConverter.class.getName());
  }

}
