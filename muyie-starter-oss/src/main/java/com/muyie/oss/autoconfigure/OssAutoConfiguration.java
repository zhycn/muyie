package com.muyie.oss.autoconfigure;

import com.aliyun.oss.OSS;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.muyie.oss.resource.OssStorageProtocolResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author larry
 * @since 2.7.13
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({OSS.class})
@ConditionalOnProperty(name = "muyie.oss.enabled", havingValue = "true", matchIfMissing = true)
public class OssAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public OssStorageProtocolResolver ossStorageProtocolResolver() {
    return new OssStorageProtocolResolver();
  }

  @Bean(name = "ossTaskExecutor")
  @ConditionalOnMissingBean
  public ExecutorService ossTaskExecutor() {
    int coreSize = Runtime.getRuntime().availableProcessors();
    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("oss-pool-").build();
    return new ThreadPoolExecutor(coreSize, 128, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory);
  }
}
