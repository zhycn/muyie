package org.muyie.framework.config.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.List;

/**
 * An {@link org.springframework.boot.actuate.info.InfoContributor} that exposes the list of active
 * spring profiles.
 */
public class ActiveProfilesInfoContributor implements InfoContributor {

  private static final String ACTIVE_PROFILES = "activeProfiles";
  private final List<String> profiles;

  /**
   * <p>
   * Constructor for ActiveProfilesInfoContributor.
   * </p>
   *
   * @param environment a {@link org.springframework.core.env.ConfigurableEnvironment} object.
   */
  public ActiveProfilesInfoContributor(ConfigurableEnvironment environment) {
    this.profiles = Arrays.asList(environment.getActiveProfiles());
  }

  /** {@inheritDoc} */
  @Override
  public void contribute(Info.Builder builder) {
    builder.withDetail(ACTIVE_PROFILES, this.profiles);
  }
}
