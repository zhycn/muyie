package com.muyie.security.jwt;

import com.muyie.security.AuthoritiesConstants;
import com.muyie.security.MuyieSecurityProperties;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

/**
 * JWT 安全配置
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(SecurityFilterChain.class)
@AutoConfigureAfter(MuyieSecurityProperties.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import({SecurityProblemSupport.class, JwtTokenProvider.class, CorsFilter.class})
public class JwtSecurityConfiguration {

  private final JwtTokenProvider jwtTokenProvider;
  private final SecurityProblemSupport problemSupport;
  private final CorsFilter corsFilter;

  public JwtSecurityConfiguration(JwtTokenProvider jwtTokenProvider, SecurityProblemSupport problemSupport,
                                  CorsFilter corsFilter) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.problemSupport = problemSupport;
    this.corsFilter = corsFilter;
  }

  @Bean
  @ConditionalOnMissingBean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
      .antMatchers(HttpMethod.OPTIONS, "/**")
      .antMatchers("/**/*.{js,css,html}")
      .antMatchers("/i18n/**")
      .antMatchers("/content/**")
      .antMatchers("/h2-console/**")
      .antMatchers("/swagger-ui/index.html")
      .antMatchers("/test/**");
  }

  @Bean
  @ConditionalOnMissingBean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling()
      .authenticationEntryPoint(problemSupport)
      .accessDeniedHandler(problemSupport)
      .and().headers().frameOptions().disable()
      .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and().authorizeRequests()
      .antMatchers("/api/**").authenticated()
      .antMatchers("/management/health").permitAll()
      .antMatchers("/management/info").permitAll()
      .antMatchers("/management/**")
      .hasAuthority(AuthoritiesConstants.ROLE_ADMIN)
      .and().apply(securityConfigurerAdapter());
    return http.build();
  }

  @Bean
  @ConditionalOnMissingBean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.builder()
      .username("user")
      .password("password")
      .roles("USER")
      .build();
    UserDetails admin = User.builder()
      .username("admin")
      .password("password")
      .roles("ADMIN", "USER")
      .build();
    return new InMemoryUserDetailsManager(user, admin);
  }

  private JwtConfigurer securityConfigurerAdapter() {
    return new JwtConfigurer(jwtTokenProvider);
  }

}
