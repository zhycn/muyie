package org.muyie.framework.config;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to MuYie.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "muyie", ignoreUnknownFields = false)
public class MuyieProperties {

  private final Async async = new Async();

  private final Http http = new Http();

  private final Cache cache = new Cache();

  private final Mail mail = new Mail();

  private final Security security = new Security();

  private final Swagger swagger = new Swagger();

  private final Metrics metrics = new Metrics();

  private final Logging logging = new Logging();

  private final CorsConfiguration cors = new CorsConfiguration();

  private final Social social = new Social();

  private final Gateway gateway = new Gateway();

  private final Registry registry = new Registry();

  private final ClientApp clientApp = new ClientApp();

  private final AuditEvents auditEvents = new AuditEvents();

  /**
   * <p>
   * Getter for the field <code>async</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Async} object.
   */
  public Async getAsync() {
    return async;
  }

  /**
   * <p>
   * Getter for the field <code>http</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Http} object.
   */
  public Http getHttp() {
    return http;
  }

  /**
   * <p>
   * Getter for the field <code>cache</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Cache} object.
   */
  public Cache getCache() {
    return cache;
  }

  /**
   * <p>
   * Getter for the field <code>mail</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Mail} object.
   */
  public Mail getMail() {
    return mail;
  }

  /**
   * <p>
   * Getter for the field <code>registry</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Registry} object.
   */
  public Registry getRegistry() {
    return registry;
  }

  /**
   * <p>
   * Getter for the field <code>security</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Security} object.
   */
  public Security getSecurity() {
    return security;
  }

  /**
   * <p>
   * Getter for the field <code>swagger</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Swagger} object.
   */
  public Swagger getSwagger() {
    return swagger;
  }

  /**
   * <p>
   * Getter for the field <code>metrics</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Metrics} object.
   */
  public Metrics getMetrics() {
    return metrics;
  }

  /**
   * <p>
   * Getter for the field <code>logging</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Logging} object.
   */
  public Logging getLogging() {
    return logging;
  }

  /**
   * <p>
   * Getter for the field <code>cors</code>.
   * </p>
   *
   * @return a {@link org.springframework.web.cors.CorsConfiguration} object.
   */
  public CorsConfiguration getCors() {
    return cors;
  }

  /**
   * <p>
   * Getter for the field <code>social</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Social} object.
   */
  public Social getSocial() {
    return social;
  }

  /**
   * <p>
   * Getter for the field <code>gateway</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.Gateway} object.
   */
  public Gateway getGateway() {
    return gateway;
  }

  /**
   * <p>
   * Getter for the field <code>clientApp</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.ClientApp} object.
   */
  public ClientApp getClientApp() {
    return clientApp;
  }

  /**
   * <p>
   * Getter for the field <code>auditEvents</code>.
   * </p>
   *
   * @return a {@link org.muyie.framework.config.MuyieProperties.AuditEvents} object.
   */
  public AuditEvents getAuditEvents() {
    return auditEvents;
  }

  public static class Async {

    private int corePoolSize = MuyieDefaults.Async.corePoolSize;

    private int maxPoolSize = MuyieDefaults.Async.maxPoolSize;

    private int keepAliveSeconds = MuyieDefaults.Async.keepAliveSeconds;

    private int queueCapacity = MuyieDefaults.Async.queueCapacity;

    private boolean allowCoreThreadTimeOut = MuyieDefaults.Async.allowCoreThreadTimeOut;

    private boolean waitForJobsToCompleteOnShutdown =
        MuyieDefaults.Async.waitForJobsToCompleteOnShutdown;

    private int awaitTerminationSeconds = MuyieDefaults.Async.awaitTerminationSeconds;

    private String threadNamePrefix = MuyieDefaults.Async.threadNamePrefix;

    public int getCorePoolSize() {
      return corePoolSize;
    }

    public int getMaxPoolSize() {
      return maxPoolSize;
    }

    public int getKeepAliveSeconds() {
      return keepAliveSeconds;
    }

    public int getQueueCapacity() {
      return queueCapacity;
    }

    public boolean isAllowCoreThreadTimeOut() {
      return allowCoreThreadTimeOut;
    }

    public boolean isWaitForJobsToCompleteOnShutdown() {
      return waitForJobsToCompleteOnShutdown;
    }

    public int getAwaitTerminationSeconds() {
      return awaitTerminationSeconds;
    }

    public String getThreadNamePrefix() {
      return threadNamePrefix;
    }

