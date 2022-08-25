package com.muyie.util;

import com.google.common.collect.Maps;

import com.muyie.properties.MuyieProfileConstants;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * Utility class to load a Spring profile to be used as default when there is no
 * <code>spring.profiles.active</code> set in the environment or as command line
 * argument. If the value is not available in <code>application.yml</code> then
 * <code>dev</code> profile will be used as default.
 *
 * @author larry.qi
 * @since 1.2.6
 */
public final class DefaultProfileUtil {

  private static final String SPRING_PROFILES_DEFAULT = "spring.profiles.default";

  private DefaultProfileUtil() {
  }

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
    defProperties.put(SPRING_PROFILES_DEFAULT, MuyieProfileConstants.SPRING_PROFILE_CONSOLE);
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
