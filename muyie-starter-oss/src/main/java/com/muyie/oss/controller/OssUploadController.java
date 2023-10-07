package com.muyie.oss.controller;

import com.google.common.collect.Lists;
import com.muyie.dto.MultiResponse;
import com.muyie.dto.SingleResponse;
import com.muyie.exception.BaseException;
import com.muyie.exception.ErrorCodeDefaults;
import com.muyie.exception.ExceptionUtil;
import com.muyie.oss.autoconfigure.OssProperties;
import com.muyie.oss.context.OssKeyGenerator;
import com.muyie.oss.model.BucketProfile;
import com.muyie.oss.model.StoreResult;
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
   * @param bucketKey 指定上传的BucketKey
   * @param file      指定上传的文件
   * @param prefix    指定上传的目录
   * @return 返回上传结果信息
   */
  @PostMapping("single")
  @Operation(summary = "单文件上传")
  public MultiResponse<StoreResult> uploadSingle(@RequestParam("bucketKey") String bucketKey,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam(value = "prefix", required = false) String prefix) {
    return this.uploadMultipart(bucketKey, new MultipartFile[]{file}, prefix);
  }

  /**
   * 多文件上传 - 注意要添加请求头：content-type=multipart/form-data
   *
   * @param bucketKey 指定上传的BucketKey
   * @param files     指定上传的文件列表
   * @param prefix    指定上传的目录
   * @return 返回上传结果信息
   */
  @PostMapping("multipart")
  @Operation(summary = "多文件上传")
  public MultiResponse<StoreResult> uploadMultipart(@RequestParam("bucketKey") String bucketKey,
                                                    @RequestParam("files") MultipartFile[] files,
                                                    @RequestParam(value = "prefix", required = false) String prefix) {
    try {
      if (files == null) {
        throw ExceptionUtil.business(ErrorCodeDefaults.A0700);
      }
      BucketProfile bucketProfile = ossProperties.getBucketProfile(bucketKey);
      this.assertMaxFiles(bucketProfile, files.length);
      String targetPrefix = this.getPrefix(bucketProfile, prefix);
      this.anyMatchPrefix(bucketProfile, targetPrefix);
      List<StoreResult> list = Lists.newArrayList();
      for (MultipartFile file : files) {
        ExceptionUtil.validate().rewrite("文件不能为空").doThrow(file.isEmpty());
        String fileName = file.getOriginalFilename();
        log.info("上传的源文件名为：" + fileName);
        String objectKey = ossKeyGenerator.getObjectKey(targetPrefix, file);
        this.anyMatchSuffix(bucketProfile, objectKey);
        StoreResult storeResult = ossService.putObject(bucketKey, objectKey, file.getInputStream());
        list.add(storeResult);
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
   * @param bucketKey 指定上传的BucketKey
   * @param url       网络资源（主要是图片）
   * @param prefix    指定上传的目录
   * @param suffix    指定上传的后缀名
   * @return 返回上传结果信息
   */
  @PostMapping("url")
  @Operation(summary = "上传网络流")
  public SingleResponse<StoreResult> uploadUrl(@RequestParam("bucketKey") String bucketKey,
                                               @RequestParam("url") String url,
                                               @RequestParam(value = "prefix", required = false) String prefix,
                                               @RequestParam(value = "suffix", required = false) String suffix) {
    try {
      BucketProfile bucketProfile = ossProperties.getBucketProfile(bucketKey);
      String targetPrefix = this.getPrefix(bucketProfile, prefix);
      this.anyMatchPrefix(bucketProfile, targetPrefix);
      if (StringUtils.isBlank(suffix)) {
        suffix = StringUtils.substringAfterLast(url, ".");
      }
      String objectKey = ossKeyGenerator.getObjectKey(targetPrefix, suffix);
      this.anyMatchSuffix(bucketProfile, objectKey);
      StoreResult storeResult = ossService.putObject(bucketKey, objectKey, url);
      return SingleResponse.of(storeResult);
    } catch (BaseException e) {
      throw e;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "上传失败：" + e.getMessage());
    }
  }

  /**
   * 上传字节流 - 注意要添加请求头：content-type=multipart/form-data
   *
   * @param bucketKey 指定上传的BucketKey
   * @param object    上传字节流（Base64编码的字节对象）
   * @param prefix    指定上传的目录
   * @param suffix    指定上传的后缀名
   * @return 返回上传结果信息
   */
  @PostMapping("object")
  @Operation(summary = "上传字节流")
  public SingleResponse<StoreResult> uploadObject(@RequestParam("bucketKey") String bucketKey,
                                                  @RequestParam("object") String object,
                                                  @RequestParam(value = "prefix", required = false) String prefix,
                                                  @RequestParam(value = "suffix") String suffix) {
    try {
      BucketProfile bucketProfile = ossProperties.getBucketProfile(bucketKey);
      String targetPrefix = this.getPrefix(bucketProfile, prefix);
      this.anyMatchPrefix(bucketProfile, targetPrefix);
      String objectKey = ossKeyGenerator.getObjectKey(targetPrefix, suffix);
      this.anyMatchSuffix(bucketProfile, objectKey);
      byte[] bytes = Base64Utils.decodeFromUrlSafeString(object);
      StoreResult storeResult = ossService.putObject(bucketKey, objectKey, bytes);
      return SingleResponse.of(storeResult);
    } catch (BaseException e) {
      throw e;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "上传失败：" + e.getMessage());
    }
  }

  /**
   * 校验文件上传指定的文件后缀名称是否匹配
   *
   * @param bucketProfile Bucket配置信息
   * @param objectKey     对象名称
   */
  private void anyMatchSuffix(BucketProfile bucketProfile, String objectKey) {
    boolean anyMatch = Arrays.stream(bucketProfile.getSuffixSupports()).anyMatch(s -> StringUtils.endsWithIgnoreCase(objectKey, s));
    ExceptionUtil.business(ErrorCodeDefaults.A0701).doThrow(!anyMatch);
  }

  /**
   * 校验文件上传指定的目录是否匹配
   *
   * @param bucketProfile Bucket配置信息
   * @param prefix        指定文件上传的目录
   */
  private void anyMatchPrefix(BucketProfile bucketProfile, String prefix) {
    boolean anyMatch = Arrays.stream(bucketProfile.getPrefixSupports()).anyMatch(s -> StringUtils.endsWithIgnoreCase(prefix, s));
    ExceptionUtil.business(ErrorCodeDefaults.A0701).rewrite("文件上传目录不支持").doThrow(!anyMatch);
  }

  /**
   * 校验用户上传的文件数量是否超限
   *
   * @param bucketProfile Bucket配置信息
   * @param size          上传的文件数量
   */
  private void assertMaxFiles(BucketProfile bucketProfile, int size) {
    ExceptionUtil.business(ErrorCodeDefaults.A0425, "文件上传数量超限：size=" + size).doThrow(size > bucketProfile.getMaxFiles());
  }

  /**
   * 获取文件上传的目录
   *
   * @param bucketProfile Bucket配置信息
   * @param prefix        指定文件上传的目录
   */
  private String getPrefix(BucketProfile bucketProfile, String prefix) {
    if (bucketProfile.isAllowPrefix() && StringUtils.isNotBlank(prefix)) {
      return prefix;
    }
    return bucketProfile.getDefaultPrefix();
  }

}
