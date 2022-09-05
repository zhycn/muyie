package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.filter.BeforeFilter;

import java.util.Arrays;

/**
 * @author larry.qi
 */
public class SensitiveBeforeFilter extends BeforeFilter {

  @Override
  public void writeBefore(Object object) {
    SensitiveConfig sensitiveConfig = object.getClass().getAnnotation(SensitiveConfig.class);
    System.out.println(sensitiveConfig);
    if (sensitiveConfig != null) {
      System.out.println(Arrays.toString(sensitiveConfig.phones()));
      SensitiveStrategy sensitiveStrategy = SensitiveStrategy.of(sensitiveConfig);
      JsonSensitiveConfig config = JsonSensitiveConfig.of(sensitiveStrategy);
      SensitiveContext.setConfigContext(config);
    }
  }
}
