package com.muyie.orm.jpa;

import org.muyie.framework.security.AuthoritiesConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Configuration(JpaAutoConfiguration.AUDITOR_AWARE_REF)
@ConditionalOnMissingBean(name = JpaAutoConfiguration.AUDITOR_AWARE_REF)
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(AuthoritiesConstants.SYSTEM_USER);
  }

}
