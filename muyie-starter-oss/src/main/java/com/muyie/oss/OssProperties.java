package com.muyie.oss;


import com.aliyun.oss.ClientBuilderConfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author larry
 * @since 2.7.13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties("muyie.oss")
public class OssProperties {
  private String endpoint;
  private String accessKeyId;
  private String secretAccessKey;
  private String securityToken;
  private ClientBuilderConfiguration config;
}
