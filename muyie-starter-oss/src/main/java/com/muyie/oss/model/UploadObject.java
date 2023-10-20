package com.muyie.oss.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 上传字节流请求报文
 *
 * @author larry
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UploadObject {

  /**
   * 上传字节流（Base64编码的字节对象）
   */
  @NotBlank(message = "object不能为空")
  private String object;

  /**
   * 指定上传的后缀名
   */
  @NotBlank(message = "suffix不能为空")
  @Length(max = 8, message = "suffix长度不能超过8个字符")
  private String suffix;

}
