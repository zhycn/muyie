package com.muyie.oss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Bucket 配置信息
 *
 * @author larry
 * @since 2.7.13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BucketProfile {

  private String bucket;
  private String dns;
  private String cdn;

}
