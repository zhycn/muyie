package org.muyie.framework.data.jpa;

import java.util.Optional;

import org.muyie.framework.security.AuthoritiesConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Configuration("springSecurityAuditorAware")
@ConditionalOnMissingBean(name = "springSecurityAuditorAware")
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(AuthoritiesConstants.SYSTEM_USER);
  }

}
