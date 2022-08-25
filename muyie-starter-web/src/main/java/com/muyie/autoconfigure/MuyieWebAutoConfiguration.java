package com.muyie.autoconfigure;

import com.muyie.properties.MuyieProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author larry.qi
 * @since 1.2.6
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MuyieProperties.class)
public class MuyieWebAutoConfiguration {

}
