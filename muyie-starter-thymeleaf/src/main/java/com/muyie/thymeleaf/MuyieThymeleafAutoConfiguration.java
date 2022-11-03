package com.muyie.thymeleaf;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author larry.qi
 * @since 1.3.1
 */
@Configuration(proxyBeanMethods = false)
public class MuyieThymeleafAutoConfiguration implements WebMvcConfigurer {

  private final ThymeleafProperties thymeleafProperties;

  public MuyieThymeleafAutoConfiguration(ThymeleafProperties thymeleafProperties) {
    this.thymeleafProperties = thymeleafProperties;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 配置映射关系
    registry.addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/")
      // 新增 resourceChain 配置即开启缓存配置。
      // 不知道为何不加这个配置，设置了 webjars-locator 未生效。
      // 生产时建议开启缓存（只是缓存了资源路径而不是资源内容）,开发环境是可以设置为false
      .resourceChain(thymeleafProperties.isCache());
  }

}
