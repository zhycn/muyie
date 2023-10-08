package com.muyie.oss.controller;

import com.google.common.collect.Lists;
import com.muyie.dto.MultiResponse;
import com.muyie.dto.SingleResponse;
import com.muyie.exception.BaseException;
import com.muyie.exception.ErrorCodeDefaults;
import com.muyie.exception.ExceptionUtil;
import com.muyie.oss.autoconfigure.OssProperties;
import com.muyie.oss.context.OssKeyGenerator;
import com.muyie.oss.model.StorageConfig;
import com.muyie.oss.model.StorageInfo;
import com.muyie.oss.service.OssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 对象上传接口
 *
 * @author larry.qi
 * @since 2.7.14
 */
@Slf4j
@RestController
@RequestMapping("${muyie.oss.context-path:api/upload}")
@RequiredArgsConstructor
@Tag(name = "文件对象上传接口")
public class OssUploadController {

  private final OssService ossService;
  private final OssProperties ossProperties;
  private final OssKeyGenerator ossKeyGenerator;

  /**
   * 单文件上传 - 注意要添加请求头：content-type=multipart/form-data
   *
   * @param key    指定上传的BucketKey
   * @param file   指定上传的文件
   * @param folder 指定上传的目录
   * @return 返回上传结果信息
   */
  @PostMapping("single")
  @Operation(summary = "单文件上传")
  public MultiResponse<StorageInfo> uploadSingle(@RequestParam("key") String key,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam(value = "folder", required = false) String folder) {
    return this.uploadMultipart(key, new MultipartFile[]{file}, folder);
  }

  /**
   * 多文件上传 - 注意要添加请求头：content-type=multipart/form-data
   *
   * @param key    指定上传的BucketKey
   * @param files  指定上传的文件列表
   * @param folder 指定上传的目录
   * @return 返回上传结果信息
   */
  @PostMapping("multipart")
  @Operation(summary = "多文件上传")
  public MultiResponse<StorageInfo> uploadMultipart(@RequestParam("key") String key,
                                                    @RequestParam("files") MultipartFile[] files,
                                                    @RequestParam(value = "folder", required = false) String folder) {
    try {
      if (files == null) {
        throw ExceptionUtil.business(ErrorCodeDefaults.A0700);
      }
      StorageConfig config = ossProperties.getStorageConfig(key);
      this.assertMaxFiles(config, files.length);
      String targetFolder = this.getFolder(config, folder);
      this.anyMatchFolder(config, targetFolder);
      List<StorageInfo> list = Lists.newArrayList();
      for (MultipartFile file : files) {
        ExceptionUtil.validate().rewrite("文件不能为空").doThrow(file.isEmpty());
        String fileName = file.getOriginalFilename();
        log.info("上传的源文件名为：" + fileName);
        String objectKey = ossKeyGenerator.getObjectKey(targetFolder, file);
        this.anyMatchSuffix(config, objectKey);
        StorageInfo storageInfo = ossService.putObject(key, objectKey, file.getInputStream());
        list.add(storageInfo);
      }
      return MultiResponse.of(list);
    } catch (BaseException e) {
      throw e;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "上传失败：" + e.getMessage());
    }
  }

  /**
   * 上传网络流 - 注意要添加请求头：content-type=multipart/form-data
   *
   * @param key    指定上传的BucketKey
   * @param url    网络资源（主要是图片）
   * @param folder 指定上传的目录
   * @param suffix 指定上传的后缀名
   * @return 返回上传结果信息
   */
  @PostMapping("url")
  @Operation(summary = "上传网络流")
  public SingleResponse<StorageInfo> uploadUrl(@RequestParam("key") String key,
                                               @RequestParam("url") String url,
                                               @RequestParam(value = "folder", required = false) String folder,
                                               @RequestParam(value = "suffix", required = false) String suffix) {
    try {
      StorageConfig config = ossProperties.getStorageConfig(key);
      String targetFolder = this.getFolder(config, folder);
      this.anyMatchFolder(config, targetFolder);
      if (StringUtils.isBlank(suffix)) {
        suffix = StringUtils.substringAfterLast(url, ".");
      }
      String objectKey = ossKeyGenerator.getObjectKey(targetFolder, suffix);
      this.anyMatchSuffix(config, objectKey);
      StorageInfo storageInfo = ossService.putObject(key, objectKey, url);
      return SingleResponse.of(storageInfo);
    } catch (BaseException e) {
      throw e;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "上传失败：" + e.getMessage());
    }
  }

  /**
   * 上传字节流 - 注意要添加请求头：content-type=multipart/form-data
   *
   * @param key    指定上传的BucketKey
   * @param object 上传字节流（Base64编码的字节对象）
   * @param folder 指定上传的目录
   * @param suffix 指定上传的后缀名
   * @return 返回上传结果信息
   */
  @PostMapping("object")
  @Operation(summary = "上传字节流")
  public SingleResponse<StorageInfo> uploadObject(@RequestParam("key") String key,
                                                  @RequestParam("object") String object,
                                                  @RequestParam(value = "folder", required = false) String folder,
                                                  @RequestParam(value = "suffix") String suffix) {
    try {
      StorageConfig config = ossProperties.getStorageConfig(key);
      String targetFolder = this.getFolder(config, folder);
      this.anyMatchFolder(config, targetFolder);
      String objectKey = ossKeyGenerator.getObjectKey(targetFolder, suffix);
      this.anyMatchSuffix(config, objectKey);
      byte[] bytes = Base64Utils.decodeFromUrlSafeString(object);
      StorageInfo storageInfo = ossService.putObject(key, objectKey, bytes);
      return SingleResponse.of(storageInfo);
    } catch (BaseException e) {
      throw e;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "上传失败：" + e.getMessage());
    }
  }

  /**
   * 校验文件上传指定的文件后缀名称是否匹配
   *
   * @param config    对象存储配置
   * @param objectKey 对象名称
   */
  private void anyMatchSuffix(StorageConfig config, String objectKey) {
    boolean anyMatch = Arrays.stream(config.getSuffixSupports()).anyMatch(s -> StringUtils.endsWithIgnoreCase(objectKey, s));
    ExceptionUtil.business(ErrorCodeDefaults.A0701).doThrow(!anyMatch);
  }

  /**
   * 校验文件上传指定的目录是否匹配
   *
   * @param config 对象存储配置
   * @param folder 指定文件上传的目录
   */
  private void anyMatchFolder(StorageConfig config, String folder) {
    boolean anyMatch = Arrays.stream(config.getPrefixSupports()).anyMatch(s -> StringUtils.endsWithIgnoreCase(folder, s));
    ExceptionUtil.business(ErrorCodeDefaults.A0701).rewrite("文件上传目录不支持").doThrow(!anyMatch);
  }

  /**
   * 校验用户上传的文件数量是否超限
   *
   * @param config 对象存储配置
   * @param size   上传的文件数量
   */
  private void assertMaxFiles(StorageConfig config, int size) {
    ExceptionUtil.business(ErrorCodeDefaults.A0425, "文件上传数量超限：size=" + size).doThrow(size > config.getMaxFiles());
  }

  /**
   * 获取文件上传的目录
   *
   * @param config 对象存储配置
   * @param folder 指定文件上传的目录
   */
  private String getFolder(StorageConfig config, String folder) {
    if (config.isAllowPrefix() && StringUtils.isNotBlank(folder)) {
      return folder;
    }
    return config.getDefaultPrefix();
  }

}
