package com.muyie.oss.model;

import org.springframework.web.multipart.MultipartFile;

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
public class OssUploadRequest {

  /**
   * 指定上传的目录（可选）- 默认配置：temp
   */
  private String prefixName;

  /**
   * 指定上传文件的后缀名（可选）- 默认会读取文件的后缀名
   */
  private String suffixName;

  /**
   * 上传网络文件流（四选一）
   */
  private String url;

  /**
   * 上传字节流（四选一）
   */
  private byte[] bytes;

  /**
   * 单文件上传（四选一）
   */
  private MultipartFile file;

  /**
   * 多文件上传（四选一）
   */
  private MultipartFile[] files;

}