    public void setCorePoolSize(int corePoolSize) {
      this.corePoolSize = corePoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
      this.maxPoolSize = maxPoolSize;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
      this.keepAliveSeconds = keepAliveSeconds;
    }

    public void setQueueCapacity(int queueCapacity) {
      this.queueCapacity = queueCapacity;
    }

    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
      this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }

    public void setWaitForJobsToCompleteOnShutdown(boolean waitForJobsToCompleteOnShutdown) {
      this.waitForJobsToCompleteOnShutdown = waitForJobsToCompleteOnShutdown;
    }

    public void setAwaitTerminationSeconds(int awaitTerminationSeconds) {
      this.awaitTerminationSeconds = awaitTerminationSeconds;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
      this.threadNamePrefix = threadNamePrefix;
    }

  }

  public static class Http {

    private final Cache cache = new Cache();

    public Cache getCache() {
      return cache;
    }

    public static class Cache {

      private int timeToLiveInDays = MuyieDefaults.Http.Cache.timeToLiveInDays;

      public int getTimeToLiveInDays() {
        return timeToLiveInDays;
      }

      public void setTimeToLiveInDays(int timeToLiveInDays) {
        this.timeToLiveInDays = timeToLiveInDays;
      }
    }
  }

  public static class Cache {

    private final Hazelcast hazelcast = new Hazelcast();

    private final Caffeine caffeine = new Caffeine();

    private final Ehcache ehcache = new Ehcache();

    private final Infinispan infinispan = new Infinispan();

    private final Memcached memcached = new Memcached();

    private final Redis redis = new Redis();

    public Hazelcast getHazelcast() {
      return hazelcast;
    }

    public Caffeine getCaffeine() {
      return caffeine;
    }

    public Ehcache getEhcache() {
      return ehcache;
    }

    public Infinispan getInfinispan() {
      return infinispan;
    }

    public Memcached getMemcached() {
      return memcached;
    }

    public Redis getRedis() {
      return redis;
    }

    public static class Hazelcast {

      private int timeToLiveSeconds = MuyieDefaults.Cache.Hazelcast.timeToLiveSeconds;

      private int backupCount = MuyieDefaults.Cache.Hazelcast.backupCount;

      private final ManagementCenter managementCenter = new ManagementCenter();

      public ManagementCenter getManagementCenter() {
        return managementCenter;
      }

      public static class ManagementCenter {

        private boolean enabled = MuyieDefaults.Cache.Hazelcast.ManagementCenter.enabled;

        private int updateInterval = MuyieDefaults.Cache.Hazelcast.ManagementCenter.updateInterval;

        private String url = MuyieDefaults.Cache.Hazelcast.ManagementCenter.url;

        public boolean isEnabled() {
          return enabled;
        }

        public void setEnabled(boolean enabled) {
          this.enabled = enabled;
        }

        public int getUpdateInterval() {
          return updateInterval;
        }

        public void setUpdateInterval(int updateInterval) {
          this.updateInterval = updateInterval;
        }

        public String getUrl() {
          return url;
        }

        public void setUrl(String url) {
          this.url = url;
        }

      }

      public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
      }

      public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
      }

      public int getBackupCount() {
        return backupCount;
      }

      public void setBackupCount(int backupCount) {
        this.backupCount = backupCount;
      }
    }

    public static class Caffeine {

      private int timeToLiveSeconds = MuyieDefaults.Cache.Caffeine.timeToLiveSeconds;

      private long maxEntries = MuyieDefaults.Cache.Caffeine.maxEntries;

      public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
      }

      public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
      }

      public long getMaxEntries() {
        return maxEntries;
      }

      public void setMaxEntries(long maxEntries) {
        this.maxEntries = maxEntries;
      }
    }

    public static class Ehcache {

      private int timeToLiveSeconds = MuyieDefaults.Cache.Ehcache.timeToLiveSeconds;

      private long maxEntries = MuyieDefaults.Cache.Ehcache.maxEntries;

      public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
      }

      public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
      }

      public long getMaxEntries() {
        return maxEntries;
      }

      public void setMaxEntries(long maxEntries) {
        this.maxEntries = maxEntries;
      }
    }

    public static class Infinispan {

      private String configFile = MuyieDefaults.Cache.Infinispan.configFile;

      private boolean statsEnabled = MuyieDefaults.Cache.Infinispan.statsEnabled;

      private final Local local = new Local();

      private final Distributed distributed = new Distributed();

      private final Replicated replicated = new Replicated();

      public String getConfigFile() {
        return configFile;
      }

      public void setConfigFile(String configFile) {
        this.configFile = configFile;
      }

      public boolean isStatsEnabled() {
        return statsEnabled;
      }

      public void setStatsEnabled(boolean statsEnabled) {
        this.statsEnabled = statsEnabled;
      }

      public Local getLocal() {
        return local;
      }

      public Distributed getDistributed() {
        return distributed;
      }

      public Replicated getReplicated() {
        return replicated;
      }

      public static class Local {

        private long timeToLiveSeconds = MuyieDefaults.Cache.Infinispan.Local.timeToLiveSeconds;

        private long maxEntries = MuyieDefaults.Cache.Infinispan.Local.maxEntries;

        public long getTimeToLiveSeconds() {
          return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
          this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public long getMaxEntries() {
          return maxEntries;
        }

        public void setMaxEntries(long maxEntries) {
          this.maxEntries = maxEntries;
        }

      }

      public static class Distributed {

        private long timeToLiveSeconds =
            MuyieDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;

        private long maxEntries = MuyieDefaults.Cache.Infinispan.Distributed.maxEntries;

        private int instanceCount = MuyieDefaults.Cache.Infinispan.Distributed.instanceCount;

        public long getTimeToLiveSeconds() {
          return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
          this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public long getMaxEntries() {
          return maxEntries;
        }

        public void setMaxEntries(long maxEntries) {
          this.maxEntries = maxEntries;
        }

        public int getInstanceCount() {
          return instanceCount;
        }

        public void setInstanceCount(int instanceCount) {
          this.instanceCount = instanceCount;
        }
      }

      public static class Replicated {

        private long timeToLiveSeconds =
            MuyieDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;

        private long maxEntries = MuyieDefaults.Cache.Infinispan.Replicated.maxEntries;

        public long getTimeToLiveSeconds() {
          return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
          this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public long getMaxEntries() {
          return maxEntries;
        }

        public void setMaxEntries(long maxEntries) {
          this.maxEntries = maxEntries;
        }

      }
    }

    public static class Memcached {

      private boolean enabled = MuyieDefaults.Cache.Memcached.enabled;

      /**
       * Comma or whitespace separated list of servers' addresses.
       */
      private String servers = MuyieDefaults.Cache.Memcached.servers;

      private int expiration = MuyieDefaults.Cache.Memcached.expiration;

      private boolean useBinaryProtocol = MuyieDefaults.Cache.Memcached.useBinaryProtocol;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public String getServers() {
        return servers;
      }

      public void setServers(String servers) {
        this.servers = servers;
      }

      public int getExpiration() {
        return expiration;
      }

      public void setExpiration(int expiration) {
        this.expiration = expiration;
      }

      public boolean isUseBinaryProtocol() {
        return useBinaryProtocol;
      }

      public void setUseBinaryProtocol(boolean useBinaryProtocol) {
        this.useBinaryProtocol = useBinaryProtocol;
      }
    }

    public static class Redis {
      private String server = MuyieDefaults.Cache.Redis.server;
      private int expiration = MuyieDefaults.Cache.Redis.expiration;

      public String getServer() {
        return server;
      }

      public void setServer(String server) {
        this.server = server;
      }

      public int getExpiration() {
        return expiration;
      }

      public void setExpiration(int expiration) {
        this.expiration = expiration;
      }
    }
  }

  public static class Mail {

    private boolean enabled = MuyieDefaults.Mail.enabled;

    private String from = MuyieDefaults.Mail.from;

    private String baseUrl = MuyieDefaults.Mail.baseUrl;

    public boolean isEnabled() {
      return enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

    public String getFrom() {
      return from;
    }

    public void setFrom(String from) {
      this.from = from;
    }

    public String getBaseUrl() {
      return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
    }
  }

  public static class Security {

    private final ClientAuthorization clientAuthorization = new ClientAuthorization();

    private final Authentication authentication = new Authentication();

    private final RememberMe rememberMe = new RememberMe();

    public ClientAuthorization getClientAuthorization() {
      return clientAuthorization;
    }

    public Authentication getAuthentication() {
      return authentication;
    }

    public RememberMe getRememberMe() {
      return rememberMe;
    }

    public static class ClientAuthorization {

      private String accessTokenUri = MuyieDefaults.Security.ClientAuthorization.accessTokenUri;

      private String tokenServiceId = MuyieDefaults.Security.ClientAuthorization.tokenServiceId;

      private String clientId = MuyieDefaults.Security.ClientAuthorization.clientId;

      private String clientSecret = MuyieDefaults.Security.ClientAuthorization.clientSecret;

      public String getAccessTokenUri() {
        return accessTokenUri;
      }

      public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
      }

      public String getTokenServiceId() {
        return tokenServiceId;
      }

      public void setTokenServiceId(String tokenServiceId) {
        this.tokenServiceId = tokenServiceId;
      }

      public String getClientId() {
        return clientId;
      }

      public void setClientId(String clientId) {
        this.clientId = clientId;
      }

      public String getClientSecret() {
        return clientSecret;
      }

      public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
      }
    }

    public static class Authentication {

      private final Jwt jwt = new Jwt();

      public Jwt getJwt() {
        return jwt;
      }

      public static class Jwt {

        private String secret = MuyieDefaults.Security.Authentication.Jwt.secret;

        private String base64Secret = MuyieDefaults.Security.Authentication.Jwt.base64Secret;

        private long tokenValidityInSeconds =
            MuyieDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;

        private long tokenValidityInSecondsForRememberMe =
            MuyieDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;

        public String getSecret() {
          return secret;
        }

        public void setSecret(String secret) {
          this.secret = secret;
        }

        public String getBase64Secret() {
          return base64Secret;
        }

        public void setBase64Secret(String base64Secret) {
          this.base64Secret = base64Secret;
        }

        public long getTokenValidityInSeconds() {
          return tokenValidityInSeconds;
        }

        public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
          this.tokenValidityInSeconds = tokenValidityInSeconds;
        }

        public long getTokenValidityInSecondsForRememberMe() {
          return tokenValidityInSecondsForRememberMe;
        }

        public void setTokenValidityInSecondsForRememberMe(
            long tokenValidityInSecondsForRememberMe) {
          this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
        }
      }
    }

    public static class RememberMe {

      @NotNull
      private String key = MuyieDefaults.Security.RememberMe.key;

      public String getKey() {
        return key;
      }

      public void setKey(String key) {
        this.key = key;
      }
    }
  }

  public static class Swagger {

    private String title = MuyieDefaults.Swagger.title;

    private String description = MuyieDefaults.Swagger.description;

    private String version = MuyieDefaults.Swagger.version;

    private String termsOfServiceUrl = MuyieDefaults.Swagger.termsOfServiceUrl;

    private String contactName = MuyieDefaults.Swagger.contactName;

    private String contactUrl = MuyieDefaults.Swagger.contactUrl;

    private String contactEmail = MuyieDefaults.Swagger.contactEmail;

    private String license = MuyieDefaults.Swagger.license;

    private String licenseUrl = MuyieDefaults.Swagger.licenseUrl;

    private String defaultIncludePattern = MuyieDefaults.Swagger.defaultIncludePattern;

    private String host = MuyieDefaults.Swagger.host;

    private String[] protocols = MuyieDefaults.Swagger.protocols;

    private boolean useDefaultResponseMessages = MuyieDefaults.Swagger.useDefaultResponseMessages;

    private final Authorization authorization = new Authorization();
    
    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }

    public String getTermsOfServiceUrl() {
      return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
      this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getContactName() {
      return contactName;
    }

    public void setContactName(String contactName) {
      this.contactName = contactName;
    }

    public String getContactUrl() {
      return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
      this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
      return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
      this.contactEmail = contactEmail;
    }

    public String getLicense() {
      return license;
    }

    public void setLicense(String license) {
      this.license = license;
    }

    public String getLicenseUrl() {
      return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
      this.licenseUrl = licenseUrl;
    }

    public String getDefaultIncludePattern() {
      return defaultIncludePattern;
    }

    public void setDefaultIncludePattern(String defaultIncludePattern) {
      this.defaultIncludePattern = defaultIncludePattern;
    }

    public String getHost() {
      return host;
    }

    public void setHost(final String host) {
      this.host = host;
    }

    public String[] getProtocols() {
      return protocols;
    }

    public void setProtocols(final String[] protocols) {
      this.protocols = protocols;
    }

    public boolean isUseDefaultResponseMessages() {
      return useDefaultResponseMessages;
    }

    public void setUseDefaultResponseMessages(final boolean useDefaultResponseMessages) {
      this.useDefaultResponseMessages = useDefaultResponseMessages;
    }
    
    public Authorization getAuthorization() {
      return authorization;
    }

    public static class Authorization {

      private boolean enabled = MuyieDefaults.Swagger.Authorization.enabled;

      private String name = MuyieDefaults.Swagger.Authorization.name;

      private String description = MuyieDefaults.Swagger.Authorization.description;

      private String defaultValue = MuyieDefaults.Swagger.Authorization.defaultValue;

      private boolean required = MuyieDefaults.Swagger.Authorization.required;

      private String paramType = MuyieDefaults.Swagger.Authorization.paramType;

      private String pattern = MuyieDefaults.Swagger.Authorization.pattern;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getDescription() {
        return description;
      }

      public void setDescription(String description) {
        this.description = description;
      }

      public String getDefaultValue() {
        return defaultValue;
      }

      public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
      }

      public boolean isRequired() {
        return required;
      }

      public void setRequired(boolean required) {
        this.required = required;
      }

      public String getParamType() {
        return paramType;
      }

      public void setParamType(String paramType) {
        this.paramType = paramType;
      }

      public String getPattern() {
        return pattern;
      }

      public void setPattern(String pattern) {
        this.pattern = pattern;
      }

    }
    
  }

  public static class Metrics {

    private final Logs logs = new Logs();

    public Logs getLogs() {
      return logs;
    }

    public static class Logs {

      private boolean enabled = MuyieDefaults.Metrics.Logs.enabled;

      private long reportFrequency = MuyieDefaults.Metrics.Logs.reportFrequency;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public long getReportFrequency() {
        return reportFrequency;
      }

      public void setReportFrequency(long reportFrequency) {
        this.reportFrequency = reportFrequency;
      }
    }
  }

  public static class Logging {

    private boolean useJsonFormat = MuyieDefaults.Logging.useJsonFormat;

    private final Logstash logstash = new Logstash();

    public boolean isUseJsonFormat() {
      return useJsonFormat;
    }

    public void setUseJsonFormat(boolean useJsonFormat) {
      this.useJsonFormat = useJsonFormat;
    }

    public Logstash getLogstash() {
      return logstash;
    }

    public static class Logstash {

      private boolean enabled = MuyieDefaults.Logging.Logstash.enabled;

      private String host = MuyieDefaults.Logging.Logstash.host;

      private int port = MuyieDefaults.Logging.Logstash.port;

      private int queueSize = MuyieDefaults.Logging.Logstash.queueSize;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public String getHost() {
        return host;
      }

      public void setHost(String host) {
        this.host = host;
      }

      public int getPort() {
        return port;
      }

      public void setPort(int port) {
        this.port = port;
      }

      public int getQueueSize() {
        return queueSize;
      }

      public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
      }
    }
  }

  public static class Social {

    private String redirectAfterSignIn = MuyieDefaults.Social.redirectAfterSignIn;

    public String getRedirectAfterSignIn() {
      return redirectAfterSignIn;
    }

    public void setRedirectAfterSignIn(String redirectAfterSignIn) {
      this.redirectAfterSignIn = redirectAfterSignIn;
    }
  }

  public static class Gateway {

    private final RateLimiting rateLimiting = new RateLimiting();

    public RateLimiting getRateLimiting() {
      return rateLimiting;
    }

    private Map<String, List<String>> authorizedMicroservicesEndpoints =
        MuyieDefaults.Gateway.authorizedMicroservicesEndpoints;

    public Map<String, List<String>> getAuthorizedMicroservicesEndpoints() {
      return authorizedMicroservicesEndpoints;
    }

    public void setAuthorizedMicroservicesEndpoints(
        Map<String, List<String>> authorizedMicroservicesEndpoints) {
      this.authorizedMicroservicesEndpoints = authorizedMicroservicesEndpoints;
    }

    public static class RateLimiting {

      private boolean enabled = MuyieDefaults.Gateway.RateLimiting.enabled;

      private long limit = MuyieDefaults.Gateway.RateLimiting.limit;

      private int durationInSeconds = MuyieDefaults.Gateway.RateLimiting.durationInSeconds;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public long getLimit() {
        return this.limit;
      }

      public void setLimit(long limit) {
        this.limit = limit;
      }

      public int getDurationInSeconds() {
        return durationInSeconds;
      }

      public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
      }
    }
  }

  public static class Registry {

    private String password = MuyieDefaults.Registry.password;

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  public static class ClientApp {

    private String name = MuyieDefaults.ClientApp.name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class AuditEvents {
    private int retentionPeriod = MuyieDefaults.AuditEvents.retentionPeriod;

    public int getRetentionPeriod() {
      return retentionPeriod;
    }

    public void setRetentionPeriod(int retentionPeriod) {
      this.retentionPeriod = retentionPeriod;
    }
  }
}
