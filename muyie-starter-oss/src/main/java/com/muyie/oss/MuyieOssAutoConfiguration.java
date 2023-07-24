package com.muyie.oss;

import com.aliyun.oss.OSS;
import com.muyie.oss.autoconfigure.OssProperties;
import com.muyie.oss.service.OssService;
import com.muyie.oss.service.impl.OssServiceImpl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author larry.qi
 * @since 2.7.13
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "muyie.oss.enabled", havingValue = "true", matchIfMissing = true)
public class MuyieOssAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public OssService ossService(OSS ossClient, OssProperties ossProperties) {
    return new OssServiceImpl(ossClient, ossProperties);
  }

}
