package org.muyie.framework.config.info;

import org.springframework.boot.actuate.autoconfigure.info.ConditionalOnEnabledInfoContributor;
import org.springframework.boot.actuate.autoconfigure.info.InfoContributorAutoConfiguration;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * MuYie auto-configuration for custom
 * {@link org.springframework.boot.actuate.info.InfoContributor}s.
 */
@Configuration
@AutoConfigureAfter(InfoContributorAutoConfiguration.class)
@ConditionalOnClass(InfoContributor.class)
public class MuyieInfoContributorConfiguration {

  /**
   * <p>
   * activeProfilesInfoContributor.
   * </p>
   *
   * @param environment a {@link org.springframework.core.env.ConfigurableEnvironment} object.
   * @return a {@link org.muyie.framework.config.info.ActiveProfilesInfoContributor} object.
   */
  @Bean
  @ConditionalOnEnabledInfoContributor("active-profiles")
  public ActiveProfilesInfoContributor activeProfilesInfoContributor(
      ConfigurableEnvironment environment) {
    return new ActiveProfilesInfoContributor(environment);
  }
}
