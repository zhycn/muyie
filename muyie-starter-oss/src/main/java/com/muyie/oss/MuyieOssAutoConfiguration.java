package com.muyie.oss;

import com.alibaba.fastjson2.JSON;
import com.aliyun.oss.OSS;
import com.muyie.oss.autoconfigure.OssProperties;
import com.muyie.oss.context.DefaultOssKeyGenerator;
import com.muyie.oss.context.OssKeyGenerator;
import com.muyie.oss.context.OssUploadCallback;
import com.muyie.oss.controller.OssUploadController;
import com.muyie.oss.service.OssService;
import com.muyie.oss.service.impl.OssServiceImpl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;

/**
 * @author larry.qi
 * @since 2.7.13
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "muyie.oss.enabled", havingValue = "true", matchIfMissing = true)
@Import({OssUploadController.class})
public class MuyieOssAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public OssService ossService(OSS ossClient, OssProperties ossProperties, OssUploadCallback callback) {
    return new OssServiceImpl(ossClient, ossProperties, callback);
  }

  @Bean
  @ConditionalOnMissingBean
  public OssKeyGenerator ossKeyGenerator() {
    return new DefaultOssKeyGenerator();
  }

  @Bean
  @ConditionalOnMissingBean
  public OssUploadCallback ossUploadCallback() {
    return storeResult -> {
      log.info("OssUploadCallback {}", JSON.toJSONString(storeResult));
    };
  }

}
