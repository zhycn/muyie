package org.muyie.framework.domain.jpa;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration("muyie.jpaAutoConfiguration")
@ConditionalOnClass({AuditorAware.class, DataSource.class, JpaRepository.class})
@ConditionalOnProperty(prefix = "spring.data.jpa.repositories", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableJpaAuditing(auditorAwareRef = JpaAutoConfiguration.AUDITOR_AWARE_REF)
@EnableTransactionManagement
@Import(SpringSecurityAuditorAware.class)
public class JpaAutoConfiguration {

  public static final String AUDITOR_AWARE_REF = "muyie.springSecurityAuditorAware";

}
