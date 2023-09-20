package com.muyie.security.jwt;

import com.muyie.security.jwt.service.TokenCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author larry.qi
 * @since 2.7.13
 */
@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtTokenProvider jwtTokenProvider;
  private final TokenCacheManager tokenCacheManager;

  @Override
  public void configure(HttpSecurity http) {
    JwtFilter jwtFilter = new JwtFilter(jwtTokenProvider, tokenCacheManager);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
