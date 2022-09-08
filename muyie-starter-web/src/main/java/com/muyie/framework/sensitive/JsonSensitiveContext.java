package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.JSONWriter;

import java.util.Optional;

import lombok.Data;

/**
 * 敏感数据隐藏上下文设置
 *
 * @author larry.qi
 * @since 1.2.9
 */
public final class JsonSensitiveContext {

  private final static ThreadLocal<Context> SENSITIVE_CONTEXT = new ThreadLocal<>();
  private final static Context CONTEXT = new Context();

  private JsonSensitiveContext() {
  }

  /**
   * 设置当前线程 JSON 序列化时的忽略字段
   *
   * @param ignores 忽略字段
   */
  public static void setIgnores(String... ignores) {
    getContext().setIgnores(ignores);
  }

  /**
   * 设置当前线程 JSON 序列化时敏感数据隐藏策略的字段配置
   *
   * @param sensitiveStrategy 敏感数据隐藏策略的字段配置
   */
  public static void setSensitiveStrategy(SensitiveStrategy sensitiveStrategy) {
    getContext().setSensitiveStrategy(sensitiveStrategy);
  }

  /**
   * 设置当前线程 JSON 序列化时的日期格式
   *
   * @param dateFormat 日期格式
   */
  public static void setDateFormat(String dateFormat) {
    getContext().setDateFormat(dateFormat);
  }

  /**
   * 设置当前线程 JSON 序列化时的配置
   *
   * @param features JSON 序列化配置
   */
  public static void setFeatures(JSONWriter.Feature... features) {
    getContext().setFeatures(features);
  }

  /**
   * 获取当前线程 JSON 序列化时的忽略字段
   *
   * @return 忽略字段
   */
  public static String[] getIgnores() {
    return getContext().getIgnores();
  }

  /**
   * 获取当前线程 JSON 序列化时敏感数据隐藏策略的字段配置
   *
   * @return 敏感数据隐藏策略的字段配置
   */
  public static SensitiveStrategy getSensitiveStrategy() {
    return getContext().getSensitiveStrategy();
  }

  /**
   * 获取当前线程 JSON 序列化时的日期格式
   *
   * @return 日期格式
   */
  public static String getDateFormat() {
    return getContext().getDateFormat();
  }

  /**
   * 获取当前线程 JSON 序列化时的配置
   *
   * @return JSON 序列化配置
   */
  public static JSONWriter.Feature[] getFeatures() {
    return getContext().getFeatures();
  }

  /**
   * 清除当前线程缓存
   */
  public static void remove() {
    SENSITIVE_CONTEXT.remove();
  }

  private static Context getContext() {
    return Optional.ofNullable(SENSITIVE_CONTEXT.get()).orElseGet(() -> {
      SENSITIVE_CONTEXT.set(CONTEXT);
      return CONTEXT;
    });
  }

  @Data
  private static class Context {

    /**
     * 序列化时忽略的字段
     */
    private String[] ignores;

    /**
     * 敏感数据隐藏策略的字段配置
     */
    private SensitiveStrategy sensitiveStrategy;

    /**
     * 序列化的日期格式
     */
    private String dateFormat;

    /**
     * 序列化设置
     */
    private JSONWriter.Feature[] features;

  }

}
