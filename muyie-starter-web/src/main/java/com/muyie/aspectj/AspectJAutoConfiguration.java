package com.muyie.aspectj;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author larry.qi
 * @since 1.2.6
 */
@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
public class AspectJAutoConfiguration {
}
