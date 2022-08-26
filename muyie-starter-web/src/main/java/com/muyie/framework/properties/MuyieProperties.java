package com.muyie.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Properties specific to MuYie. Properties are configured in the application.yml file.
 *
 * @author larry.qi
 * @since 1.2.6
 */
@Data
@ConfigurationProperties(prefix = "muyie")
public class MuyieProperties {

  public final StopWatch stopWatch = new StopWatch();

  @Data
  public static class StopWatch {

    /**
     * 慢方法的阀值设置（全局）
     */
    private int slowMethodMillis = MuyieDefaults.StopWatch.SLOW_METHOD_MILLIS;

  }

}
