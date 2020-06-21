package org.muyie.framework.config.async;

import org.muyie.framework.config.MuyieProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(MuyieProperties.class)
@EnableAsync
@EnableScheduling
public class MuyieAsyncConfiguration implements WebMvcConfigurer {

  private final MuyieProperties muyieProperties;

  public MuyieAsyncConfiguration(final MuyieProperties muyieProperties) {
    this.muyieProperties = muyieProperties;
  }

  @Bean("muyieAsyncConfigurer")
  @ConditionalOnMissingBean
  public MuyieAsyncConfigurer muyieAsyncConfigurer() {
    return new MuyieAsyncConfigurer(muyieProperties);
  }

  @Bean("muyieAsyncTaskExecutor")
  @ConditionalOnMissingBean
  public MuyieAsyncTaskExecutor muyieAsyncTaskExecutor() {
    return new MuyieAsyncTaskExecutor(muyieAsyncConfigurer().create());
  }

  @Override
  public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
    configurer.setTaskExecutor(muyieAsyncTaskExecutor());
  }

  @Bean("muyieSchedulingConfigurer")
  @ConditionalOnMissingBean
  public MuyieSchedulingConfigurer muyieSchedulingConfigurer() {
    return new MuyieSchedulingConfigurer(muyieProperties);
  }

  @Bean("muyieTaskExecutor")
  @ConditionalOnMissingBean
  public MuyieTaskExecutor muyieTaskExecutor() {
    return new MuyieTaskExecutor(muyieAsyncTaskExecutor(), muyieSchedulingConfigurer().scheduledTaskExecutor());
  }

}
