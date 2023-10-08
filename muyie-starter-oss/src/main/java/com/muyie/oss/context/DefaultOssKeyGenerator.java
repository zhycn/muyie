package com.muyie.oss.context;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

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
   * @param folderName 指定存储目录 (eg: temp | user/temp)
   * @param suffix 指定文件后缀名 (eg: png)
   * @return 对象名称
   */
  private static String generateObjectKey(String folderName, String suffix) {
    Assert.hasText(folderName, "ObjectKey folderName must be not empty");
    Assert.hasText(suffix, "ObjectKey suffix must be not empty");
    String suffixName = StringUtils.replace(suffix, ".", "").toLowerCase();
    String date = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
    String random = IdUtil.fastSimpleUUID();
    // 注意：要移除前后的斜杠
    folderName = StringUtils.removeStart(folderName, "/");
    folderName = StringUtils.removeEnd(folderName, "/");
    String objectKey = StrUtil.format("{}/{}/{}.{}", folderName, date, random, suffixName);
    log.info("Generate objectKey: {}", objectKey);
    return objectKey;
  }

  @Override
  public String getObjectKey(String folderName, String suffix) {
    return generateObjectKey(folderName, suffix);
  }

  @Override
  public String getObjectKey(String folderName, MultipartFile file) {
    String fileName = file.getOriginalFilename();
    String suffix = StringUtils.substringAfterLast(fileName, ".").toLowerCase();
    return generateObjectKey(folderName, suffix);
  }

}
