package com.muyie.oss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * 上传网络流请求报文
 *
 * @author larry
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UploadUrl {

  /**
   * 网络资源地址（主要是图片）
   */
  @URL
  @NotBlank(message = "url不能为空")
  private String url;

  /**
   * 指定上传的后缀名（资源地址为空时，默认取.后面的字符串）
   */
  @Length(max = 8, message = "suffix长度不能超过8个字符")
  private String suffix;

  public String getSuffix() {
    return StringUtils.isBlank(suffix) ? StringUtils.substringAfterLast(url, ".") : suffix;
  }
}
