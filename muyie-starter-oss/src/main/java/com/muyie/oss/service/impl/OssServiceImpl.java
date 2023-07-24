package com.muyie.oss.service.impl;

import com.alibaba.fastjson2.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.muyie.exception.ErrorCodeDefaults;
import com.muyie.exception.ExceptionUtil;
import com.muyie.oss.autoconfigure.OssProperties;
import com.muyie.oss.model.BucketProfile;
import com.muyie.oss.model.StoreResult;
import com.muyie.oss.service.OssService;

import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单文件操作实现
 *
 * @author larry.qi
 * @since 2.7.14
 */
@Slf4j
@RequiredArgsConstructor
public class OssServiceImpl implements OssService {

  private final OSS ossClient;
  private final OssProperties ossProperties;

  @Override
  public OSS getOssClient() {
    return ossClient;
  }

  @Override
  public BucketProfile getBucketProfile(String bucketKey) {
    return ossProperties.getBucketProfile(bucketKey);
  }

  @Override
  public StoreResult putObject(String bucketKey, String objectKey, InputStream inputStream) {
    try {
      Assert.notNull(bucketKey, "bucketKey must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      Assert.notNull(inputStream, "inputStream must be not null");
      BucketProfile bp = ossProperties.getBucketProfile(bucketKey);
      log.info("putObject bucketKey={}, bucketName={}", bucketKey, bp.getBucket());
      log.info("putObject objectKey={}", objectKey);
      PutObjectResult result = ossClient.putObject(bp.getBucket(), objectKey, inputStream);
      Assert.notNull(result, "PutObjectResult must be not null");
      log.info("PutObjectResult {}", JSON.toJSONString(result));
      StoreResult storeResult = new StoreResult();
      storeResult.setBucket(bp.getBucket());
      storeResult.setStorePath(objectKey);
      storeResult.setStoreUrl(bp.getBaseUrl() + objectKey);
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
      BucketProfile bp = ossProperties.getBucketProfile(bucketKey);
      log.info("putObject bucketKey={}, bucketName={}", bucketKey, bp.getBucket());
      log.info("putObject objectKey={}", objectKey);
      PutObjectResult result = ossClient.putObject(bp.getBucket(), objectKey, inputStream, metadata);
      Assert.notNull(result, "PutObjectResult must be not null");
      log.info("PutObjectResult {}", JSON.toJSONString(result));
      StoreResult storeResult = new StoreResult();
      storeResult.setBucket(bp.getBucket());
      storeResult.setStorePath(objectKey);
      storeResult.setStoreUrl(bp.getBaseUrl() + objectKey);
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
  public StoreResult putObject(String bucketKey, String objectKey, String url, ObjectMetadata metadata) {
    try {
      return this.putObject(bucketKey, objectKey, new URL(url).openStream(), metadata);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StoreResult putObject(String bucketKey, String objectKey, String url) {
    try {
      return this.putObject(bucketKey, objectKey, new URL(url).openStream());
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StoreResult putObject(String bucketKey, PutObjectRequest putObjectRequest) {
    try {
      Assert.notNull(bucketKey, "bucketKey must be not null");
      Assert.notNull(putObjectRequest, "putObjectRequest must be not null");
      BucketProfile bp = ossProperties.getBucketProfile(bucketKey);
      putObjectRequest.setBucketName(bp.getBucket());
      log.info("putObject bucketKey={}, bucketName={}", bucketKey, bp.getBucket());
      log.info("putObject objectKey={}", putObjectRequest.getKey());
      PutObjectResult result = ossClient.putObject(putObjectRequest);
      Assert.notNull(result, "PutObjectResult must be not null");
      log.info("PutObjectResult {}", JSON.toJSONString(result));
      StoreResult storeResult = new StoreResult();
      storeResult.setBucket(bp.getBucket());
      storeResult.setStorePath(putObjectRequest.getKey());
      storeResult.setStoreUrl(bp.getBaseUrl() + putObjectRequest.getKey());
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
      ExceptionUtil.business(ErrorCodeDefaults.S0400, "源文件不存在：fromObjectKey=" + fromObjectKey).doThrow(!doesObjectExist);
      CopyObjectResult result = ossClient.copyObject(fromBucketProfile.getBucket(), fromObjectKey, toBucketProfile.getBucket(), toObjectKey);
      Assert.notNull(result, "CopyObjectResult must be not null");
      log.info("CopyObjectResult {}", JSON.toJSONString(result));
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
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, e.getMessage()).rewrite("复制文件失败");
    }
  }

  @Override
  public OSSObject getObject(String bucketKey, String objectKey) {
    try {
      Assert.notNull(bucketKey, "bucketKey must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      BucketProfile bp = ossProperties.getBucketProfile(bucketKey);
      log.info("OSSObject bucketKey={}, bucketName={}", bucketKey, bp.getBucket());
      log.info("OSSObject objectKey={}", objectKey);
      OSSObject result = ossClient.getObject(bp.getBucket(), objectKey);
      Assert.notNull(result, "OSSObject must be not null");
      return result;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, e.getMessage()).rewrite("获取文件失败");
    }
  }

  @Override
  public void deleteObject(String bucketKey, String objectKey) {
    try {
      Assert.notNull(bucketKey, "bucketKey must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      BucketProfile bp = ossProperties.getBucketProfile(bucketKey);
      log.info("deleteObject bucketKey={}, bucketName={}", bucketKey, bp.getBucket());
      log.info("deleteObject objectKey={}", objectKey);
      ossClient.deleteObject(bp.getBucket(), objectKey);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, e.getMessage()).rewrite("删除文件失败");
    }
  }

  @Override
  public ObjectMetadata getObjectMetadata(String bucketKey, String objectKey) {
    try {
      Assert.notNull(bucketKey, "bucketKey must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      BucketProfile bp = ossProperties.getBucketProfile(bucketKey);
      log.info("ObjectMetadata bucketKey={}, bucketName={}", bucketKey, bp.getBucket());
      log.info("ObjectMetadata objectKey={}", objectKey);
      ObjectMetadata result = ossClient.getObjectMetadata(bp.getBucket(), objectKey);
      Assert.notNull(result, "ObjectMetadata must be not null");
      return result;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, e.getMessage()).rewrite("获取对象元信息失败");
    }
  }

}
