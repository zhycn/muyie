package com.muyie.oss.service.impl;

import com.alibaba.fastjson2.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import com.muyie.exception.ErrorCodeDefaults;
import com.muyie.exception.ExceptionUtil;
import com.muyie.oss.autoconfigure.OssProperties;
import com.muyie.oss.context.OssUploadCallback;
import com.muyie.oss.model.StorageConfig;
import com.muyie.oss.model.StorageInfo;
import com.muyie.oss.service.OssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * 简单文件操作实现（上传、下载、删除、复制）
 *
 * @author larry.qi
 * @since 2.7.14
 */
@Slf4j
@RequiredArgsConstructor
public class OssServiceImpl implements OssService {

  private final OSS ossClient;
  private final OssProperties ossProperties;
  private final OssUploadCallback ossUploadCallback;

  @Override
  public OSS getOssClient() {
    return ossClient;
  }

  @Override
  public StorageConfig getStorageConfig(String key) {
    return ossProperties.getStorageConfig(key);
  }

  @Override
  public StorageInfo putObject(String key, String objectKey, InputStream inputStream) {
    try {
      Assert.notNull(key, "key must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      Assert.notNull(inputStream, "inputStream must be not null");
      StorageConfig config = ossProperties.getStorageConfig(key);
      log.info("putObject key={}, bucketName={}", key, config.getBucket());
      log.info("putObject objectKey={}", objectKey);
      PutObjectResult result = ossClient.putObject(config.getBucket(), objectKey, inputStream);
      Assert.notNull(result, "PutObjectResult must be not null");
      log.info("PutObjectResult {}", JSON.toJSONString(result));
      StorageInfo storageInfo = new StorageInfo();
      storageInfo.setBucket(config.getBucket());
      storageInfo.setObjectKey(objectKey);
      storageInfo.setObjectUrl(config.getBaseUrl() + objectKey);
      storageInfo.setEtag(result.getETag());
      ossUploadCallback.callback(storageInfo);
      return storageInfo;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StorageInfo putObject(String key, String objectKey, InputStream inputStream, ObjectMetadata metadata) {
    try {
      Assert.notNull(key, "key must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      Assert.notNull(inputStream, "inputStream must be not null");
      Assert.notNull(metadata, "metadata must be not null");
      StorageConfig config = ossProperties.getStorageConfig(key);
      log.info("putObject key={}, bucketName={}", key, config.getBucket());
      log.info("putObject objectKey={}", objectKey);
      PutObjectResult result = ossClient.putObject(config.getBucket(), objectKey, inputStream, metadata);
      Assert.notNull(result, "PutObjectResult must be not null");
      log.info("PutObjectResult {}", JSON.toJSONString(result));
      StorageInfo storageInfo = new StorageInfo();
      storageInfo.setBucket(config.getBucket());
      storageInfo.setObjectKey(objectKey);
      storageInfo.setObjectUrl(config.getBaseUrl() + objectKey);
      storageInfo.setEtag(result.getETag());
      ossUploadCallback.callback(storageInfo);
      return storageInfo;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StorageInfo putObject(String key, String objectKey, File file, ObjectMetadata metadata) {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      return this.putObject(key, objectKey, inputStream, metadata);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StorageInfo putObject(String key, String objectKey, File file) {
    try (FileInputStream inputStream = new FileInputStream(file)) {
      return this.putObject(key, objectKey, inputStream);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StorageInfo putObject(String key, String objectKey, byte[] bytes, ObjectMetadata metadata) {
    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
      return this.putObject(key, objectKey, inputStream, metadata);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StorageInfo putObject(String key, String objectKey, byte[] bytes) {
    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
      return this.putObject(key, objectKey, inputStream);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StorageInfo putObject(String key, String objectKey, String url, ObjectMetadata metadata) {
    try {
      return this.putObject(key, objectKey, new URL(url).openStream(), metadata);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StorageInfo putObject(String key, String objectKey, String url) {
    try {
      return this.putObject(key, objectKey, new URL(url).openStream());
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StorageInfo putObject(String key, PutObjectRequest putObjectRequest) {
    try {
      Assert.notNull(key, "key must be not null");
      Assert.notNull(putObjectRequest, "putObjectRequest must be not null");
      StorageConfig config = ossProperties.getStorageConfig(key);
      putObjectRequest.setBucketName(config.getBucket());
      log.info("putObject key={}, bucketName={}", key, config.getBucket());
      log.info("putObject objectKey={}", putObjectRequest.getKey());
      PutObjectResult result = ossClient.putObject(putObjectRequest);
      Assert.notNull(result, "PutObjectResult must be not null");
      log.info("PutObjectResult {}", JSON.toJSONString(result));
      StorageInfo storageInfo = new StorageInfo();
      storageInfo.setBucket(config.getBucket());
      storageInfo.setObjectKey(putObjectRequest.getKey());
      storageInfo.setObjectUrl(config.getBaseUrl() + putObjectRequest.getKey());
      storageInfo.setEtag(result.getETag());
      ossUploadCallback.callback(storageInfo);
      return storageInfo;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, "文件上传失败：" + e.getMessage());
    }
  }

  @Override
  public StorageInfo copyObject(String fromKey, String fromObjectKey, String toKey, String toObjectKey, boolean isDelete) {
    try {
      StorageConfig fromStorageConfig = ossProperties.getStorageConfig(fromKey);
      StorageConfig toStorageConfig = ossProperties.getStorageConfig(toKey);
      log.info("copyObject fromKey={}, fromObjectKey={}", fromKey, fromObjectKey);
      log.info("copyObject toKey={}, toObjectKey={}", toKey, toObjectKey);
      boolean doesObjectExist = ossClient.doesObjectExist(fromStorageConfig.getBucket(), fromObjectKey);
      ExceptionUtil.business(ErrorCodeDefaults.S0400, "源文件不存在：fromObjectKey=" + fromObjectKey).doThrow(!doesObjectExist);
      CopyObjectResult result = ossClient.copyObject(fromStorageConfig.getBucket(), fromObjectKey, toStorageConfig.getBucket(), toObjectKey);
      Assert.notNull(result, "CopyObjectResult must be not null");
      log.info("CopyObjectResult {}", JSON.toJSONString(result));
      // 复制成功，删除源文件
      if (isDelete) {
        ossClient.deleteObject(fromStorageConfig.getBucket(), fromObjectKey);
      }
      StorageInfo storageInfo = new StorageInfo();
      storageInfo.setBucket(toStorageConfig.getBucket());
      storageInfo.setObjectKey(toObjectKey);
      storageInfo.setObjectUrl(toStorageConfig.getBaseUrl() + toObjectKey);
      storageInfo.setEtag(result.getETag());
      ossUploadCallback.callback(storageInfo);
      return storageInfo;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, e.getMessage()).rewrite("复制文件失败");
    }
  }

  @Override
  public OSSObject getObject(String key, String objectKey) {
    try {
      Assert.notNull(key, "key must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      StorageConfig config = ossProperties.getStorageConfig(key);
      log.info("OSSObject key={}, bucketName={}", key, config.getBucket());
      log.info("OSSObject objectKey={}", objectKey);
      OSSObject result = ossClient.getObject(config.getBucket(), objectKey);
      Assert.notNull(result, "OSSObject must be not null");
      return result;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, e.getMessage()).rewrite("获取文件失败");
    }
  }

  @Override
  public void deleteObject(String key, String objectKey) {
    try {
      Assert.notNull(key, "key must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      StorageConfig config = ossProperties.getStorageConfig(key);
      log.info("deleteObject key={}, bucketName={}", key, config.getBucket());
      log.info("deleteObject objectKey={}", objectKey);
      ossClient.deleteObject(config.getBucket(), objectKey);
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, e.getMessage()).rewrite("删除文件失败");
    }
  }

  @Override
  public ObjectMetadata getObjectMetadata(String key, String objectKey) {
    try {
      Assert.notNull(key, "key must be not null");
      Assert.notNull(objectKey, "objectKey must be not null");
      StorageConfig config = ossProperties.getStorageConfig(key);
      log.info("ObjectMetadata key={}, bucketName={}", key, config.getBucket());
      log.info("ObjectMetadata objectKey={}", objectKey);
      ObjectMetadata result = ossClient.getObjectMetadata(config.getBucket(), objectKey);
      Assert.notNull(result, "ObjectMetadata must be not null");
      return result;
    } catch (Exception e) {
      throw ExceptionUtil.business(ErrorCodeDefaults.A0700, e.getMessage()).rewrite("获取对象元信息失败");
    }
  }

}
