package com.muyie.security.jwt;

import com.muyie.security.jwt.service.TokenCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.muyie.security.AuthoritiesConstants.AUTHORIZATION_BEARER;
import static com.muyie.security.AuthoritiesConstants.AUTHORIZATION_HEADER;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a
 * valid user is found.
 *
 * @author larry.qi
 * @since 2.7.13
 */
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;
  private final TokenCacheManager tokenCacheManager;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    String token = resolveToken(httpServletRequest);
    if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token) && tokenCacheManager.validateCache(token)) {
      Authentication authentication = jwtTokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTHORIZATION_BEARER)) {
      return bearerToken.substring(AUTHORIZATION_BEARER.length());
    }
    return null;
  }
}
