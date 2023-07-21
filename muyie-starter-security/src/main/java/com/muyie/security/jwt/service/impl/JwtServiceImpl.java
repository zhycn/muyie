package com.muyie.security.jwt.service.impl;

import com.muyie.security.jwt.JwtTokenProvider;
import com.muyie.security.jwt.service.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author larry.qi
 * @since 2.7.13
 */
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  @Override
  public String login(String username, String password, boolean rememberMe, String... authorities) {
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
      AuthorityUtils.createAuthorityList(authorities));
    Authentication authentication = authenticationManager.authenticate(authToken);
    return jwtTokenProvider.createToken(authentication, rememberMe);
  }

  @Override
  public String login(String username, boolean rememberMe, String... authorities) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
      AuthorityUtils.createAuthorityList(authorities));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtTokenProvider.createToken(authentication, rememberMe);
  }

  @Override
  public String getPasswordHash(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  @Override
  public String getPasswordHashWithSalt(String rawPassword, String salt) {
    return getPasswordHash(rawPassword + "-" + salt);
  }

  @Override
  public String getPasswordWithSalt(String rawPassword, String salt) {
    return rawPassword + "-" + salt;
  }

}
