package com.muyie.security.jwt;

import com.muyie.security.MuyieSecurityProperties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT token provider
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Slf4j
@Component
public class JwtTokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private final MuyieSecurityProperties properties;
  private Key key;
  private long tokenValidityInMilliseconds;
  private long tokenValidityInMillisecondsForRememberMe;

  public JwtTokenProvider(MuyieSecurityProperties properties) {
    this.properties = properties;
  }

  /**
   * 随机生成一个 JWT key
   */
  public static void getJwtSecretKey() {
    getJwtSecretKey(IdUtil.fastSimpleUUID());
  }

  /**
   * 指定参数生成一个 JWT key
   */
  public static void getJwtSecretKey(String data) {
    String secretKey = new HMac(HmacAlgorithm.HmacSHA512).digestHex(data);
    System.out.println("Using a JWT secret key: " + secretKey);
    String base64Secret = Base64Utils.encodeToUrlSafeString(secretKey.getBytes());
    System.out.println("Using a Base64-encoded JWT secret key: " + base64Secret);
  }

  @PostConstruct
  public void init() {
    byte[] keyBytes;
    String secret = properties.getAuthentication().getJwt().getSecret();
    if (!StringUtils.isEmpty(secret)) {
      log.warn("Warning: the JWT key used is not Base64-encoded. "
        + "We recommend using the `muyie.security.authentication.jwt.base64-secret` key for optimum security.");
      keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    } else {
      log.info("Using a Base64-encoded JWT secret key");
      keyBytes = Base64Utils.decodeFromUrlSafeString(properties.getAuthentication().getJwt().getBase64Secret());
    }
    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.tokenValidityInMilliseconds = 1000
      * properties.getAuthentication().getJwt().getTokenValidityInSeconds();
    this.tokenValidityInMillisecondsForRememberMe = 1000
      * properties.getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
  }

  public String createToken(Authentication authentication, boolean rememberMe) {
    String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity;
    if (rememberMe) {
      validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
    } else {
      validity = new Date(now + this.tokenValidityInMilliseconds);
    }

    return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
      .signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

    Collection<? extends GrantedAuthority> authorities = Arrays
      .stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature.");
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token.");
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
    } catch (IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
    }
    return false;
  }

}
