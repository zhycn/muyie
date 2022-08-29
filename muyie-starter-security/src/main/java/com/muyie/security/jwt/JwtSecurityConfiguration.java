package com.muyie.security.jwt;

import org.muyie.framework.config.MuyieProperties;
import org.muyie.framework.security.AuthoritiesConstants;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@AutoConfigureAfter(MuyieProperties.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import({SecurityProblemSupport.class, JwtTokenProvider.class})
public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;

  private final SecurityProblemSupport problemSupport;

  private final CorsFilter corsFilter;

  public JwtSecurityConfiguration(final JwtTokenProvider jwtTokenProvider, final SecurityProblemSupport problemSupport,
                                  final CorsFilter corsFilter) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.problemSupport = problemSupport;
    this.corsFilter = corsFilter;
  }

  @Override
  public void configure(final WebSecurity web) {
    web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/**/*.{js,html}").antMatchers("/i18n/**")
      .antMatchers("/content/**").antMatchers("/h2-console/**").antMatchers("/swagger-ui/index.html")
      .antMatchers("/test/**");
  }

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http.csrf().disable().addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling()
      .authenticationEntryPoint(problemSupport).accessDeniedHandler(problemSupport).and().headers().frameOptions()
      .disable().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeRequests().antMatchers("/api/**").authenticated().antMatchers("/management/health").permitAll()
      .antMatchers("/management/info").permitAll().antMatchers("/management/**")
      .hasAuthority(AuthoritiesConstants.ADMIN).and().apply(securityConfigurerAdapter());
  }

  private JwtConfigurer securityConfigurerAdapter() {
    return new JwtConfigurer(jwtTokenProvider);
  }

  @Bean
  @Override
  @ConditionalOnMissingBean(AuthenticationManager.class)
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Bean
  @ConditionalOnMissingBean(PasswordEncoder.class)
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
