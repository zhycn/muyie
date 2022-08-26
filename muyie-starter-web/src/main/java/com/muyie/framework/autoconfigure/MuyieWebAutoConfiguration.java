package com.muyie.framework.autoconfigure;

import com.muyie.framework.configure.SpringContextHolder;
import com.muyie.framework.configure.SpringEventPublisher;
import com.muyie.framework.properties.MuyieProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author larry.qi
 * @since 1.2.6
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MuyieProperties.class)
@Import({SpringContextHolder.class, SpringEventPublisher.class})
public class MuyieWebAutoConfiguration {

}
