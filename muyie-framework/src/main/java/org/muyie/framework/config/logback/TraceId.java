package org.muyie.framework.config.logback;

import java.util.Optional;

import org.slf4j.MDC;

import cn.hutool.core.util.IdUtil;

public final class TraceId {

  private static final String TRACE_ID = "traceId";

  public static String get() {
    return Optional.ofNullable(MDC.get(TRACE_ID)).orElseGet(() -> {
      String uuid = IdUtil.fastSimpleUUID();
      TraceId.set(uuid);
      return uuid;
    });
  }

  public static void set(String traceId) {
    MDC.put(TRACE_ID, traceId);
  }

  public static void remove() {
    MDC.remove(TRACE_ID);
  }

}
