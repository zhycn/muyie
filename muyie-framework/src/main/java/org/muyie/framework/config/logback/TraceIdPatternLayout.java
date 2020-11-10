package org.muyie.framework.config.logback;

import org.muyie.framework.config.MuyieConstants;

import ch.qos.logback.classic.PatternLayout;

public class TraceIdPatternLayout extends PatternLayout {

  static {
    defaultConverterMap.put(MuyieConstants.LOG_TRACE_ID, TraceIdConverter.class.getName());
  }

}
