package org.muyie.framework.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * MuyieDefaults interface.
 * </p>
 */
public interface MuyieDefaults {

  interface Async {

    int corePoolSize = 8;
    int maxPoolSize = 50;
    int keepAliveSeconds = 60 * 5;
    int queueCapacity = 10000;
    boolean allowCoreThreadTimeOut = false;
    boolean waitForJobsToCompleteOnShutdown = false;
    int awaitTerminationSeconds = 60 * 15;
    String threadNamePrefix = "MuYieAsync-";
  }

  interface Http {

    interface Cache {

      int timeToLiveInDays = 1461; // 4 years (including leap day)
    }
  }

  interface Cache {

    interface Hazelcast {

      int timeToLiveSeconds = 3600; // 1 hour
      int backupCount = 1;

      interface ManagementCenter {

        boolean enabled = false;
        int updateInterval = 3;
        String url = "";
      }
    }

    interface Caffeine {

      int timeToLiveSeconds = 3600; // 1 hour
      long maxEntries = 100;
    }

    interface Ehcache {

      int timeToLiveSeconds = 3600; // 1 hour
      long maxEntries = 100;
    }

    interface Infinispan {

      String configFile = "default-configs/default-jgroups-tcp.xml";
      boolean statsEnabled = false;

      interface Local {

        long timeToLiveSeconds = 60; // 1 minute
        long maxEntries = 100;
      }

      interface Distributed {

        long timeToLiveSeconds = 60; // 1 minute
        long maxEntries = 100;
        int instanceCount = 1;
      }

      interface Replicated {

        long timeToLiveSeconds = 60; // 1 minute
        long maxEntries = 100;
      }
    }

    interface Memcached {

      boolean enabled = false;
      String servers = "localhost:11211";
      int expiration = 300; // 5 minutes
      boolean useBinaryProtocol = true;
    }

    interface Redis {
      String server = "redis://localhost:6379";
      int expiration = 300; // 5 minutes
    }
  }

  interface Mail {
    boolean enabled = false;
    String from = "";
    String baseUrl = "";
  }

  interface Security {

    interface ClientAuthorization {

      String accessTokenUri = null;
      String tokenServiceId = null;
      String clientId = null;
      String clientSecret = null;
    }

    interface Authentication {

      interface Jwt {

        String secret = null;
        String base64Secret = null;
        long tokenValidityInSeconds = 1800; // 30 minutes
        long tokenValidityInSecondsForRememberMe = 2592000; // 30 days
      }
    }

    interface RememberMe {

      String key = null;
    }
  }

  interface Snowflake {

    long workerId = 0L;
    long datacenterId = 0L;
    boolean useSystemClock = false;
  }

  interface Swagger {

    String title = "Application API";
    String description = "API documentation";
    String version = "0.0.1";
    String termsOfServiceUrl = null;
    String contactName = null;
    String contactUrl = null;
    String contactEmail = null;
    String license = null;
    String licenseUrl = null;
    String defaultIncludePattern = "/api/.*";
    String host = null;
    String[] protocols = {};
    boolean useDefaultResponseMessages = true;

    interface Authorization {

      boolean enabled = false;
      String name = "Authorization";
      String description = null;
      String defaultValue = null;
      boolean required = false;
      String paramType = "header";
      String pattern = null;
    }

  }

  interface Metrics {

    interface Jmx {

      boolean enabled = false;
    }

    interface Logs {

      boolean enabled = false;
      long reportFrequency = 60;

    }

    interface Prometheus {

      boolean enabled = false;
      String endpoint = "/prometheusMetrics";
    }
  }

  interface Logging {

    boolean useJsonFormat = false;

    interface Logstash {

      boolean enabled = false;
      String host = "localhost";
      int port = 5000;
      int queueSize = 512;
    }
  }

  interface Social {

    String redirectAfterSignIn = "/#/home";
  }

  interface Gateway {

    Map<String, List<String>> authorizedMicroservicesEndpoints = new LinkedHashMap<>();

    interface RateLimiting {

      boolean enabled = false;
      long limit = 100_000L;
      int durationInSeconds = 3_600;

    }
  }

  interface Ribbon {

    String[] displayOnActiveProfiles = null;
  }

  interface Registry {

    String password = null;
  }

  interface ClientApp {

    String name = "muyieApp";
  }

  interface AuditEvents {

    int retentionPeriod = 30;
  }
}
