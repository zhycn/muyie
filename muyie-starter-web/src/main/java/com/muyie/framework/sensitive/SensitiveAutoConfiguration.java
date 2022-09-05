package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.JSON;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author larry.qi
 */
@Configuration
@ConditionalOnClass(JSON.class)
public class SensitiveAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public JsonSensitiveConfig jsonSensitiveConfig() {
    return JsonSensitiveConfig.of(SensitiveStrategy.builder().build());
  }

}
