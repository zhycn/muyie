package com.muyie.oss.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 对象名称（ObjectKey）生成器
 *
 * @author larry
 * @since 2.7.14
 */
public class DefaultOssKeyGenerator implements OssKeyGenerator {

  @Override
  public String getObjectKey(String prefix, String suffix) {
    return generateObjectKey(prefix, suffix);
  }

  @Override
  public String getObjectKey(String prefix, MultipartFile file) {
    String fileName = file.getOriginalFilename();
    String suffix = StringUtils.substringAfterLast(fileName, ".").toLowerCase();
    return generateObjectKey(prefix, suffix);
  }

  /**
   * 生成对象名称
   *
   * @param prefix 指定存储文件夹 (eg: temp)
   * @param suffix 指定文件后缀名 (eg: png)
   * @return 对象名称
   */
  private static String generateObjectKey(String prefix, String suffix) {
    Assert.hasText(prefix, "ObjectKey prefix must be not empty");
    Assert.hasText(suffix, "ObjectKey suffix must be not empty");
    String prefixName = StringUtils.replace(prefix, "/", "");
    String suffixName = StringUtils.replace(suffix, ".", "").toLowerCase();
    String date = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
    String random = IdUtil.fastSimpleUUID();
    return StrUtil.format("{}/{}/{}.{}", prefixName, date, random, suffixName);
  }
}
