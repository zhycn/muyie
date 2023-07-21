package com.muyie.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author larry.qi
 * @since 2.7.13
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MuyieSecurityProperties.class)
public class MuyieSecurityAutoConfiguration {

}
