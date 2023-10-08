package com.muyie.oss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 对象存储配置
 *
 * @author larry
 * @since 2.7.14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StorageConfig {

  /**
   * 指定对象存储的 Bucket 名称（必填）
   */
  private String bucket;

  /**
   * 指定访问文件的DNS
   */
  private String dns;

  /**
   * 指定访问文件的CDN。如果配置了CND，则优先于DNS
   */
  private String cdn;

  /**
   * 是否允许上传到指定目录
   */
  private boolean allowFolder;

  /**
   * 默认上传文件的目录（可在上传请求中指定）
   */
  private String defaultFolder = "tmp";

  /**
   * 支持上传的目录列表
   */
  private String[] folderSupports = new String[]{defaultFolder};

  /**
   * 支持的文件后缀名称列表
   */
  private String[] suffixSupports = new String[]{"bmp", "jpg", "jpeg", "gif", "png", "svg", "webp"};

  /**
   * 多文件上传允许的最大数量
   */
  private int maxFiles = 10;

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
