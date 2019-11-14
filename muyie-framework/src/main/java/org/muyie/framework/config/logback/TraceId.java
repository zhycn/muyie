package org.muyie.framework.config.logback;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.util.IdUtil;

public final class TraceId {

  private volatile static ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

  public static String get() {
    String traceId = TRACE_ID.get();
    if (StringUtils.isBlank(traceId)) {
      String uuid = IdUtil.fastSimpleUUID();
      TraceId.set(uuid);
      return uuid;
    }
    return traceId;
  }

  public static void set(String traceId) {
    TRACE_ID.set(traceId);
  }

  public static void remove() {
    TRACE_ID.remove();
  }

}
