package com.muyie.oss.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.unit.DataSize;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Bucket 配置信息
 *
 * @author larry
 * @since 2.7.14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BucketProfile {

  /**
   * Bucket 名称
   */
  private String bucket;

  /**
   * 访问文件的DNS
   */
  private String dns;

  /**
   * 如果配置了CND，则优先于DNS
   */
  private String cdn;

  /**
   * 默认上传文件的目录（可在上传请求中指定）
   */
  private String prefixName = "temp";

  /**
   * 是否允许上传到指定目录
   */
  private boolean allowPrefix;

  /**
   * 支持的文件后缀名称
   */
  private String[] suffixNames = new String[]{"bmp", "jpg", "jpeg", "gif", "png", "svg", "webp"};

  /**
   * 是否允许指定后缀名
   */
  private boolean allowSuffix;

  /**
   * 多文件上传允许的最大数量
   */
  private int maxFiles = 10;

  /**
   * 单个文件上传大小限制（默认1M）
   */
  private DataSize maxFileSize = DataSize.ofMegabytes(1L);

  /**
   * 多个文件上传大小限制（默认10M）
   */
  private DataSize maxRequestSize = DataSize.ofMegabytes(10L);

  /**
   * 获取文件上传后的BaseUrl，如果配置了CDN，则优先取CND地址，否则取DNS地址。
   *
   * @return baseUrl，以`/`结尾
   */
  public String getBaseUrl() {
    String baseUrl = StringUtils.defaultIfBlank(cdn, dns);
    return StringUtils.endsWith(baseUrl, "/") ? baseUrl : baseUrl + "/";
  }

}
