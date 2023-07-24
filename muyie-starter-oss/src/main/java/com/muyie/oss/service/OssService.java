package com.muyie.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.muyie.oss.model.BucketProfile;
import com.muyie.oss.model.StoreResult;

import java.io.File;
import java.io.InputStream;

/**
 * 简单文件操作接口（上传、下载、删除、复制）
 *
 * @author larry.qi
 * @since 2.7.14
 */
public interface OssService {

  /**
   * 阿里云OSS客户端
   *
   * @return OSS
   */
  OSS getOssClient();

  /**
   * 获取Bucket配置信息
   *
   * @param bucketKey key
   * @return 配置信息
   */
  BucketProfile getBucketProfile(String bucketKey);

  /**
   * 文件对象上传
   *
   * @param bucketKey   配置BucketProfile的key
   * @param objectKey   文件存储路径，e.g. temp/v1-2390293382.png
   * @param inputStream 文件对象
   * @return 上传结果
   */
  StoreResult putObject(String bucketKey, String objectKey, InputStream inputStream);

  /**
   * 文件对象上传
   *
   * @param bucketKey   配置BucketProfile的key
   * @param objectKey   文件存储路径，e.g. temp/v1-2390293382.png
   * @param inputStream 文件对象
   * @param metadata    对象元信息
   * @return 上传结果
   */
  StoreResult putObject(String bucketKey, String objectKey, InputStream inputStream, ObjectMetadata metadata);

  /**
   * 文件对象上传
   *
   * @param bucketKey 配置BucketProfile的key
   * @param objectKey 文件存储路径，e.g. temp/v1-2390293382.png
   * @param file      文件对象
   * @param metadata  对象元信息
   * @return 上传结果
   */
  StoreResult putObject(String bucketKey, String objectKey, File file, ObjectMetadata metadata);

  /**
   * 文件对象上传
   *
   * @param bucketKey 配置BucketProfile的key
   * @param objectKey 文件存储路径，e.g. temp/v1-2390293382.png
   * @param file      文件对象
   * @return 上传结果
   */
  StoreResult putObject(String bucketKey, String objectKey, File file);

  /**
   * 文件对象上传
   *
   * @param bucketKey 配置BucketProfile的key
   * @param objectKey 文件存储路径，e.g. temp/v1-2390293382.png
   * @param bytes     文件对象
   * @param metadata  对象元信息
   * @return 上传结果
   */
  StoreResult putObject(String bucketKey, String objectKey, byte[] bytes, ObjectMetadata metadata);

  /**
   * 文件对象上传
   *
   * @param bucketKey 配置BucketProfile的key
   * @param objectKey 文件存储路径，e.g. temp/v1-2390293382.png
   * @param bytes     文件对象
   * @return 上传结果
   */
  StoreResult putObject(String bucketKey, String objectKey, byte[] bytes);

  /**
   * 文件对象上传（上传网络流）
   *
   * @param bucketKey 配置BucketProfile的key
   * @param objectKey 文件存储路径，e.g. temp/v1-2390293382.png
   * @param url       资源网址
   * @param metadata  对象元信息
   * @return 上传结果
   */
  StoreResult putObject(String bucketKey, String objectKey, String url, ObjectMetadata metadata);

  /**
   * 文件对象上传（上传网络流）
   *
   * @param bucketKey 配置BucketProfile的key
   * @param objectKey 文件存储路径，e.g. temp/v1-2390293382.png
   * @param url       资源网址
   * @return 上传结果
   */
  StoreResult putObject(String bucketKey, String objectKey, String url);

  /**
   * 文件对象上传
   *
   * @param bucketKey        配置BucketProfile的key
   * @param putObjectRequest 原生的文件上传对象（bucketName通过bucketKey自动获取）
   * @return 上传结果
   */
  StoreResult putObject(String bucketKey, PutObjectRequest putObjectRequest);

  /**
   * 复制文件
   *
   * @param fromBucketKey 源文件配置BucketProfile的key
   * @param fromObjectKey 源文件存储路径
   * @param toBucketKey   目标文件配置BucketProfile的key
   * @param toObjectKey   目标文件存储路径
   * @param isDelete      是否删除源文件
   * @return 复制后的文件存储信息
   */
  StoreResult copyObject(String fromBucketKey, String fromObjectKey, String toBucketKey, String toObjectKey, boolean isDelete);

  /**
   * 获取文件
   *
   * @param bucketKey 配置BucketProfile的key
   * @param objectKey 文件存储路径，e.g. temp/v1-2390293382.png
   * @return 结果
   */
  OSSObject getObject(String bucketKey, String objectKey);

  /**
   * 删除文件
   *
   * @param bucketKey 配置BucketProfile的key
   * @param objectKey 文件存储路径，e.g. temp/v1-2390293382.png
   */
  void deleteObject(String bucketKey, String objectKey);

  /**
   * 获取对象信息
   *
   * @param bucketKey 配置BucketProfile的key
   * @param objectKey 文件存储路径，e.g. temp/v1-2390293382.png
   * @return 结果
   */
  ObjectMetadata getObjectMetadata(String bucketKey, String objectKey);

}
