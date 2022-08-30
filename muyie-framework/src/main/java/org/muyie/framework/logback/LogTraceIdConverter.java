package org.muyie.framework.logback;

import com.google.common.base.Strings;

import org.muyie.framework.config.MuyieConstants;
import org.slf4j.MDC;

import java.util.Map;
import java.util.Optional;

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
    return Optional.ofNullable(MDC.get(MuyieConstants.LOG_TRACE_ID)).orElseGet(() -> setAndGet(IdUtil.nanoId(12)));
  }

  /**
   * 设置并获取一个请求追踪标识
   *
   * @param traceId 请求追踪标识
   */
  public static void set(String traceId) {
    setAndGet(Optional.ofNullable(Strings.emptyToNull(traceId)).orElseGet(() -> IdUtil.nanoId(12)));
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
  public static void close() {
    MDC.remove(MuyieConstants.LOG_TRACE_ID);
  }

  /**
   * 拷贝线程上下文内容（适用于异步线程）
   *
   * @return 上下文内容
   */
  public static Map<String, String> getCopyOfContextMap() {
    return MDC.getCopyOfContextMap();
  }

  /**
   * 设置线程上下文内容（适用于异步线程）
   *
   * @param contextMap 上下文内容
   */
  public static void setContextMap(Map<String, String> contextMap) {
    MDC.setContextMap(contextMap);
  }

  @Override
  public String convert(ILoggingEvent event) {
    return get();
  }

}
