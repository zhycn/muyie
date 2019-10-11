package org.muyie.framework.config.aspectj;

import org.muyie.framework.config.MuyieProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@AutoConfigureAfter(MuyieProperties.class)
@Import({StopWatchConfiguration.class})
public class AspectJAutoConfiguration {

}
