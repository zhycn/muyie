package com.muyie.oss.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 对象名称（ObjectKey）生成器
 *
 * @author larry
 * @since 2.7.14
 */
@Slf4j
public class DefaultOssKeyGenerator implements OssKeyGenerator {

  /**
   * 生成对象名称
   *
   * @param prefix 指定存储目录 (eg: temp | user/temp)
   * @param suffix 指定文件后缀名 (eg: png)
   * @return 对象名称
   */
  private static String generateObjectKey(String prefix, String suffix) {
    Assert.hasText(prefix, "ObjectKey prefix must be not empty");
    Assert.hasText(suffix, "ObjectKey suffix must be not empty");
    String suffixName = StringUtils.replace(suffix, ".", "").toLowerCase();
    String date = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
    String random = IdUtil.fastSimpleUUID();
    // 注意：要移除前后的斜杠
    prefix = StringUtils.removeStart(prefix, "/");
    prefix = StringUtils.removeEnd(prefix, "/");
    String objectKey = StrUtil.format("{}/{}/{}.{}", prefix, date, random, suffixName);
    log.info("Generate objectKey: {}", objectKey);
    return objectKey;
  }

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

}
