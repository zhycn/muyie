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
   * 文件存储路径
   */
  private String storePath;

  /**
   * 文件存储链接
   */
  private String storeUrl;

}
