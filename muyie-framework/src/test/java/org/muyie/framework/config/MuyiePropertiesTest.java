package org.muyie.framework.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

public class MuyiePropertiesTest {

  private MuyieProperties properties;

  @BeforeEach
  public void setup() {
    properties = new MuyieProperties();
  }

  @Test
  public void testComplete() throws Exception {
    // Slightly pedantic; this checks if there are tests for each of the properties.
    final Set<String> set = new LinkedHashSet<>(64, 1F);
    reflect(properties, set, "test");
    for (final String name : set) {
      this.getClass().getDeclaredMethod(name);
    }
  }

  private void reflect(final Object obj, final Set<String> dst, final String prefix) throws Exception {
    final Class<?> src = obj.getClass();
    for (final Method method : src.getDeclaredMethods()) {
      final String name = method.getName();
      if (name.startsWith("get")) {
        final Object res = method.invoke(obj, (Object[]) null);
        if (res != null && src.equals(res.getClass().getDeclaringClass())) {
          reflect(res, dst, prefix + name.substring(3));
        }
      } else if (name.startsWith("set")) {
        dst.add(prefix + name.substring(3));
      }
    }
  }

  @Test
  public void testAsyncCorePoolSize() {
    final MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.corePoolSize;
    assertThat(obj.getCorePoolSize()).isEqualTo(val);
    val++;
    obj.setCorePoolSize(val);
    assertThat(obj.getCorePoolSize()).isEqualTo(val);
  }

  @Test
  public void testAsyncMaxPoolSize() {
    final MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.maxPoolSize;
    assertThat(obj.getMaxPoolSize()).isEqualTo(val);
    val++;
    obj.setMaxPoolSize(val);
    assertThat(obj.getMaxPoolSize()).isEqualTo(val);
  }

  @Test
  public void testAsyncQueueCapacity() {
    final MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.queueCapacity;
    assertThat(obj.getQueueCapacity()).isEqualTo(val);
    val++;
    obj.setQueueCapacity(val);
    assertThat(obj.getQueueCapacity()).isEqualTo(val);
  }

  @Test
  public void testAsyncKeepAliveSeconds() {
    final MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.keepAliveSeconds;
    assertThat(obj.getKeepAliveSeconds()).isEqualTo(val);
    val++;
    obj.setKeepAliveSeconds(val);
    assertThat(obj.getKeepAliveSeconds()).isEqualTo(val);
  }

  @Test
  public void testAsyncAllowCoreThreadTimeOut() {
    final MuyieProperties.Async obj = properties.getAsync();
    boolean val = MuyieDefaults.Async.allowCoreThreadTimeOut;
    assertThat(obj.isAllowCoreThreadTimeOut()).isEqualTo(val);
    val = !val;
    obj.setAllowCoreThreadTimeOut(val);
    assertThat(obj.isAllowCoreThreadTimeOut()).isEqualTo(val);
  }

  @Test
  public void testAsyncWaitForJobsToCompleteOnShutdown() {
    final MuyieProperties.Async obj = properties.getAsync();
    boolean val = MuyieDefaults.Async.waitForJobsToCompleteOnShutdown;
    assertThat(obj.isWaitForJobsToCompleteOnShutdown()).isEqualTo(val);
    val = !val;
    obj.setWaitForJobsToCompleteOnShutdown(val);
    assertThat(obj.isWaitForJobsToCompleteOnShutdown()).isEqualTo(val);
  }

  @Test
  public void testAsyncAwaitTerminationSeconds() {
    final MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.awaitTerminationSeconds;
    assertThat(obj.getAwaitTerminationSeconds()).isEqualTo(val);
    val++;
    obj.setAwaitTerminationSeconds(val);
    assertThat(obj.getAwaitTerminationSeconds()).isEqualTo(val);
  }

