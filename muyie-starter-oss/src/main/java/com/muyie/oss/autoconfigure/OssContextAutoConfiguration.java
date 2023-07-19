package com.muyie.oss.autoconfigure;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.utils.StringUtils;
import com.muyie.oss.env.OssProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author larry
 * @since 2.7.13
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(OssAutoConfiguration.class)
@ConditionalOnProperty(prefix = "muyie.oss", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties({OssProperties.class})
public class OssContextAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public OSS ossClient(OssProperties ossProperties) {
    Assert.isTrue(!StringUtils.isEmpty(ossProperties.getEndpoint()), "Oss endpoint can't be empty.");
    Assert.isTrue(!StringUtils.isEmpty(ossProperties.getAccessKeyId()), "AccessKeyId can't be empty.");
    Assert.isTrue(!StringUtils.isEmpty(ossProperties.getSecretAccessKey()), "SecretAccessKey can't be empty.");
    if (!StringUtils.isEmpty(ossProperties.getSecurityToken())) {
      return (new OSSClientBuilder()).build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getSecretAccessKey(), ossProperties.getSecurityToken(), ossProperties.getConfig());
    }
    return (new OSSClientBuilder()).build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getSecretAccessKey(), ossProperties.getConfig());
  }
}
