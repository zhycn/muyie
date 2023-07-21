package com.muyie.oss.service.impl;

import com.alibaba.fastjson2.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.muyie.exception.ErrorCodeDefaults;
import com.muyie.exception.ExceptionUtil;
import com.muyie.oss.autoconfigure.OssProperties;
import com.muyie.oss.model.BucketProfile;
import com.muyie.oss.model.StoreResult;
import com.muyie.oss.service.OssService;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author larry.qi
 * @since 2.7.14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OssServiceImpl implements OssService {

  private final OSS ossClient;
  private final OssProperties ossProperties;

  @Override
  public StoreResult putObject(String bucketKey, String objectKey, InputStream inputStream) {
    try {
      Assert.notNull(bucketKey, "bucketKey must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      Assert.notNull(inputStream, "inputStream must be not null");
      BucketProfile bucketProfile = ossProperties.getBucketProfile(bucketKey);
      log.info("putObject bucketKey={}, bucketName={}", bucketKey, bucketProfile.getBucket());
      PutObjectResult result = ossClient.putObject(bucketProfile.getBucket(), objectKey, inputStream);
      log.info("PutObjectResult {}", JSON.toJSONString(result));
      Assert.notNull(result.getETag(), "PutObjectResult.ETag must be not null");
      StoreResult storeResult = new StoreResult();
      storeResult.setBucket(bucketProfile.getBucket());
      storeResult.setStorePath(objectKey);
      storeResult.setStoreUrl(bucketProfile.getBaseUrl() + objectKey);
      return storeResult;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StoreResult putObject(String bucketKey, String objectKey, InputStream inputStream, ObjectMetadata metadata) {
    try {
      Assert.notNull(bucketKey, "bucketKey must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      Assert.notNull(inputStream, "inputStream must be not null");
      Assert.notNull(metadata, "metadata must be not null");
      BucketProfile bucketProfile = ossProperties.getBucketProfile(bucketKey);
      log.info("putObject bucketKey={}, bucketName={}", bucketKey, bucketProfile.getBucket());
      PutObjectResult result = ossClient.putObject(bucketProfile.getBucket(), objectKey, inputStream, metadata);
      log.info("PutObjectResult {}", JSON.toJSONString(result));
      Assert.notNull(result.getETag(), "PutObjectResult.ETag must be not null");
      StoreResult storeResult = new StoreResult();
      storeResult.setBucket(bucketProfile.getBucket());
      storeResult.setStorePath(objectKey);
      storeResult.setStoreUrl(bucketProfile.getBaseUrl() + objectKey);
      return storeResult;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StoreResult putObject(String bucketKey, String objectKey, File file, ObjectMetadata metadata) {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      return this.putObject(bucketKey, objectKey, inputStream, metadata);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StoreResult putObject(String bucketKey, String objectKey, File file) {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      return this.putObject(bucketKey, objectKey, inputStream);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StoreResult putObject(String bucketKey, String objectKey, byte[] bytes, ObjectMetadata metadata) {
    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
      return this.putObject(bucketKey, objectKey, inputStream, metadata);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StoreResult putObject(String bucketKey, String objectKey, byte[] bytes) {
    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
      return this.putObject(bucketKey, objectKey, inputStream);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StoreResult putObject(String bucketKey, PutObjectRequest putObjectRequest) {
    try {
      Assert.notNull(bucketKey, "bucketKey must be not null");
      Assert.notNull(putObjectRequest, "putObjectRequest must be not null");
      BucketProfile bucketProfile = ossProperties.getBucketProfile(bucketKey);
      putObjectRequest.setBucketName(bucketProfile.getBucket());
      log.info("PutObjectRequest bucketKey={}, bucketName={}", bucketKey, bucketProfile.getBucket());
      PutObjectResult result = ossClient.putObject(putObjectRequest);
      log.info("PutObjectResult {}", JSON.toJSONString(result));
      Assert.notNull(result.getETag(), "PutObjectResult.ETag must be not null");
      StoreResult storeResult = new StoreResult();
      storeResult.setBucket(bucketProfile.getBucket());
      storeResult.setStorePath(putObjectRequest.getKey());
      storeResult.setStoreUrl(bucketProfile.getBaseUrl() + putObjectRequest.getKey());
      return storeResult;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StoreResult copyObject(String fromBucketKey, String fromObjectKey, String toBucketKey, String toObjectKey, boolean isDelete) {
    try {
      BucketProfile fromBucketProfile = ossProperties.getBucketProfile(fromBucketKey);
      BucketProfile toBucketProfile = ossProperties.getBucketProfile(toBucketKey);
      log.info("copyObject fromBucketKey={}, fromObjectKey={}", fromBucketKey, fromObjectKey);
      log.info("copyObject toBucketKey={}, toObjectKey={}", toBucketKey, toObjectKey);
      boolean doesObjectExist = ossClient.doesObjectExist(fromBucketProfile.getBucket(), fromObjectKey);
      ExceptionUtil.business(ErrorCodeDefaults.S0400, "源文件不存在：fromObjectKey=" + fromObjectKey).doThrow(doesObjectExist);
      CopyObjectResult result = ossClient.copyObject(fromBucketProfile.getBucket(), fromObjectKey, toBucketProfile.getBucket(), toObjectKey);
      log.info("CopyObjectResult {}", JSON.toJSONString(result));
      Assert.notNull(result.getETag(), "CopyObjectResult.ETag must be not null");
      // 复制成功，删除源文件
      if (isDelete) {
        ossClient.deleteObject(fromBucketProfile.getBucket(), fromObjectKey);
      }
      StoreResult storeResult = new StoreResult();
      storeResult.setBucket(toBucketProfile.getBucket());
      storeResult.setStorePath(toObjectKey);
      storeResult.setStoreUrl(toBucketProfile.getBaseUrl() + toObjectKey);
      return storeResult;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件拷贝异常：" + e.getMessage());
    }
  }

}
