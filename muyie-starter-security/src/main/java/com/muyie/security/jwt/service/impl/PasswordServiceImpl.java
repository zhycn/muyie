package com.muyie.security.jwt.service.impl;

import com.muyie.security.jwt.service.PasswordService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * @author larry.qi
 * @since 2.7.14
 */
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

  private final PasswordEncoder passwordEncoder;

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
