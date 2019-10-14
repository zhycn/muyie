package org.muyie.framework.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.muyie.framework.config.MuyieDefaults;
import org.muyie.framework.config.MuyieProperties;

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
    Set<String> set = new LinkedHashSet<>(64, 1F);
    reflect(properties, set, "test");
    for (String name : set) {
      this.getClass().getDeclaredMethod(name);
    }
  }

  private void reflect(Object obj, Set<String> dst, String prefix) throws Exception {
    Class<?> src = obj.getClass();
    for (Method method : src.getDeclaredMethods()) {
      String name = method.getName();
      if (name.startsWith("get")) {
        Object res = method.invoke(obj, (Object[]) null);
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
    MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.corePoolSize;
    assertThat(obj.getCorePoolSize()).isEqualTo(val);
    val++;
    obj.setCorePoolSize(val);
    assertThat(obj.getCorePoolSize()).isEqualTo(val);
  }

  @Test
  public void testAsyncMaxPoolSize() {
    MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.maxPoolSize;
    assertThat(obj.getMaxPoolSize()).isEqualTo(val);
    val++;
    obj.setMaxPoolSize(val);
    assertThat(obj.getMaxPoolSize()).isEqualTo(val);
  }

  @Test
  public void testAsyncQueueCapacity() {
    MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.queueCapacity;
    assertThat(obj.getQueueCapacity()).isEqualTo(val);
    val++;
    obj.setQueueCapacity(val);
    assertThat(obj.getQueueCapacity()).isEqualTo(val);
  }

  @Test
  public void testAsyncKeepAliveSeconds() {
    MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.keepAliveSeconds;
    assertThat(obj.getKeepAliveSeconds()).isEqualTo(val);
    val++;
    obj.setKeepAliveSeconds(val);
    assertThat(obj.getKeepAliveSeconds()).isEqualTo(val);
  }

  @Test
  public void testAsyncAllowCoreThreadTimeOut() {
    MuyieProperties.Async obj = properties.getAsync();
    boolean val = MuyieDefaults.Async.allowCoreThreadTimeOut;
    assertThat(obj.isAllowCoreThreadTimeOut()).isEqualTo(val);
    val = !val;
    obj.setAllowCoreThreadTimeOut(val);
    assertThat(obj.isAllowCoreThreadTimeOut()).isEqualTo(val);
  }

  @Test
  public void testAsyncWaitForJobsToCompleteOnShutdown() {
    MuyieProperties.Async obj = properties.getAsync();
    boolean val = MuyieDefaults.Async.waitForJobsToCompleteOnShutdown;
    assertThat(obj.isWaitForJobsToCompleteOnShutdown()).isEqualTo(val);
    val = !val;
    obj.setWaitForJobsToCompleteOnShutdown(val);
    assertThat(obj.isWaitForJobsToCompleteOnShutdown()).isEqualTo(val);
  }

  @Test
  public void testAsyncAwaitTerminationSeconds() {
    MuyieProperties.Async obj = properties.getAsync();
    int val = MuyieDefaults.Async.awaitTerminationSeconds;
    assertThat(obj.getAwaitTerminationSeconds()).isEqualTo(val);
    val++;
    obj.setAwaitTerminationSeconds(val);
    assertThat(obj.getAwaitTerminationSeconds()).isEqualTo(val);
  }

  @Test
  public void testAsyncThreadNamePrefix() {
    MuyieProperties.Async obj = properties.getAsync();
    String val = MuyieDefaults.Async.threadNamePrefix;
    assertThat(obj.getThreadNamePrefix()).isEqualTo(val);
    val = "1" + val;
    obj.setThreadNamePrefix(val);
    assertThat(obj.getThreadNamePrefix()).isEqualTo(val);
  }

  @Test
  public void testHttpCacheTimeToLiveInDays() {
    MuyieProperties.Http.Cache obj = properties.getHttp().getCache();
    int val = MuyieDefaults.Http.Cache.timeToLiveInDays;
    assertThat(obj.getTimeToLiveInDays()).isEqualTo(val);
    val++;
    obj.setTimeToLiveInDays(val);
    assertThat(obj.getTimeToLiveInDays()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastTimeToLiveSeconds() {
    MuyieProperties.Cache.Hazelcast obj = properties.getCache().getHazelcast();
    int val = MuyieDefaults.Cache.Hazelcast.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastBackupCount() {
    MuyieProperties.Cache.Hazelcast obj = properties.getCache().getHazelcast();
    int val = MuyieDefaults.Cache.Hazelcast.backupCount;
    assertThat(obj.getBackupCount()).isEqualTo(val);
    val++;
    obj.setBackupCount(val);
    assertThat(obj.getBackupCount()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastManagementCenterEnabled() {
    MuyieProperties.Cache.Hazelcast.ManagementCenter obj =
        properties.getCache().getHazelcast().getManagementCenter();

    boolean val = MuyieDefaults.Cache.Hazelcast.ManagementCenter.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastManagementCenterUpdateInterval() {
    MuyieProperties.Cache.Hazelcast.ManagementCenter obj =
        properties.getCache().getHazelcast().getManagementCenter();

    int val = MuyieDefaults.Cache.Hazelcast.ManagementCenter.updateInterval;
    assertThat(obj.getUpdateInterval()).isEqualTo(val);
    val++;
    obj.setUpdateInterval(val);
    assertThat(obj.getUpdateInterval()).isEqualTo(val);
  }

  @Test
  public void testCacheHazelcastManagementCenterUrl() {
    MuyieProperties.Cache.Hazelcast.ManagementCenter obj =
        properties.getCache().getHazelcast().getManagementCenter();

    String val = MuyieDefaults.Cache.Hazelcast.ManagementCenter.url;
    assertThat(obj.getUrl()).isEqualTo(val);
    val = "http://localhost:8080";
    obj.setUrl(val);
    assertThat(obj.getUrl()).isEqualTo(val);
  }

  @Test
  public void testCacheCaffeineTimeToLiveSeconds() {
    MuyieProperties.Cache.Caffeine obj = properties.getCache().getCaffeine();
    int val = MuyieDefaults.Cache.Caffeine.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheCaffeineMaxEntries() {
    MuyieProperties.Cache.Caffeine obj = properties.getCache().getCaffeine();
    long val = MuyieDefaults.Cache.Caffeine.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheEhcacheTimeToLiveSeconds() {
    MuyieProperties.Cache.Ehcache obj = properties.getCache().getEhcache();
    int val = MuyieDefaults.Cache.Ehcache.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheEhcacheMaxEntries() {
    MuyieProperties.Cache.Ehcache obj = properties.getCache().getEhcache();
    long val = MuyieDefaults.Cache.Ehcache.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanConfigFile() {
    MuyieProperties.Cache.Infinispan obj = properties.getCache().getInfinispan();
    String val = MuyieDefaults.Cache.Infinispan.configFile;
    assertThat(obj.getConfigFile()).isEqualTo(val);
    val = "1" + val;
    obj.setConfigFile(val);
    assertThat(obj.getConfigFile()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanStatsEnabled() {
    MuyieProperties.Cache.Infinispan obj = properties.getCache().getInfinispan();
    boolean val = MuyieDefaults.Cache.Infinispan.statsEnabled;
    assertThat(obj.isStatsEnabled()).isEqualTo(val);
    val = !val;
    obj.setStatsEnabled(val);
    assertThat(obj.isStatsEnabled()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanLocalTimeToLiveSeconds() {
    MuyieProperties.Cache.Infinispan.Local obj = properties.getCache().getInfinispan().getLocal();
    long val = MuyieDefaults.Cache.Infinispan.Local.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanLocalMaxEntries() {
    MuyieProperties.Cache.Infinispan.Local obj = properties.getCache().getInfinispan().getLocal();
    long val = MuyieDefaults.Cache.Infinispan.Local.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanDistributedTimeToLiveSeconds() {
    MuyieProperties.Cache.Infinispan.Distributed obj =
        properties.getCache().getInfinispan().getDistributed();
    long val = MuyieDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanDistributedMaxEntries() {
    MuyieProperties.Cache.Infinispan.Distributed obj =
        properties.getCache().getInfinispan().getDistributed();
    long val = MuyieDefaults.Cache.Infinispan.Distributed.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanDistributedInstanceCount() {
    MuyieProperties.Cache.Infinispan.Distributed obj =
        properties.getCache().getInfinispan().getDistributed();
    int val = MuyieDefaults.Cache.Infinispan.Distributed.instanceCount;
    assertThat(obj.getInstanceCount()).isEqualTo(val);
    val++;
    obj.setInstanceCount(val);
    assertThat(obj.getInstanceCount()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanReplicatedTimeToLiveSeconds() {
    MuyieProperties.Cache.Infinispan.Replicated obj =
        properties.getCache().getInfinispan().getReplicated();
    long val = MuyieDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    val++;
    obj.setTimeToLiveSeconds(val);
    assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
  }

  @Test
  public void testCacheInfinispanReplicatedMaxEntries() {
    MuyieProperties.Cache.Infinispan.Replicated obj =
        properties.getCache().getInfinispan().getReplicated();
    long val = MuyieDefaults.Cache.Infinispan.Replicated.maxEntries;
    assertThat(obj.getMaxEntries()).isEqualTo(val);
    val++;
    obj.setMaxEntries(val);
    assertThat(obj.getMaxEntries()).isEqualTo(val);
  }

  @Test
  public void testCacheMemcachedEnabled() {
    MuyieProperties.Cache.Memcached obj = properties.getCache().getMemcached();
    boolean val = MuyieDefaults.Cache.Memcached.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = true;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testCacheMemcachedServers() {
    MuyieProperties.Cache.Memcached obj = properties.getCache().getMemcached();
    String val = MuyieDefaults.Cache.Memcached.servers;
    assertThat(obj.getServers()).isEqualTo(val);
    val = "myserver:1337";
    obj.setServers(val);
    assertThat(obj.getServers()).isEqualTo(val);
  }

  @Test
  public void testCacheMemcachedExpiration() {
    MuyieProperties.Cache.Memcached obj = properties.getCache().getMemcached();
    int val = MuyieDefaults.Cache.Memcached.expiration;
    assertThat(obj.getExpiration()).isEqualTo(val);
    val++;
    obj.setExpiration(val);
    assertThat(obj.getExpiration()).isEqualTo(val);
  }

  @Test
  public void testCacheMemcachedUseBinaryProtocol() {
    MuyieProperties.Cache.Memcached obj = properties.getCache().getMemcached();
    boolean val = MuyieDefaults.Cache.Memcached.useBinaryProtocol;
    assertThat(obj.isUseBinaryProtocol()).isEqualTo(val);
    val = false;
    obj.setUseBinaryProtocol(val);
    assertThat(obj.isUseBinaryProtocol()).isEqualTo(val);
  }

  @Test
  public void testCacheRedisServer() {
    MuyieProperties.Cache.Redis obj = properties.getCache().getRedis();
    String val = MuyieDefaults.Cache.Redis.server;
    assertThat(obj.getServer()).isEqualTo(val);
    val = "myserver:1337";
    obj.setServer(val);
    assertThat(obj.getServer()).isEqualTo(val);
  }

  @Test
  public void testCacheRedisExpiration() {
    MuyieProperties.Cache.Redis obj = properties.getCache().getRedis();
    int val = MuyieDefaults.Cache.Redis.expiration;
    assertThat(obj.getExpiration()).isEqualTo(val);
    val++;
    obj.setExpiration(val);
    assertThat(obj.getExpiration()).isEqualTo(val);
  }

  @Test
  public void testMailFrom() {
    MuyieProperties.Mail obj = properties.getMail();
    String val = MuyieDefaults.Mail.from;
    assertThat(obj.getFrom()).isEqualTo(val);
    val = "1" + val;
    obj.setFrom(val);
    assertThat(obj.getFrom()).isEqualTo(val);
  }

  @Test
  public void testMailBaseUrl() {
    MuyieProperties.Mail obj = properties.getMail();
    String val = MuyieDefaults.Mail.baseUrl;
    assertThat(obj.getBaseUrl()).isEqualTo(val);
    val = "1" + val;
    obj.setBaseUrl(val);
    assertThat(obj.getBaseUrl()).isEqualTo(val);
  }

  @Test
  public void testMailEnabled() {
    MuyieProperties.Mail obj = properties.getMail();
    boolean val = MuyieDefaults.Mail.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testSecurityClientAuthorizationAccessTokenUri() {
    MuyieProperties.Security.ClientAuthorization obj =
        properties.getSecurity().getClientAuthorization();
    String val = MuyieDefaults.Security.ClientAuthorization.accessTokenUri;
    assertThat(obj.getAccessTokenUri()).isEqualTo(val);
    val = "1" + val;
    obj.setAccessTokenUri(val);
    assertThat(obj.getAccessTokenUri()).isEqualTo(val);
  }

  @Test
  public void testSecurityClientAuthorizationTokenServiceId() {
    MuyieProperties.Security.ClientAuthorization obj =
        properties.getSecurity().getClientAuthorization();
    String val = MuyieDefaults.Security.ClientAuthorization.tokenServiceId;
    assertThat(obj.getTokenServiceId()).isEqualTo(val);
    val = "1" + val;
    obj.setTokenServiceId(val);
    assertThat(obj.getTokenServiceId()).isEqualTo(val);
  }

  @Test
  public void testSecurityClientAuthorizationClientId() {
    MuyieProperties.Security.ClientAuthorization obj =
        properties.getSecurity().getClientAuthorization();
    String val = MuyieDefaults.Security.ClientAuthorization.clientId;
    assertThat(obj.getClientId()).isEqualTo(val);
    val = "1" + val;
    obj.setClientId(val);
    assertThat(obj.getClientId()).isEqualTo(val);
  }

  @Test
  public void testSecurityClientAuthorizationClientSecret() {
    MuyieProperties.Security.ClientAuthorization obj =
        properties.getSecurity().getClientAuthorization();
    String val = MuyieDefaults.Security.ClientAuthorization.clientSecret;
    assertThat(obj.getClientSecret()).isEqualTo(val);
    val = "1" + val;
    obj.setClientSecret(val);
    assertThat(obj.getClientSecret()).isEqualTo(val);
  }

  @Test
  public void testSecurityAuthenticationJwtSecret() {
    MuyieProperties.Security.Authentication.Jwt obj =
        properties.getSecurity().getAuthentication().getJwt();
    String val = MuyieDefaults.Security.Authentication.Jwt.secret;
    assertThat(obj.getSecret()).isEqualTo(val);
    val = "1" + val;
    obj.setSecret(val);
    assertThat(obj.getSecret()).isEqualTo(val);
  }

  @Test
  public void testSecurityAuthenticationJwtBase64Secret() {
    MuyieProperties.Security.Authentication.Jwt obj =
        properties.getSecurity().getAuthentication().getJwt();
    String val = MuyieDefaults.Security.Authentication.Jwt.base64Secret;
    assertThat(obj.getSecret()).isEqualTo(val);
    val = "1" + val;
    obj.setBase64Secret(val);
    assertThat(obj.getBase64Secret()).isEqualTo(val);
  }

  @Test
  public void testSecurityAuthenticationJwtTokenValidityInSeconds() {
    MuyieProperties.Security.Authentication.Jwt obj =
        properties.getSecurity().getAuthentication().getJwt();
    long val = MuyieDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;
    assertThat(obj.getTokenValidityInSeconds()).isEqualTo(val);
    val++;
    obj.setTokenValidityInSeconds(val);
    assertThat(obj.getTokenValidityInSeconds()).isEqualTo(val);
  }

  @Test
  public void testSecurityAuthenticationJwtTokenValidityInSecondsForRememberMe() {
    MuyieProperties.Security.Authentication.Jwt obj =
        properties.getSecurity().getAuthentication().getJwt();
    long val = MuyieDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;
    assertThat(obj.getTokenValidityInSecondsForRememberMe()).isEqualTo(val);
    val++;
    obj.setTokenValidityInSecondsForRememberMe(val);
    assertThat(obj.getTokenValidityInSecondsForRememberMe()).isEqualTo(val);
  }

  @Test
  public void testSecurityRememberMeKey() {
    MuyieProperties.Security.RememberMe obj = properties.getSecurity().getRememberMe();
    String val = MuyieDefaults.Security.RememberMe.key;
    assertThat(obj.getKey()).isEqualTo(val);
    val = "1" + val;
    obj.setKey(val);
    assertThat(obj.getKey()).isEqualTo(val);
  }

  @Test
  public void testSnowflakeWorkerId() {
    MuyieProperties.Snowflake obj = properties.getSnowflake();
    long val = MuyieDefaults.Snowflake.workerId;
    assertThat(obj.getWorkerId()).isEqualTo(val);
    val = val++;
    obj.setWorkerId(val);
    assertThat(obj.getWorkerId()).isEqualTo(val);
  }

  @Test
  public void testSnowflakeDatacenterId() {
    MuyieProperties.Snowflake obj = properties.getSnowflake();
    long val = MuyieDefaults.Snowflake.datacenterId;
    assertThat(obj.getDatacenterId()).isEqualTo(val);
    val = val++;
    obj.setDatacenterId(val);
    assertThat(obj.getDatacenterId()).isEqualTo(val);
  }

  @Test
  public void testSnowflakeUseSystemClock() {
    MuyieProperties.Snowflake obj = properties.getSnowflake();
    boolean val = MuyieDefaults.Snowflake.useSystemClock;
    assertThat(obj.isUseSystemClock()).isEqualTo(val);
    val = !val;
    obj.setUseSystemClock(val);
    assertThat(obj.isUseSystemClock()).isEqualTo(val);
  }

  @Test
  public void testSwaggerTitle() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.title;
    assertThat(obj.getTitle()).isEqualTo(val);
    val = "1" + val;
    obj.setTitle(val);
    assertThat(obj.getTitle()).isEqualTo(val);
  }

  @Test
  public void testSwaggerDescription() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.description;
    assertThat(obj.getDescription()).isEqualTo(val);
    val = "1" + val;
    obj.setDescription(val);
    assertThat(obj.getDescription()).isEqualTo(val);
  }

  @Test
  public void testSwaggerVersion() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.version;
    assertThat(obj.getVersion()).isEqualTo(val);
    val = "1" + val;
    obj.setVersion(val);
    assertThat(obj.getVersion()).isEqualTo(val);
  }

  @Test
  public void testSwaggerTermsOfServiceUrl() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.termsOfServiceUrl;
    assertThat(obj.getTermsOfServiceUrl()).isEqualTo(val);
    val = "1" + val;
    obj.setTermsOfServiceUrl(val);
    assertThat(obj.getTermsOfServiceUrl()).isEqualTo(val);
  }

  @Test
  public void testSwaggerContactName() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.contactName;
    assertThat(obj.getContactName()).isEqualTo(val);
    val = "1" + val;
    obj.setContactName(val);
    assertThat(obj.getContactName()).isEqualTo(val);
  }

  @Test
  public void testSwaggerContactUrl() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.contactUrl;
    assertThat(obj.getContactUrl()).isEqualTo(val);
    val = "1" + val;
    obj.setContactUrl(val);
    assertThat(obj.getContactUrl()).isEqualTo(val);
  }

  @Test
  public void testSwaggerContactEmail() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.contactEmail;
    assertThat(obj.getContactEmail()).isEqualTo(val);
    val = "1" + val;
    obj.setContactEmail(val);
    assertThat(obj.getContactEmail()).isEqualTo(val);
  }

  @Test
  public void testSwaggerLicense() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.license;
    assertThat(obj.getLicense()).isEqualTo(val);
    val = "1" + val;
    obj.setLicense(val);
    assertThat(obj.getLicense()).isEqualTo(val);
  }

  @Test
  public void testSwaggerLicenseUrl() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.licenseUrl;
    assertThat(obj.getLicenseUrl()).isEqualTo(val);
    val = "1" + val;
    obj.setLicenseUrl(val);
    assertThat(obj.getLicenseUrl()).isEqualTo(val);
  }

  @Test
  public void testSwaggerDefaultIncludePattern() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.defaultIncludePattern;
    assertThat(obj.getDefaultIncludePattern()).isEqualTo(val);
    val = "1" + val;
    obj.setDefaultIncludePattern(val);
    assertThat(obj.getDefaultIncludePattern()).isEqualTo(val);
  }

  @Test
  public void testSwaggerHost() {
    MuyieProperties.Swagger obj = properties.getSwagger();
    String val = MuyieDefaults.Swagger.host;
    assertThat(obj.getHost()).isEqualTo(val);
    val = "1" + val;
    obj.setHost(val);
    assertThat(obj.getHost()).isEqualTo(val);
  }

  @Test
  public void testSwaggerProtocols() {
    MuyieProperties.Swagger obj = properties.getSwagger();
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
    MuyieProperties.Swagger obj = properties.getSwagger();
    boolean val = MuyieDefaults.Swagger.useDefaultResponseMessages;
    assertThat(obj.isUseDefaultResponseMessages()).isEqualTo(val);
    val = false;
    obj.setUseDefaultResponseMessages(val);
    assertThat(obj.isUseDefaultResponseMessages()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationEnabled() {
    MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    boolean val = MuyieDefaults.Swagger.Authorization.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationName() {
    MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.name;
    assertThat(obj.getName()).isEqualTo(val);
    val = "1" + val;
    obj.setName(val);
    assertThat(obj.getName()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationDescription() {
    MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.description;
    assertThat(obj.getDescription()).isEqualTo(val);
    val = "1" + val;
    obj.setDescription(val);
    assertThat(obj.getDescription()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationDefaultValue() {
    MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.defaultValue;
    assertThat(obj.getDefaultValue()).isEqualTo(val);
    val = "1" + val;
    obj.setDefaultValue(val);
    assertThat(obj.getDefaultValue()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationRequired() {
    MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    boolean val = MuyieDefaults.Swagger.Authorization.required;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationParamType() {
    MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.paramType;
    assertThat(obj.getParamType()).isEqualTo(val);
    val = "1" + val;
    obj.setParamType(val);
    assertThat(obj.getParamType()).isEqualTo(val);
  }

  @Test
  public void testSwaggerAuthorizationPattern() {
    MuyieProperties.Swagger.Authorization obj = properties.getSwagger().getAuthorization();
    String val = MuyieDefaults.Swagger.Authorization.pattern;
    assertThat(obj.getPattern()).isEqualTo(val);
    val = "1" + val;
    obj.setPattern(val);
    assertThat(obj.getPattern()).isEqualTo(val);
  }

  @Test
  public void testMetricsLogsEnabled() {
    MuyieProperties.Metrics.Logs obj = properties.getMetrics().getLogs();
    boolean val = MuyieDefaults.Metrics.Logs.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testMetricsLogsReportFrequency() {
    MuyieProperties.Metrics.Logs obj = properties.getMetrics().getLogs();
    long val = MuyieDefaults.Metrics.Logs.reportFrequency;
    assertThat(obj.getReportFrequency()).isEqualTo(val);
    val++;
    obj.setReportFrequency(val);
    assertThat(obj.getReportFrequency()).isEqualTo(val);
  }

  @Test
  public void testLoggingUseJsonFormat() {
    MuyieProperties.Logging obj = properties.getLogging();
    boolean val = MuyieDefaults.Logging.useJsonFormat;
    assertThat(obj.isUseJsonFormat()).isEqualTo(val);
    val = true;
    obj.setUseJsonFormat(val);
    assertThat(obj.isUseJsonFormat()).isEqualTo(val);
  }

  @Test
  public void testLoggingLogstashEnabled() {
    MuyieProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
    boolean val = MuyieDefaults.Logging.Logstash.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testLoggingLogstashHost() {
    MuyieProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
    String val = MuyieDefaults.Logging.Logstash.host;
    assertThat(obj.getHost()).isEqualTo(val);
    val = "1" + val;
    obj.setHost(val);
    assertThat(obj.getHost()).isEqualTo(val);
  }

  @Test
  public void testLoggingLogstashPort() {
    MuyieProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
    int val = MuyieDefaults.Logging.Logstash.port;
    assertThat(obj.getPort()).isEqualTo(val);
    val++;
    obj.setPort(val);
    assertThat(obj.getPort()).isEqualTo(val);
  }

  @Test
  public void testLoggingLogstashQueueSize() {
    MuyieProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
    int val = MuyieDefaults.Logging.Logstash.queueSize;
    assertThat(obj.getQueueSize()).isEqualTo(val);
    val++;
    obj.setQueueSize(val);
    assertThat(obj.getQueueSize()).isEqualTo(val);
  }

  @Test
  public void testSocialRedirectAfterSignIn() {
    MuyieProperties.Social obj = properties.getSocial();
    String val = MuyieDefaults.Social.redirectAfterSignIn;
    assertThat(obj.getRedirectAfterSignIn()).isEqualTo(val);
    val = "1" + val;
    obj.setRedirectAfterSignIn(val);
    assertThat(obj.getRedirectAfterSignIn()).isEqualTo(val);
  }

  @Test
  public void testGatewayAuthorizedMicroservicesEndpoints() {
    MuyieProperties.Gateway obj = properties.getGateway();
    Map<String, List<String>> val = MuyieDefaults.Gateway.authorizedMicroservicesEndpoints;
    assertThat(obj.getAuthorizedMicroservicesEndpoints()).isEqualTo(val);
    val.put("1", null);
    obj.setAuthorizedMicroservicesEndpoints(val);
    assertThat(obj.getAuthorizedMicroservicesEndpoints()).isEqualTo(val);
  }

  @Test
  public void testGatewayRateLimitingEnabled() {
    MuyieProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
    boolean val = MuyieDefaults.Gateway.RateLimiting.enabled;
    assertThat(obj.isEnabled()).isEqualTo(val);
    val = !val;
    obj.setEnabled(val);
    assertThat(obj.isEnabled()).isEqualTo(val);
  }

  @Test
  public void testGatewayRateLimitingLimit() {
    MuyieProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
    long val = MuyieDefaults.Gateway.RateLimiting.limit;
    assertThat(obj.getLimit()).isEqualTo(val);
    val++;
    obj.setLimit(val);
    assertThat(obj.getLimit()).isEqualTo(val);
  }

  @Test
  public void testGatewayRateLimitingDurationInSeconds() {
    MuyieProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
    int val = MuyieDefaults.Gateway.RateLimiting.durationInSeconds;
    assertThat(obj.getDurationInSeconds()).isEqualTo(val);
    val++;
    obj.setDurationInSeconds(val);
    assertThat(obj.getDurationInSeconds()).isEqualTo(val);
  }

  @Test
  public void testRegistryPassword() {
    MuyieProperties.Registry obj = properties.getRegistry();
    String val = MuyieDefaults.Registry.password;
    assertThat(obj.getPassword()).isEqualTo(val);
    val = "1" + val;
    obj.setPassword(val);
    assertThat(obj.getPassword()).isEqualTo(val);
  }

  @Test
  public void testClientAppName() {
    MuyieProperties.ClientApp obj = properties.getClientApp();
    String val = MuyieDefaults.ClientApp.name;
    assertThat(obj.getName()).isEqualTo(val);
    val = "1" + val;
    obj.setName(val);
    assertThat(obj.getName()).isEqualTo(val);
  }

  @Test
  public void testAuditEventsRetentionPeriod() {
    MuyieProperties.AuditEvents obj = properties.getAuditEvents();
    int val = MuyieDefaults.AuditEvents.retentionPeriod;
    assertThat(obj.getRetentionPeriod()).isEqualTo(val);
    val++;
    obj.setRetentionPeriod(val);
    assertThat(obj.getRetentionPeriod()).isEqualTo(val);
  }
}
