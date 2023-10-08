package com.muyie.oss.context;

import com.muyie.oss.model.StorageInfo;

/**
 * 对象上传成功的结果回调
 *
 * @author larry
 * @since 2.7.14
 */
@FunctionalInterface
public interface OssUploadCallback {

  /**
   * 上传结果回调，主要用于管理对象名称
   *
   * @param storageInfo 上传结果
   */
  void callback(StorageInfo storageInfo);

}
