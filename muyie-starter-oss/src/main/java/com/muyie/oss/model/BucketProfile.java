package com.muyie.oss.model;

import org.apache.commons.lang3.StringUtils;

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