  @Test
  public void testAsyncThreadNamePrefix() {
    final MuyieProperties.Async obj = properties.getAsync();
    String val = MuyieDefaults.Async.threadNamePrefix;
    assertThat(obj.getThreadNamePrefix()).isEqualTo(val);
    val = "1" + val;
    obj.setThreadNamePrefix(val);
    assertThat(obj.getThreadNamePrefix()).isEqualTo(val);
  }

  @Test
  public void testHttpCacheTimeToLiveInDays() {
    final MuyieProperties.Http.Cache obj = properties.getHttp().getCache();
    int val = MuyieDefaults.Http.Cache.timeToLiveInDays;
    assertThat(obj.getTimeToLiveInDays()).isEqualTo(val);
    val++;
    obj.setTimeToLiveInDays(val);
    assertThat(obj.getTimeToLiveInDays()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastTimeToLiveSeconds() {
    final MuyieProperties.Cache.Hazelcast obj = properties.getCache().getHazelcast();
    int val = MuyieDefaults.Cache.Hazelcast.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastBackupCount() {
    final MuyieProperties.Cache.Hazelcast obj = properties.getCache().getHazelcast();
    int val = MuyieDefaults.Cache.Hazelcast.backupCount;
    assertThat(obj.getBackupCount()).isEqualTo(val);
    val++;
    obj.setBackupCount(val);
    assertThat(obj.getBackupCount()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastManagementCenterEnabled() {
    final MuyieProperties.Cache.Hazelcast.ManagementCenter obj = properties.getCache().getHazelcast()
        .getManagementCenter();

    boolean val = MuyieDefaults.Cache.Hazelcast.ManagementCenter.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastManagementCenterUpdateInterval() {
    final MuyieProperties.Cache.Hazelcast.ManagementCenter obj = properties.getCache().getHazelcast()
        .getManagementCenter();

    int val = MuyieDefaults.Cache.Hazelcast.ManagementCenter.updateInterval;
    assertThat(obj.getUpdateInterval()).isEqualTo(val);
    val++;
    obj.setUpdateInterval(val);
    assertThat(obj.getUpdateInterval()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastManagementCenterUrl() {
    final MuyieProperties.Cache.Hazelcast.ManagementCenter obj = properties.getCache().getHazelcast()
        .getManagementCenter();

    String val = MuyieDefaults.Cache.Hazelcast.ManagementCenter.url;
    assertThat(obj.getUrl()).isEqualTo(val);
    val = "http://localhost:8080";
    obj.setUrl(val);
    assertThat(obj.getUrl()).isEqualTo(val);
  }

  @Test
  public void testCacheCaffeineTimeToLiveSeconds() {
    final MuyieProperties.Cache.Caffeine obj = properties.getCache().getCaffeine();
    int val = MuyieDefaults.Cache.Caffeine.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheCaffeineMaxEntries() {
    final MuyieProperties.Cache.Caffeine obj = properties.getCache().getCaffeine();
    long val = MuyieDefaults.Cache.Caffeine.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheEhcacheTimeToLiveSeconds() {
    final MuyieProperties.Cache.Ehcache obj = properties.getCache().getEhcache();
    int val = MuyieDefaults.Cache.Ehcache.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheEhcacheMaxEntries() {
    final MuyieProperties.Cache.Ehcache obj = properties.getCache().getEhcache();
    long val = MuyieDefaults.Cache.Ehcache.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanConfigFile() {
    final MuyieProperties.Cache.Infinispan obj = properties.getCache().getInfinispan();
    String val = MuyieDefaults.Cache.Infinispan.configFile;
    assertThat(obj.getConfigFile()).isEqualTo(val);
    val = "1" + val;
    obj.setConfigFile(val);
    assertThat(obj.getConfigFile()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanStatsEnabled() {
    final MuyieProperties.Cache.Infinispan obj = properties.getCache().getInfinispan();
    boolean val = MuyieDefaults.Cache.Infinispan.statsEnabled;
    assertThat(obj.isStatsEnabled()).isEqualTo(val);
    val = !val;
    obj.setStatsEnabled(val);
    assertThat(obj.isStatsEnabled()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanLocalTimeToLiveSeconds() {
    final MuyieProperties.Cache.Infinispan.Local obj = properties.getCache().getInfinispan().getLocal();
    long val = MuyieDefaults.Cache.Infinispan.Local.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanLocalMaxEntries() {
    final MuyieProperties.Cache.Infinispan.Local obj = properties.getCache().getInfinispan().getLocal();
    long val = MuyieDefaults.Cache.Infinispan.Local.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanDistributedTimeToLiveSeconds() {
    final MuyieProperties.Cache.Infinispan.Distributed obj = properties.getCache().getInfinispan().getDistributed();
    long val = MuyieDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanDistributedMaxEntries() {
    final MuyieProperties.Cache.Infinispan.Distributed obj = properties.getCache().getInfinispan().getDistributed();
    long val = MuyieDefaults.Cache.Infinispan.Distributed.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanDistributedInstanceCount() {
    final MuyieProperties.Cache.Infinispan.Distributed obj = properties.getCache().getInfinispan().getDistributed();
    int val = MuyieDefaults.Cache.Infinispan.Distributed.instanceCount;
    assertThat(obj.getInstanceCount()).isEqualTo(val);
    val++;
    obj.setInstanceCount(val);
    assertThat(obj.getInstanceCount()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanReplicatedTimeToLiveSeconds() {
    final MuyieProperties.Cache.Infinispan.Replicated obj = properties.getCache().getInfinispan().getReplicated();
    long val = MuyieDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanReplicatedMaxEntries() {
    final MuyieProperties.Cache.Infinispan.Replicated obj = properties.getCache().getInfinispan().getReplicated();
    long val = MuyieDefaults.Cache.Infinispan.Replicated.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheMemcachedEnabled() {
    final MuyieProperties.Cache.Memcached obj = properties.getCache().getMemcached();
    boolean val = MuyieDefaults.Cache.Memcached.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = true;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testCacheMemcachedServers() {
    final MuyieProperties.Cache.Memcached obj = properties.getCache().getMemcached();
    String val = MuyieDefaults.Cache.Memcached.servers;
    assertThat(obj.getServers()).isEqualTo(val);
    val = "myserver:1337";
    obj.setServers(val);
    assertThat(obj.getServers()).isEqualTo(val);
  }

  @Test
  public void testCacheMemcachedExpiration() {
    final MuyieProperties.Cache.Memcached obj = properties.getCache().getMemcached();
    int val = MuyieDefaults.Cache.Memcached.expiration;
    assertThat(obj.getExpiration()).isEqualTo(val);
    val++;
    obj.setExpiration(val);
    assertThat(obj.getExpiration()).isEqualTo(val);
  }

  @Test
  public void testCacheMemcachedUseBinaryProtocol() {
    final MuyieProperties.Cache.Memcached obj = properties.getCache().getMemcached();
    boolean val = MuyieDefaults.Cache.Memcached.useBinaryProtocol;
    assertThat(obj.isUseBinaryProtocol()).isEqualTo(val);
    val = false;
    obj.setUseBinaryProtocol(val);
    assertThat(obj.isUseBinaryProtocol()).isEqualTo(val);
  }

  @Test
  public void testCacheRedisServer() {
    final MuyieProperties.Cache.Redis obj = properties.getCache().getRedis();
    String val = MuyieDefaults.Cache.Redis.server;
    assertThat(obj.getServer()).isEqualTo(val);
    val = "myserver:1337";
    obj.setServer(val);
    assertThat(obj.getServer()).isEqualTo(val);
  }

  @Test
  public void testCacheRedisExpiration() {
    final MuyieProperties.Cache.Redis obj = properties.getCache().getRedis();
    int val = MuyieDefaults.Cache.Redis.expiration;
    assertThat(obj.getExpiration()).isEqualTo(val);
    val++;
    obj.setExpiration(val);
    assertThat(obj.getExpiration()).isEqualTo(val);
  }

  @Test
  public void testMailFrom() {
    final MuyieProperties.Mail obj = properties.getMail();
    String val = MuyieDefaults.Mail.from;
    assertThat(obj.getFrom()).isEqualTo(val);
    val = "1" + val;
    obj.setFrom(val);
    assertThat(obj.getFrom()).isEqualTo(val);
  }

  @Test
  public void testMailBaseUrl() {
    final MuyieProperties.Mail obj = properties.getMail();
    String val = MuyieDefaults.Mail.baseUrl;
    assertThat(obj.getBaseUrl()).isEqualTo(val);
    val = "1" + val;
    obj.setBaseUrl(val);
    assertThat(obj.getBaseUrl()).isEqualTo(val);
  }

  @Test
  public void testMailEnabled() {
    final MuyieProperties.Mail obj = properties.getMail();
    boolean val = MuyieDefaults.Mail.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testSecurityClientAuthorizationAccessTokenUri() {
    final MuyieProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
    String val = MuyieDefaults.Security.ClientAuthorization.accessTokenUri;
    assertThat(obj.getAccessTokenUri()).isEqualTo(val);
    val = "1" + val;
    obj.setAccessTokenUri(val);
    assertThat(obj.getAccessTokenUri()).isEqualTo(val);
  }

  @Test
  public void testSecurityClientAuthorizationTokenServiceId() {
    final MuyieProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
    String val = MuyieDefaults.Security.ClientAuthorization.tokenServiceId;
    assertThat(obj.getTokenServiceId()).isEqualTo(val);
    val = "1" + val;
    obj.setTokenServiceId(val);
    assertThat(obj.getTokenServiceId()).isEqualTo(val);
  }

  @Test
  public void testSecurityClientAuthorizationClientId() {
    final MuyieProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
    String val = MuyieDefaults.Security.ClientAuthorization.clientId;
    assertThat(obj.getClientId()).isEqualTo(val);
    val = "1" + val;
    obj.setClientId(val);
    assertThat(obj.getClientId()).isEqualTo(val);
  }

  @Test
  public void testSecurityClientAuthorizationClientSecret() {
    final MuyieProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
    String val = MuyieDefaults.Security.ClientAuthorization.clientSecret;
    assertThat(obj.getClientSecret()).isEqualTo(val);
    val = "1" + val;
    obj.setClientSecret(val);
    assertThat(obj.getClientSecret()).isEqualTo(val);
  }

  @Test
  public void testSecurityAuthenticationJwtSecret() {
    final MuyieProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
    String val = MuyieDefaults.Security.Authentication.Jwt.secret;
    assertThat(obj.getSecret()).isEqualTo(val);
    val = "1" + val;
    obj.setSecret(val);
    assertThat(obj.getSecret()).isEqualTo(val);
  }

  @Test
  public void testSecurityAuthenticationJwtBase64Secret() {
    final MuyieProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
    String val = MuyieDefaults.Security.Authentication.Jwt.base64Secret;
    assertThat(obj.getSecret()).isEqualTo(val);
    val = "1" + val;
    obj.setBase64Secret(val);
    assertThat(obj.getBase64Secret()).isEqualTo(val);
  }

  @Test
  public void testSecurityAuthenticationJwtTokenValidityInSeconds() {
    final MuyieProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
    long val = MuyieDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;
    assertThat(obj.getTokenValidityInSeconds()).isEqualTo(val);
    val++;
    obj.setTokenValidityInSeconds(val);
    assertThat(obj.getTokenValidityInSeconds()).isEqualTo(val);
  }

  @Test
  public void testSecurityAuthenticationJwtTokenValidityInSecondsForRememberMe() {
    final MuyieProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
    long val = MuyieDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;
    assertThat(obj.getTokenValidityInSecondsForRememberMe()).isEqualTo(val);
    val++;
    obj.setTokenValidityInSecondsForRememberMe(val);
    assertThat(obj.getTokenValidityInSecondsForRememberMe()).isEqualTo(val);
  }

  @Test
  public void testSecurityRememberMeKey() {
    final MuyieProperties.Security.RememberMe obj = properties.getSecurity().getRememberMe();
    String val = MuyieDefaults.Security.RememberMe.key;
    assertThat(obj.getKey()).isEqualTo(val);
    val = "1" + val;
    obj.setKey(val);
    assertThat(obj.getKey()).isEqualTo(val);
  }

  @Test
  public void testSnowflakeWorkerId() {
    final MuyieProperties.Snowflake obj = properties.getSnowflake();
    long val = MuyieDefaults.Snowflake.workerId;
    assertThat(obj.getWorkerId()).isEqualTo(val);
    val = val++;
    obj.setWorkerId(val);
    assertThat(obj.getWorkerId()).isEqualTo(val);
  }

  @Test
  public void testSnowflakeDatacenterId() {
    final MuyieProperties.Snowflake obj = properties.getSnowflake();
    long val = MuyieDefaults.Snowflake.datacenterId;
    assertThat(obj.getDatacenterId()).isEqualTo(val);
    val = val++;
    obj.setDatacenterId(val);
    assertThat(obj.getDatacenterId()).isEqualTo(val);
  }

  @Test
  public void testSnowflakeUseSystemClock() {
    final MuyieProperties.Snowflake obj = properties.getSnowflake();
    boolean val = MuyieDefaults.Snowflake.useSystemClock;
    assertThat(obj.isUseSystemClock()).isEqualTo(val);
    val = !val;
    obj.setUseSystemClock(val);
    assertThat(obj.isUseSystemClock()).isEqualTo(val);
  }

  @Test
  public void testSwaggerTitle() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.title;
    assertThat(obj.getTitle()).isEqualTo(val);
    val = "1" + val;
    obj.setTitle(val);
    assertThat(obj.getTitle()).isEqualTo(val);
  }

  @Test
  public void testSwaggerDescription() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.description;
    assertThat(obj.getDescription()).isEqualTo(val);
    val = "1" + val;
    obj.setDescription(val);
    assertThat(obj.getDescription()).isEqualTo(val);
  }

  @Test
  public void testSwaggerVersion() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.version;
    assertThat(obj.getVersion()).isEqualTo(val);
    val = "1" + val;
    obj.setVersion(val);
    assertThat(obj.getVersion()).isEqualTo(val);
  }

  @Test
  public void testSwaggerTermsOfServiceUrl() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.termsOfServiceUrl;
    assertThat(obj.getTermsOfServiceUrl()).isEqualTo(val);
    val = "1" + val;
    obj.setTermsOfServiceUrl(val);
    assertThat(obj.getTermsOfServiceUrl()).isEqualTo(val);
  }

  @Test
  public void testSwaggerContactName() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.contactName;
    assertThat(obj.getContactName()).isEqualTo(val);
    val = "1" + val;
    obj.setContactName(val);
    assertThat(obj.getContactName()).isEqualTo(val);
  }

  @Test
  public void testSwaggerContactUrl() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.contactUrl;
    assertThat(obj.getContactUrl()).isEqualTo(val);
    val = "1" + val;
    obj.setContactUrl(val);
    assertThat(obj.getContactUrl()).isEqualTo(val);
  }

  @Test
  public void testSwaggerContactEmail() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.contactEmail;
    assertThat(obj.getContactEmail()).isEqualTo(val);
    val = "1" + val;
    obj.setContactEmail(val);
    assertThat(obj.getContactEmail()).isEqualTo(val);
  }

  @Test
  public void testSwaggerLicense() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.license;
    assertThat(obj.getLicense()).isEqualTo(val);
    val = "1" + val;
    obj.setLicense(val);
    assertThat(obj.getLicense()).isEqualTo(val);
  }

  @Test
  public void testSwaggerLicenseUrl() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.licenseUrl;
    assertThat(obj.getLicenseUrl()).isEqualTo(val);
    val = "1" + val;
    obj.setLicenseUrl(val);
    assertThat(obj.getLicenseUrl()).isEqualTo(val);
  }

  @Test
  public void testSwaggerDefaultIncludePattern() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.defaultIncludePattern;
    assertThat(obj.getDefaultIncludePattern()).isEqualTo(val);
    val = "1" + val;
    obj.setDefaultIncludePattern(val);
    assertThat(obj.getDefaultIncludePattern()).isEqualTo(val);
  }

  @Test
  public void testSwaggerHost() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.host;
    assertThat(obj.getHost()).isEqualTo(val);
    val = "1" + val;
    obj.setHost(val);
    assertThat(obj.getHost()).isEqualTo(val);
  }

  @Test
  public void testSwaggerProtocols() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    String[] def = MuyieDefaults.Swagger.protocols;
    ArrayList<String> val;
    if (def != null) {
      val = newArrayList(def);
      assertThat(obj.getProtocols()).containsExactlyElementsOf(newArrayList(val));
    } else {
      assertThat(obj.getProtocols()).isNull();
      def = new String[1];
      val = new ArrayList<>(1);
    }
    val.add("1");
    obj.setProtocols(val.toArray(def));
    assertThat(obj.getProtocols()).containsExactlyElementsOf(newArrayList(val));
  }

  @Test
  public void testSwaggerUseDefaultResponseMessages() {
    final MuyieProperties.Swagger obj = properties.getSwagger();
    boolean val = MuyieDefaults.Swagger.useDefaultResponseMessages;
    assertThat(obj.isUseDefaultResponseMessages()).isEqualTo(val);
    val = false;
    obj.setUseDefaultResponseMessages(val);
    assertThat(obj.isUseDefaultResponseMessages()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationEnabled() {
    final MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    boolean val = MuyieDefaults.Swagger.Authorization.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationName() {
    final MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.name;
    assertThat(obj.getName()).isEqualTo(val);
    val = "1" + val;
    obj.setName(val);
    assertThat(obj.getName()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationDescription() {
    final MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.description;
    assertThat(obj.getDescription()).isEqualTo(val);
    val = "1" + val;
    obj.setDescription(val);
    assertThat(obj.getDescription()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationDefaultValue() {
    final MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.defaultValue;
    assertThat(obj.getDefaultValue()).isEqualTo(val);
    val = "1" + val;
    obj.setDefaultValue(val);
    assertThat(obj.getDefaultValue()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationRequired() {
    final MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    boolean val = MuyieDefaults.Swagger.Authorization.required;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationParamType() {
    final MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.paramType;
    assertThat(obj.getParamType()).isEqualTo(val);
    val = "1" + val;
    obj.setParamType(val);
    assertThat(obj.getParamType()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationPattern() {
    final MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.pattern;
    assertThat(obj.getPattern()).isEqualTo(val);
    val = "1" + val;
    obj.setPattern(val);
    assertThat(obj.getPattern()).isEqualTo(val);
  }

  @Test
  public void testMetricsLogsEnabled() {
    final MuyieProperties.Metrics.Logs obj = properties.getMetrics().getLogs();
    boolean val = MuyieDefaults.Metrics.Logs.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testMetricsLogsReportFrequency() {
    final MuyieProperties.Metrics.Logs obj = properties.getMetrics().getLogs();
    long val = MuyieDefaults.Metrics.Logs.reportFrequency;
    assertThat(obj.getReportFrequency()).isEqualTo(val);
    val++;
    obj.setReportFrequency(val);
    assertThat(obj.getReportFrequency()).isEqualTo(val);
  }

  @Test
  public void testLoggingUseJsonFormat() {
    final MuyieProperties.Logging obj = properties.getLogging();
    boolean val = MuyieDefaults.Logging.useJsonFormat;
    assertThat(obj.isUseJsonFormat()).isEqualTo(val);
    val = true;
    obj.setUseJsonFormat(val);
    assertThat(obj.isUseJsonFormat()).isEqualTo(val);
  }

  @Test
  public void testLoggingLogstashEnabled() {
    final MuyieProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
    boolean val = MuyieDefaults.Logging.Logstash.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testLoggingLogstashHost() {
    final MuyieProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
    String val = MuyieDefaults.Logging.Logstash.host;
    assertThat(obj.getHost()).isEqualTo(val);
    val = "1" + val;
    obj.setHost(val);
    assertThat(obj.getHost()).isEqualTo(val);
  }

  @Test
  public void testLoggingLogstashPort() {
    final MuyieProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
    int val = MuyieDefaults.Logging.Logstash.port;
    assertThat(obj.getPort()).isEqualTo(val);
    val++;
    obj.setPort(val);
    assertThat(obj.getPort()).isEqualTo(val);
  }

  @Test
  public void testLoggingLogstashQueueSize() {
    final MuyieProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
    int val = MuyieDefaults.Logging.Logstash.queueSize;
    assertThat(obj.getQueueSize()).isEqualTo(val);
    val++;
    obj.setQueueSize(val);
    assertThat(obj.getQueueSize()).isEqualTo(val);
  }

  @Test
  public void testSocialRedirectAfterSignIn() {
    final MuyieProperties.Social obj = properties.getSocial();
    String val = MuyieDefaults.Social.redirectAfterSignIn;
    assertThat(obj.getRedirectAfterSignIn()).isEqualTo(val);
    val = "1" + val;
    obj.setRedirectAfterSignIn(val);
    assertThat(obj.getRedirectAfterSignIn()).isEqualTo(val);
  }

  @Test
  public void testGatewayAuthorizedMicroservicesEndpoints() {
    final MuyieProperties.Gateway obj = properties.getGateway();
    final Map<String, List<String>> val = MuyieDefaults.Gateway.authorizedMicroservicesEndpoints;
    assertThat(obj.getAuthorizedMicroservicesEndpoints()).isEqualTo(val);
    val.put("1", null);
    obj.setAuthorizedMicroservicesEndpoints(val);
    assertThat(obj.getAuthorizedMicroservicesEndpoints()).isEqualTo(val);
  }

  @Test
  public void testGatewayRateLimitingEnabled() {
    final MuyieProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
    boolean val = MuyieDefaults.Gateway.RateLimiting.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testGatewayRateLimitingLimit() {
    final MuyieProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
    long val = MuyieDefaults.Gateway.RateLimiting.limit;
    assertThat(obj.getLimit()).isEqualTo(val);
    val++;
    obj.setLimit(val);
    assertThat(obj.getLimit()).isEqualTo(val);
  }

  @Test
  public void testGatewayRateLimitingDurationInSeconds() {
    final MuyieProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
    int val = MuyieDefaults.Gateway.RateLimiting.durationInSeconds;
    assertThat(obj.getDurationInSeconds()).isEqualTo(val);
    val++;
    obj.setDurationInSeconds(val);
    assertThat(obj.getDurationInSeconds()).isEqualTo(val);
  }

  @Test
  public void testRegistryPassword() {
    final MuyieProperties.Registry obj = properties.getRegistry();
    String val = MuyieDefaults.Registry.password;
    assertThat(obj.getPassword()).isEqualTo(val);
    val = "1" + val;
    obj.setPassword(val);
    assertThat(obj.getPassword()).isEqualTo(val);
  }

  @Test
  public void testClientAppName() {
    final MuyieProperties.ClientApp obj = properties.getClientApp();
    String val = MuyieDefaults.ClientApp.name;
    assertThat(obj.getName()).isEqualTo(val);
    val = "1" + val;
    obj.setName(val);
    assertThat(obj.getName()).isEqualTo(val);
  }

  @Test
  public void testAuditEventsRetentionPeriod() {
    final MuyieProperties.AuditEvents obj = properties.getAuditEvents();
    int val = MuyieDefaults.AuditEvents.retentionPeriod;
    assertThat(obj.getRetentionPeriod()).isEqualTo(val);
    val++;
    obj.setRetentionPeriod(val);
    assertThat(obj.getRetentionPeriod()).isEqualTo(val);
  }
}
