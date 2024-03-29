package com.muyie.framework.aspectj;

import com.muyie.framework.config.MuyieProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author larry.qi
 * @since 2.7.13
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(MuyieProperties.class)
@EnableAspectJAutoProxy
@Import({CatchAndLogAspect.class, StopWatchAspect.class})
public class MuyieAspectJAutoConfiguration {
}
