package org.muyie.framework.config.logback;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.util.IdUtil;

public final class TraceId {

  private volatile static ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

  public static String get() {
    if (StringUtils.isBlank(TRACE_ID.get())) {
      TraceId.set(IdUtil.fastSimpleUUID());
    }
    return TRACE_ID.get();
  }

  public static void set(String traceId) {
    TRACE_ID.set(traceId);
  }

  public static void remove() {
    TRACE_ID.remove();
  }

}
