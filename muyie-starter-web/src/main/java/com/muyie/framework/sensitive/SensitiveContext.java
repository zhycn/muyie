package com.muyie.framework.sensitive;

/**
 * 脱敏上下文信息
 */
public class SensitiveContext {

  private volatile static ThreadLocal<JsonSensitiveConfig> configContext = new ThreadLocal<>();

  private SensitiveContext() {
  }

  public static void setConfigContext(JsonSensitiveConfig config) {
    configContext.set(config);
  }

  public static JsonSensitiveConfig getConfigContext() {
    return configContext.get();
  }

  public static void removeConfigContext() {
    configContext.remove();
  }

}
