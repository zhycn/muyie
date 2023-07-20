package com.muyie.oss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传结果
 *
 * @author larry
 * @since 2.7.13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UploadResult {

  /**
   * Bucket名称
   */
  private String bucket;

  /**
   * 文件存储路径
   */
  private String storePath;

  /**
   * 文件存储链接
   */
  private String storeUrl;

  /**
   * ETag
   */
  private String etag;

}
