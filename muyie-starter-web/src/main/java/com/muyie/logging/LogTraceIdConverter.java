package com.muyie.logging;

import com.muyie.constants.MuyieConstants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.hutool.core.util.IdUtil;

/**
 * 在日志文件中打印请求追踪日志，自动生成一个随机字符串。
 *
 * @author larry.qi
 * @since 1.2.6
 */
public class LogTraceIdConverter extends ClassicConverter {

  /**
   * 获取当前的请求追踪标识，没有则随机生成。
   *
   * @return TraceId 请求追踪标识
   */
  public static String get() {
    return StringUtils.defaultIfBlank(MDC.get(MuyieConstants.LOG_TRACE_ID), setAndGet(IdUtil.nanoId(12)));
  }

  /**
   * 设置并获取一个请求追踪标识
   *
   * @param traceId 请求追踪标识
   */
  public static void set(String traceId) {
    setAndGet(StringUtils.defaultIfBlank(traceId, IdUtil.nanoId(12)));
  }

  /**
   * 设置并获取一个请求追踪标识
   *
   * @param traceId 请求追踪标识
   * @return 请求追踪标识
   */
  private static String setAndGet(String traceId) {
    MDC.put(MuyieConstants.LOG_TRACE_ID, traceId);
    return traceId;
  }

  /**
   * 释放线程中的缓存
   */
  public static void clear() {
    MDC.remove(MuyieConstants.LOG_TRACE_ID);
  }

  @Override
  public String convert(ILoggingEvent event) {
    System.out.println(get());
    return get();
  }

}
