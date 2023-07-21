package com.muyie.oss.autoconfigure;

import com.google.common.collect.Maps;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.muyie.oss.model.BucketProfile;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Objects;

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
  private final Map<String, BucketProfile> buckets = Maps.newConcurrentMap();

  public BucketProfile getBucketProfile(String key) {
    return Objects.requireNonNull(buckets.get(key), "BucketProfile key '" + key + "' not found");
  }

}
