package org.muyie.framework.config.logback;

import org.muyie.framework.config.MuyieConstants;
import org.slf4j.MDC;

public final class TraceIdContext {

  private TraceIdContext() {}

  public static void setTraceId(String traceId) {
    MDC.putCloseable(MuyieConstants.LOG_TRACE_ID, traceId);
  }

}
