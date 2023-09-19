package com.muyie.framework.config;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author larry.qi
 * @since 1.2.6
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MuyieProperties.class)
@EnableSpringUtil
public class MuyieWebAutoConfiguration {

}
