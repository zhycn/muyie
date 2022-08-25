package com.muyie.autoconfigure;

import com.muyie.aspectj.CatchAndLogAspect;
import com.muyie.aspectj.StopWatchAspect;
import com.muyie.properties.MuyieProperties;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author larry.qi
 * @since 1.2.6
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(MuyieProperties.class)
@EnableAspectJAutoProxy
@Import({CatchAndLogAspect.class, StopWatchAspect.class})
public class MuyieAspectJAutoConfiguration {
}
