package com.muyie.security;

public interface MuyieSecurityDefaults {

  interface ClientAuthorization {

    String ACCESS_TOKEN_URI = null;
    String TOKEN_SERVICE_ID = null;
    String CLIENT_ID = null;
    String CLIENT_SECRET = null;
  }

  interface Authentication {

    interface Jwt {

      String SECRET = null;
      String BASE64_SECRET = null;
      long TOKEN_VALIDITY_IN_SECONDS = 1800; // 30 minutes
      long TOKEN_VALIDITY_IN_SECONDS_FOR_REMEMBER_ME = 2592000; // 30 days
    }
  }

  interface RememberMe {

    String KEY = null;
  }

}
