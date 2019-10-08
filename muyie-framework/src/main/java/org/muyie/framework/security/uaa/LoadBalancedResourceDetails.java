package org.muyie.framework.security.uaa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p>
 * LoadBalancedResourceDetails class.
 * </p>
 */
@ConditionalOnMissingBean
public class LoadBalancedResourceDetails extends ClientCredentialsResourceDetails {

  /** Constant <code>EXCEPTION_MESSAGE="Returning an invalid URI: {}"</code> */
  public static final String EXCEPTION_MESSAGE = "Returning an invalid URI: {}";

  private final Logger log = LoggerFactory.getLogger(LoadBalancedResourceDetails.class);

  private String tokenServiceId;

  private LoadBalancerClient loadBalancerClient;

  /**
   * <p>
   * Constructor for LoadBalancedResourceDetails.
   * </p>
   *
   * @param loadBalancerClient a
   *        {@link org.springframework.cloud.client.loadbalancer.LoadBalancerClient} object.
   */
  public LoadBalancedResourceDetails(LoadBalancerClient loadBalancerClient) {
    this.loadBalancerClient = loadBalancerClient;
  }

  /** {@inheritDoc} */
  @Override
  public String getAccessTokenUri() {
    if (loadBalancerClient != null && tokenServiceId != null && !tokenServiceId.isEmpty()) {
      try {
        return loadBalancerClient.reconstructURI(loadBalancerClient.choose(tokenServiceId),
            new URI(super.getAccessTokenUri())).toString();
      } catch (URISyntaxException e) {
        log.error(EXCEPTION_MESSAGE, e.getMessage());

        return super.getAccessTokenUri();
      }
    } else {
      return super.getAccessTokenUri();
    }
  }

  /**
   * <p>
   * Getter for the field <code>tokenServiceId</code>.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getTokenServiceId() {
    return this.tokenServiceId;
  }

  /**
   * <p>
   * Setter for the field <code>tokenServiceId</code>.
   * </p>
   *
   * @param tokenServiceId a {@link java.lang.String} object.
   */
  public void setTokenServiceId(String tokenServiceId) {
    this.tokenServiceId = tokenServiceId;
  }
}
