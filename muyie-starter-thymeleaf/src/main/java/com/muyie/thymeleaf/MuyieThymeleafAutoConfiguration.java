package com.muyie.thymeleaf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 保持默认配置即可，开发时可关闭缓存
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Configuration(proxyBeanMethods = false)
public class MuyieThymeleafAutoConfiguration implements WebMvcConfigurer {

}
