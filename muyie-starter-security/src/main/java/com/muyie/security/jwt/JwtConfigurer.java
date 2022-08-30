package com.muyie.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author larry.qi
 */
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtConfigurer(final JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void configure(final HttpSecurity http) {
    final JwtFilter jwtFilter = new JwtFilter(jwtTokenProvider);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
