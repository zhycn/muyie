package com.muyie.oss.controller;

import com.google.common.collect.Lists;

import com.muyie.dto.MultiResponse;
import com.muyie.dto.SingleResponse;
import com.muyie.exception.ErrorCodeDefaults;
import com.muyie.exception.ExceptionUtil;
import com.muyie.oss.autoconfigure.OssProperties;
import com.muyie.oss.context.OssKeyGenerator;
import com.muyie.oss.model.BucketProfile;
import com.muyie.oss.model.StoreResult;
import com.muyie.oss.service.OssService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 对象上传接口
 *
 * @author larry.qi
 * @since 2.7.14
 */
@Slf4j
@RestController
@RequestMapping("api/upload")
@RequiredArgsConstructor
public class OssUploadController {

  private final OssService ossService;
  private final OssProperties ossProperties;
  private final OssKeyGenerator ossKeyGenerator;

  /**
   * 单个文件上传 - 注意要添加请求头：content-type=multipart/form-data
   *
   * @param bucketKey 指定上传的BucketKey
   * @param file      指定上传的文件
   * @param prefix    指定上传的目录
   * @return 返回上传结果信息
   */
  @PostMapping("single")
  public MultiResponse<StoreResult> upload(@RequestParam("bucketKey") String bucketKey,
                                           @RequestParam("file") MultipartFile file,
                                           @RequestParam(value = "prefix", required = false) String prefix) {
    return this.upload(bucketKey, new MultipartFile[]{file}, prefix);
  }

  /**
   * 多个文件上传 - 注意要添加请求头：content-type=multipart/form-data
   *
   * @param bucketKey 指定上传的BucketKey
   * @param prefix    指定上传的目录
   * @return 返回上传结果信息
   */
  @PostMapping("multipart")
  public MultiResponse<StoreResult> upload(@RequestParam("bucketKey") String bucketKey,
                                           @RequestParam("files") MultipartFile[] files,
                                           @RequestParam(value = "prefix", required = false) String prefix) {
    BucketProfile bucketProfile = ossProperties.getBucketProfile(bucketKey);
    // 设置上传的目录
    if (bucketProfile.isAllowPrefix() && StringUtils.isNotBlank(prefix)) {
      bucketProfile.setPrefixName(prefix);
    }
    List<StoreResult> result = this.multipart(bucketKey, bucketProfile, files);
    return MultiResponse.of(result);
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
  @PostMapping("network")
  public SingleResponse<StoreResult> upload(@RequestParam("bucketKey") String bucketKey,
                                            @RequestParam("url") String url,
                                            @RequestParam(value = "prefix", required = false) String prefix,
                                            @RequestParam(value = "suffix", required = false) String suffix) {
    BucketProfile bucketProfile = ossProperties.getBucketProfile(bucketKey);
    // 设置上传的目录
    if (bucketProfile.isAllowPrefix() && StringUtils.isNotBlank(prefix)) {
      bucketProfile.setPrefixName(prefix);
    }
    // 上传网络流
    if (StringUtils.isBlank(suffix)) {
      suffix = StringUtils.substringAfterLast(url, ".");
    }
    String objectKey = ossKeyGenerator.getObjectKey(bucketProfile.getPrefixName(), suffix);
    this.anyMatchSuffixName(bucketProfile, objectKey);
    StoreResult storeResult = ossService.putObject(bucketKey, objectKey, url);
    return SingleResponse.of(storeResult);
  }

  /**
   * 上传字节流 - 注意要添加请求头：content-type=multipart/form-data
   *
   * @param bucketKey 指定上传的BucketKey
   * @param bytes     上传字节流
   * @param prefix    指定上传的目录
   * @param suffix    指定上传的后缀名
   * @return 返回上传结果信息
   */
  @PostMapping("stream")
  public SingleResponse<StoreResult> upload(@RequestParam("bucketKey") String bucketKey,
                                            @RequestParam("bytes") byte[] bytes,
                                            @RequestParam(value = "prefix", required = false) String prefix,
                                            @RequestParam(value = "suffix") String suffix) {
    BucketProfile bucketProfile = ossProperties.getBucketProfile(bucketKey);
    // 设置上传的目录
    if (bucketProfile.isAllowPrefix() && StringUtils.isNotBlank(prefix)) {
      bucketProfile.setPrefixName(prefix);
    }
    String objectKey = ossKeyGenerator.getObjectKey(bucketProfile.getPrefixName(), suffix);
    this.anyMatchSuffixName(bucketProfile, objectKey);
    StoreResult storeResult = ossService.putObject(bucketKey, objectKey, bytes);
    return SingleResponse.of(storeResult);
  }

  /**
   * 校验用户上传的文件后缀名是否匹配
   *
   * @param bucketProfile Bucket配置信息
   * @param objectKey     对象名称
   */
  private void anyMatchSuffixName(BucketProfile bucketProfile, String objectKey) {
    boolean anyMatch = Arrays.stream(bucketProfile.getSuffixNames()).anyMatch(s -> StringUtils.endsWithIgnoreCase(objectKey, s));
    ExceptionUtil.business(ErrorCodeDefaults.A0701).doThrow(!anyMatch);
  }

  /**
   * 校验用户上传的文件数量是否超限
   *
   * @param bucketProfile Bucket配置信息
   * @param files         上传的文件数量
   */
  private void checkMaxFiles(BucketProfile bucketProfile, int files) {
    ExceptionUtil.business(ErrorCodeDefaults.A0425, "文件上传数量超限：count=" + files).doThrow(files > bucketProfile.getMaxFiles());
  }

  private List<StoreResult> multipart(String bucketKey, BucketProfile bucketProfile, MultipartFile[] files) {
    if (files == null) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700);
    }
    this.checkMaxFiles(bucketProfile, files.length);
    List<StoreResult> list = Lists.newArrayList();
    for (MultipartFile file : files) {
      // 校验参数
      ExceptionUtil.validate().rewrite("文件不能为空").doThrow(file.isEmpty());
      // 获取文件名
      String fileName = file.getOriginalFilename();
      log.info("上传的文件名为：" + fileName);
      try {
        // 上传文件
        String objectKey = ossKeyGenerator.getObjectKey(bucketProfile.getPrefixName(), file);
        this.anyMatchSuffixName(bucketProfile, objectKey);
        StoreResult storeResult = ossService.putObject(bucketKey, objectKey, file.getInputStream());
        list.add(storeResult);
      } catch (Exception e) {
        throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
      }
    }
    return list;
  }

}
