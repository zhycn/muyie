package com.muyie.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author larry.qi
 * @since 1.2.12
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MuyieSecurityProperties.class)
public class MuyieSecurityAutoConfiguration {

}
