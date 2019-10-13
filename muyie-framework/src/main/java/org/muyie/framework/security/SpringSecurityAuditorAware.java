package org.muyie.framework.security;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Configuration
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(AuthoritiesConstants.SYSTEM_USER));
  }
}