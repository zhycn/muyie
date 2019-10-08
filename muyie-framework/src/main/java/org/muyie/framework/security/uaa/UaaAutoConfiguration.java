package org.muyie.framework.security.uaa;

import org.muyie.framework.config.MuyieProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

/**
 * <p>
 * UaaAutoConfiguration class.
 * </p>
 */
@Configuration
@ConditionalOnClass({ClientCredentialsResourceDetails.class, LoadBalancerClient.class})
@ConditionalOnProperty("muyie.security.client-authorization.client-id")
public class UaaAutoConfiguration {

  private MuyieProperties muyieProperties;

  /**
   * <p>
   * Constructor for UaaAutoConfiguration.
   * </p>
   *
   * @param muyieProperties a {@link org.muyie.framework.config.MuyieProperties} object.
   */
  public UaaAutoConfiguration(MuyieProperties muyieProperties) {
    this.muyieProperties = muyieProperties;
  }

  /**
   * <p>
   * loadBalancedResourceDetails.
   * </p>
   *
   * @param loadBalancerClient a
   *        {@link org.springframework.cloud.client.loadbalancer.LoadBalancerClient} object.
   * @return a {@link org.muyie.framework.security.uaa.LoadBalancedResourceDetails} object.
   */
  @Bean
  public LoadBalancedResourceDetails loadBalancedResourceDetails(
      LoadBalancerClient loadBalancerClient) {
    LoadBalancedResourceDetails loadBalancedResourceDetails =
        new LoadBalancedResourceDetails(loadBalancerClient);
    MuyieProperties.Security.ClientAuthorization clientAuthorization =
        muyieProperties.getSecurity().getClientAuthorization();
    loadBalancedResourceDetails.setAccessTokenUri(clientAuthorization.getAccessTokenUri());
    loadBalancedResourceDetails.setTokenServiceId(clientAuthorization.getTokenServiceId());
    loadBalancedResourceDetails.setClientId(clientAuthorization.getClientId());
    loadBalancedResourceDetails.setClientSecret(clientAuthorization.getClientSecret());
    return loadBalancedResourceDetails;
  }
}
