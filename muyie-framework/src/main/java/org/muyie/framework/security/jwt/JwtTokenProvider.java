package org.muyie.framework.security.jwt;

import org.apache.commons.lang3.StringUtils;
import org.muyie.framework.config.MuyieProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;

/**
 * JWT token provider
 */
@Component
public class JwtTokenProvider {

  private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

  private static final JacksonDeserializer JACKSON_DESERIALIZER = new JacksonDeserializer();
  private static final JacksonSerializer JACKSON_SERIALIZER = new JacksonSerializer();

  private static final String AUTHORITIES_KEY = "auth";
  private final MuyieProperties muyieProperties;
  private Key key;
  private long tokenValidityInMilliseconds;
  private long tokenValidityInMillisecondsForRememberMe;

  public JwtTokenProvider(final MuyieProperties muyieProperties) {
    this.muyieProperties = muyieProperties;
  }

  public static void getJwtSecretKey(final String data) {
    final String secretKey = new HMac(HmacAlgorithm.HmacSHA512).digestHex(data);
    System.out.println("Using a JWT secret key: " + secretKey);
    String base64Secret = Base64Utils.encodeToUrlSafeString(secretKey.getBytes());
    System.out.println("Using a Base64-encoded JWT secret key: " + base64Secret);
  }

  @PostConstruct
  public void init() {
    byte[] keyBytes;
    final String secret = muyieProperties.getSecurity().getAuthentication().getJwt().getSecret();
    if (!StringUtils.isEmpty(secret)) {
      log.warn("Warning: the JWT key used is not Base64-encoded. "
        + "We recommend using the `muyie.security.authentication.jwt.base64-secret` key for optimum security.");
      keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    } else {
      log.debug("Using a Base64-encoded JWT secret key");
      keyBytes = Decoders.BASE64.decode(muyieProperties.getSecurity().getAuthentication().getJwt().getBase64Secret());
    }
    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.tokenValidityInMilliseconds = 1000
      * muyieProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
    this.tokenValidityInMillisecondsForRememberMe = 1000
      * muyieProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
  }

  public String createToken(final Authentication authentication, final boolean rememberMe) {
    final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    final long now = (new Date()).getTime();
    Date validity;
    if (rememberMe) {
      validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
    } else {
      validity = new Date(now + this.tokenValidityInMilliseconds);
    }

    return Jwts.builder().serializeToJsonWith(JACKSON_SERIALIZER).setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
      .signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();
  }

  public Authentication getAuthentication(final String token) {
    final Claims claims = Jwts.parserBuilder().deserializeJsonWith(JACKSON_DESERIALIZER).setSigningKey(key).build().parseClaimsJws(token).getBody();

    final Collection<? extends GrantedAuthority> authorities = Arrays
      .stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());

    final User principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public boolean validateToken(final String token) {
    try {
      Jwts.parserBuilder().deserializeJsonWith(JACKSON_DESERIALIZER).setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature.");
      log.trace("Invalid JWT signature trace", e);
    } catch (final ExpiredJwtException e) {
      log.info("Expired JWT token.");
      log.trace("Expired JWT token trace", e);
    } catch (final UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
      log.trace("Unsupported JWT token trace", e);
    } catch (final IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
      log.trace("JWT token compact of handler are invalid trace", e);
    }
    return false;
  }

}
