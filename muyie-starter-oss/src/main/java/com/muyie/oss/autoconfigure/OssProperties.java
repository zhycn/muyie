package com.muyie.oss.autoconfigure;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.google.common.collect.Maps;
import com.muyie.oss.model.BucketProfile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Objects;

/**
 * @author larry
 * @since 2.7.13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties("muyie.oss")
public class OssProperties {
  private final Map<String, BucketProfile> buckets = Maps.newConcurrentMap();
  private String endpoint;
  private String accessKeyId;
  private String secretAccessKey;
  private String securityToken;
  private ClientBuilderConfiguration config;

  public BucketProfile getBucketProfile(String bucketKey) {
    return Objects.requireNonNull(buckets.get(bucketKey), "BucketProfile key '" + bucketKey + "' not found");
  }

}
