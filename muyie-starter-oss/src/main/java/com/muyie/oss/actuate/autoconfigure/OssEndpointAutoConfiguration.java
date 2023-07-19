package com.muyie.oss.actuate.autoconfigure;

import com.muyie.oss.actuate.OssEndpoint;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author larry
 * @since 2.7.13
 */
@ConditionalOnClass({Endpoint.class})
public class OssEndpointAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnAvailableEndpoint
  public OssEndpoint ossEndpoint() {
    return new OssEndpoint();
  }
}
