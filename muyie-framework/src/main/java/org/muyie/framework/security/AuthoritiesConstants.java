package org.muyie.framework.security;

/**
 * Constants for Spring Security authorities.
 */
public interface AuthoritiesConstants {

  String ADMIN = "ROLE_ADMIN";

  String USER = "ROLE_USER";

  String TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";

  String ANONYMOUS = "ROLE_ANONYMOUS";

  // system user
  String SYSTEM_USER = "system";
  
  // anonymous user
  String ANONYMOUS_USER = "anonymous";

}
