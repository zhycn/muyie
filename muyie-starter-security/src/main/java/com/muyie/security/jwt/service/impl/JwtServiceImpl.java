package com.muyie.security.jwt.service.impl;

import com.muyie.security.jwt.JwtSecurityUtils;
import com.muyie.security.jwt.JwtTokenProvider;
import com.muyie.security.jwt.service.JwtService;
import com.muyie.security.jwt.service.TokenCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author larry.qi
 * @since 2.7.13
 */
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final TokenCacheManager tokenCacheManager;
  private final PasswordEncoder passwordEncoder;

  @Override
  public String login(String username, String password, boolean rememberMe, String... authorities) {
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
      AuthorityUtils.createAuthorityList(authorities));
    Authentication authentication = authenticationManager.authenticate(authToken);
    String token = jwtTokenProvider.createToken(authentication, rememberMe);
    tokenCacheManager.setCache(token);
    return token;
  }

  @Override
  public String login(String username, boolean rememberMe, String... authorities) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
      AuthorityUtils.createAuthorityList(authorities));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtTokenProvider.createToken(authentication, rememberMe);
    tokenCacheManager.setCache(token);
    return token;
  }

  @Override
  public void logout() {
    JwtSecurityUtils.getCurrentUserJwt().ifPresent(tokenCacheManager::removeCache);
  }

  @Override
  public String getPasswordHash(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  @Override
  public String getPasswordHashWithSalt(String rawPassword, String salt) {
    return getPasswordHash(getPasswordWithSalt(rawPassword, salt));
  }

  @Override
  public String getPasswordWithSalt(String rawPassword, String salt) {
    return rawPassword + "-" + salt;
  }

}
