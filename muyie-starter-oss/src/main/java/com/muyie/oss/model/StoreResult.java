package com.muyie.oss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件存储结果
 *
 * @author larry
 * @since 2.7.13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreResult {

  /**
   * Bucket名称
   */
  private String bucket;

  /**
   * 对象名称
   */
  private String objectKey;

  /**
   * 对象链接
   */
  private String objectUrl;

  /**
   * ETag
   */
  private String etag;

}
