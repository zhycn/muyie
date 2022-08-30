package com.muyie.security;

/**
 * Constants for Spring Security authorities.
 *
 * @author larry.qi
 */
public interface AuthoritiesConstants {

  String ADMIN = "ROLE_ADMIN";

  String USER = "ROLE_USER";

  String TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";

  String ANONYMOUS = "ROLE_ANONYMOUS";

  String SYSTEM_USER = "system";

  String ANONYMOUS_USER = "anonymous";

}
