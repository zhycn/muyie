package com.muyie.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * MuyieSecurityProperties
 *
 * @author larry.qi
 * @since 2.7.13
 */
@Data
@ConfigurationProperties(prefix = "muyie.security")
public class MuyieSecurityProperties {

  private final ClientAuthorization clientAuthorization = new ClientAuthorization();

  private final Authentication authentication = new Authentication();

  @Data
  public static class ClientAuthorization {

    private String accessTokenUri = MuyieSecurityDefaults.ClientAuthorization.ACCESS_TOKEN_URI;

    private String tokenServiceId = MuyieSecurityDefaults.ClientAuthorization.TOKEN_SERVICE_ID;

    private String clientId = MuyieSecurityDefaults.ClientAuthorization.CLIENT_ID;

    private String clientSecret = MuyieSecurityDefaults.ClientAuthorization.CLIENT_SECRET;

  }

  @Data
  public static class Authentication {

    private final Jwt jwt = new Jwt();

    @Data
    public static class Jwt {

      private String secret = MuyieSecurityDefaults.Authentication.Jwt.SECRET;

      private String base64Secret = MuyieSecurityDefaults.Authentication.Jwt.BASE64_SECRET;

      private long tokenValidityInSeconds = MuyieSecurityDefaults.Authentication.Jwt.TOKEN_VALIDITY_IN_SECONDS;

      private long tokenValidityInSecondsForRememberMe = MuyieSecurityDefaults.Authentication.Jwt.TOKEN_VALIDITY_IN_SECONDS_FOR_REMEMBER_ME;

    }
  }

}
