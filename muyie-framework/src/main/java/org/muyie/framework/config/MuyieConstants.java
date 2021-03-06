package org.muyie.framework.config;

/**
 * MuYie constants.
 */
public interface MuyieConstants {

  // Spring profiles for development, debug, test, sit, uat and production
  /** Constant <code>SPRING_PROFILE_DEVELOPMENT="dev"</code> */
  String SPRING_PROFILE_DEVELOPMENT = "dev";

  /** Constant <code>SPRING_PROFILE_DEBUG="debug"</code> */
  String SPRING_PROFILE_DEBUG = "debug";

  /** Constant <code>SPRING_PROFILE_TEST="test"</code> */
  String SPRING_PROFILE_TEST = "test";

  /** Constant <code>SPRING_PROFILE_SIT="sit"</code> */
  String SPRING_PROFILE_SIT = "sit";

  /** Constant <code>SPRING_PROFILE_UAT="uat"</code> */
  String SPRING_PROFILE_UAT = "uat";

  /** Constant <code>SPRING_PROFILE_PRODUCTION="prod"</code> */
  String SPRING_PROFILE_PRODUCTION = "prod";

  /**
   * Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
   * Constant <code>SPRING_PROFILE_CLOUD="cloud"</code>
   */
  String SPRING_PROFILE_CLOUD = "cloud";

  /**
   * Spring profile used when deploying to Heroku Constant
   * <code>SPRING_PROFILE_HEROKU="heroku"</code>
   */
  String SPRING_PROFILE_HEROKU = "heroku";

  /**
   * Spring profile used to enable swagger Constant <code>SPRING_PROFILE_SWAGGER="swagger"</code>
   */
  String SPRING_PROFILE_SWAGGER = "swagger";

  /**
   * Spring profile used when deploying to Kubernetes and OpenShift Constant
   * <code>SPRING_PROFILE_K8S="k8s"</code>
   */
  String SPRING_PROFILE_K8S = "k8s";

  /**
   * Date Format Constants
   */
  String FORMAT_DATE = "yyyy-MM-dd";
  String FORMAT_TIME = "HH:mm:ss";
  String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
  String FORMAT_DATE_2 = "yyyyMMdd";
  String FORMAT_TIME_2 = "HHmmss";
  String FORMAT_DATE_TIME_2 = "yyyyMMddHHmmss";

  /**
   * Trace logs
   */
  String LOG_TRACE_ID = "traceId";
  String TRACE_ID_HEADER = "X-TRACE-ID";

}
