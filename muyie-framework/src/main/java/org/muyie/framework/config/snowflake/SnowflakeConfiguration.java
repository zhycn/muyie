package org.muyie.framework.config.snowflake;

import org.muyie.framework.config.MuyieProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.hutool.core.lang.Snowflake;

@Configuration
@AutoConfigureAfter(MuyieProperties.class)
public class SnowflakeConfiguration {

  private final MuyieProperties.Snowflake properties;

  public SnowflakeConfiguration(MuyieProperties muyieProperties) {
    this.properties = muyieProperties.getSnowflake();
  }

  @Bean
  @ConditionalOnMissingBean
  public Snowflake snowflake() {
    return new Snowflake(properties.getWorkerId(), properties.getDatacenterId(),
        properties.isUseSystemClock());
  }

}
