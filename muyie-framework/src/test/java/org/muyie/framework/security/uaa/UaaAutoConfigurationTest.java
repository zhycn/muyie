
package org.muyie.framework.security.uaa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.config.MuyieProperties;
import org.muyie.framework.config.MuyieProperties.Security.ClientAuthorization;
import org.muyie.framework.security.uaa.LoadBalancedResourceDetails;
import org.muyie.framework.security.uaa.UaaAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

public class UaaAutoConfigurationTest {

  private static final String ACCESS_TOKEN_URI = "http://access.token.uri/";
  private static final String TOKEN_SERVICE_ID = "tokkie";
  private static final String CLIENT_ID = "abacadabra";
  private static final String CLIENT_SECRET = "hush";

  private MuyieProperties properties;
  private ClientAuthorization authorization;
  private UaaAutoConfiguration config;

  @BeforeEach
  public void setup() {
    properties = new MuyieProperties();
    authorization = properties.getSecurity().getClientAuthorization();
    authorization.setAccessTokenUri(ACCESS_TOKEN_URI);
    authorization.setTokenServiceId(TOKEN_SERVICE_ID);
    authorization.setClientId(CLIENT_ID);
    authorization.setClientSecret(CLIENT_SECRET);
    config = new UaaAutoConfiguration(properties);
  }

  @Test
  public void testLoadBalancedResourceDetails() {
    LoadBalancedResourceDetails details = config.loadBalancedResourceDetails(null);
    assertThat(details.getAccessTokenUri()).isEqualTo(authorization.getAccessTokenUri());
    assertThat(details.getTokenServiceId()).isEqualTo(authorization.getTokenServiceId());
    assertThat(details.getClientId()).isEqualTo(authorization.getClientId());
    assertThat(details.getClientSecret()).isEqualTo(authorization.getClientSecret());
  }
}
