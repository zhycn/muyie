package com.muyie.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author larry.qi
 * @since 1.2.6
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@ComponentScan("com.muyie.aspectj")
public class MuyieAspectJAutoConfiguration {
}
