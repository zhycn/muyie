package com.muyie.oss.resource;

import com.aliyun.oss.OSS;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;

/**
 * @author larry
 * @since 2.7.13
 */
@Slf4j
public class OssStorageProtocolResolver implements ProtocolResolver, BeanFactoryPostProcessor, ResourceLoaderAware {
  public static final String PROTOCOL = "oss://";
  private ConfigurableListableBeanFactory beanFactory;
  private OSS oss;

  private OSS getOss() {
    if (this.oss == null) {
      if (this.beanFactory.getBeansOfType(OSS.class).size() > 1) {
        log.warn("There are multiple OSS instances, consider marking one of them as @Primary to resolve oss protocol.");
      }
      this.oss = this.beanFactory.getBean(OSS.class);
    }
    return this.oss;
  }

  @Override
  public Resource resolve(@NonNull String location, @Nullable ResourceLoader resourceLoader) {
    return !location.startsWith(PROTOCOL) ? null : new OssStorageResource(this.getOss(), location, this.beanFactory);
  }

  @Override
  public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
    if (DefaultResourceLoader.class.isAssignableFrom(resourceLoader.getClass())) {
      ((DefaultResourceLoader) resourceLoader).addProtocolResolver(this);
    } else {
      log.warn("The provided delegate resource loader is not an implementation of DefaultResourceLoader. Custom Protocol using oss:// prefix will not be enabled.");
    }
  }

  @Override
  public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }
}
