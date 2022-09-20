package com.muyie.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static com.muyie.security.AuthoritiesConstants.AUTHORIZATION_BEARER;
import static com.muyie.security.AuthoritiesConstants.AUTHORIZATION_HEADER;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a
 * valid user is found.
 *
 * @author larry.qi
 * @since 1.2.12
 */
public class JwtFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtFilter(final JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                       final FilterChain filterChain) throws IOException, ServletException {
    final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    final String jwt = resolveToken(httpServletRequest);
    if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
      final Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private String resolveToken(final HttpServletRequest request) {
    final String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTHORIZATION_BEARER)) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
