package com.muyie.framework.config;

import com.google.common.collect.Maps;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

import java.util.Map;

import lombok.experimental.UtilityClass;

import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;

/**
 * Utility class to load a Spring profile to be used as default when there is no
 * <code>spring.profiles.active</code> set in the environment or as command line
 * argument. If the value is not available in <code>application.yml</code> then
 * <code>dev</code> profile will be used as default.
 *
 * @author larry.qi
 * @since 1.2.6
 */
@UtilityClass
public class DefaultProfileUtil {

  /**
   * Set a default to use when no profile is configured.
   *
   * @param app the Spring application
   */
  public static void addDefaultProfile(final SpringApplication app) {
    final Map<String, Object> defProperties = Maps.newHashMap();
    /*
     * The default profile to use when no other profiles are defined This cannot be
     * set in the <code>application.yml</code> file. See
     * https://github.com/spring-projects/spring-boot/issues/1219
     */
    defProperties.put(DEFAULT_PROFILES_PROPERTY_NAME, MuyieProfileConstants.SPRING_PROFILE_DEVELOPMENT);
    app.setDefaultProperties(defProperties);
  }

  /**
   * Get the profiles that are applied else get default profiles.
   *
   * @param env spring environment
   * @return profiles
   */
  public static String[] getActiveProfiles(final Environment env) {
    final String[] profiles = env.getActiveProfiles();
    if (profiles.length == 0) {
      return env.getDefaultProfiles();
    }
    return profiles;
  }
}
