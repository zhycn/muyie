package com.muyie.security;

/**
 * Constants for Spring Security authorities.
 *
 * @author larry.qi
 * @since 1.2.12
 */
public interface AuthoritiesConstants {

  String ROLE_ADMIN = "ROLE_ADMIN";

  String ROLE_USER = "ROLE_USER";

  String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";

  String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

  String SYSTEM_USER = "system";

  String ANONYMOUS_USER = "anonymous";

  String AUTHORIZATION_HEADER = "Authorization";

  String AUTHORIZATION_BEARER = "Bearer ";

}
