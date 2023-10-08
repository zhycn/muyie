package com.muyie.oss.context;

import org.springframework.web.multipart.MultipartFile;

/**
 * 对象名称（ObjectKey）生成器
 *
 * @author larry
 * @since 2.7.14
 */
public interface OssKeyGenerator {

  /**
   * 生成文件对象名称
   *
   * @param folder 指定存储目录 (eg: tmp | user/tmp)
   * @param suffix 指定文件后缀名 (eg: png)
   * @return 对象名称
   */
  String getObjectKey(String folder, String suffix);

  /**
   * 生成文件对象名称
   *
   * @param folder 指定存储目录 (eg: temp | user/temp)
   * @param file   上传的文件（从文件名称中获取后缀名）
   * @return 对象名称
   */
  String getObjectKey(String folder, MultipartFile file);

}
