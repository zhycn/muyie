# Apollo Client 使用指南

Apollo（阿波罗）是一款可靠的分布式配置管理中心，诞生于携程框架研发部，能够集中化管理应用不同环境、不同集群的配置，配置修改后能够实时推送到应用端，并且具备规范的权限、流程治理等特性，适用于微服务配置管理场景。

服务端基于 Spring Boot 和 Spring Cloud 开发，打包后可以直接运行，不需要额外安装 Tomcat 等应用容器。

Java 客户端不依赖任何框架，能够运行于所有 Java 运行时环境，同时对 Spring / Spring Boot 环境也有较好的支持。

更多产品介绍请查看官方文档：https://www.apolloconfig.com/

!!! tip "组件特征"

    **`muyie-starter-apollo` 组件是基于 Spring Boot 和 Apollo Client 开发的快速集成方案，开箱即用。
    重点解决了在 Spring Boot 中使用 `@ConfigurationProperties` 注解时配置参数不更新的问题。**

## 快速集成

1）在你的 Spring Boot 项目中添加依赖配置：

```xml title="Maven Dependency"
<!-- Apollo Client >=2.0.0 -->
<dependency>
  <groupId>com.github.zhycn</groupId>
  <artifactId>muyie-starter-apollo</artifactId>
  <version>${muyie-framework.version}</version>
</dependency>
```

2）添加配置参数（**以下是推荐配置**）：

```properties title="推荐配置"
## Apollo Configuration
# 多环境时，需要通过启动参数来指定配置环境：-Denv=DEV
app.id=YOUR-APP-ID
apollo.bootstrap.enabled=true
apollo.bootstrap.eagerLoad.enabled=true
apollo.bootstrap.namespaces=application
apollo.meta=http://config-service-url
apollo.cache-dir=./config-cache
apollo.cluster=default
```

!!! success "Tips"

    简单两步，就可以快速地将 Spring Boot 项目的配置参数迁移到 Apollo 配置中心，代码不需要做任何改动。

## 自定义设置

**`@ApolloConfigChangeListener` 注解默认只能自动更新 `application` 命名空间的配置属性，其他命名空间的配置属性更新可参考下列配置：**

```java

@Configuration
public class AppConfig extends BaseConfigChangeListener {

  @Override
  @ApolloConfigChangeListener({"someNamespace", "anotherNamespace"})
  public void onChange(ConfigChangeEvent configChangeEvent) {
    refreshConfigChange(configChangeEvent);
  }
}

```

## 配置参数详解

**Apollo Client 支持通过启动参数和 Spring Boot application.properties / bootstrap.properties 属性文件配置。**

以下是 Apollo Client
配置参数的详细说明，查看官方文档可了解更多参数配置信息：https://www.apolloconfig.com/#/zh/usage/java-sdk-user-guide

```properties title="配置参数详解"
# [必选] 开启 Apollo 配置
# [注意] 如果配置中使用了 @EnableApolloConfig 注解，该配置将不会生效。
apollo.bootstrap.enabled=true

# [必选] 指定 app.id
# AppId 是应用的身份信息，是从服务端获取配置的一个重要信息。
# 启动参数：-Dapp.id=YOUR-APP-ID
# 系统参数：APP_ID=YOUR-APP-ID
app.id=YOUR-APP-ID

# [必选] 指定配置服务器地址
# 为了实现 meta server 的高可用，推荐通过 SLB 做动态负载均衡。
# 启动参数：-Dapollo.meta=http://config-service-url
# 系统参数：APOLLO_META=http://config-service-url
# 如果是在运行 JAR 文件中指定，需要注意格式是：java -Dapollo.meta=http://config-service-url -jar xxx.jar
apollo.meta=http://config-service-url

# [可选] 指定配置环境
# 启动参数：-Denv=DEV
# 系统参数：ENV=DEV
# 多环境时，该参数必须通过启动参数或系统参数来指定。
# 如果是运行 JAR 文件，需要注意格式是：java -Denv=DEV -jar xxx.jar

# [可选] 指定集群环境
# Apollo 支持配置按照集群划分，也就是说对于一个 AppId 和一个环境，对不同的集群可以有不同的配置。
# 如果还是没找到指定集群，会从默认的集群（default）加载。
# 启动参数：-Dapollo.cluster=SomeCluster
apollo.cluster=default

# [可选] 设置内存中的配置项是否保持和页面上的顺序一致，默认值：false
# 有些场景会强依赖配置项的顺序（如 Spring Cloud Zuul 的路由规则）
# 针对这种情况，可以开启 OrderedProperties 特性来使得内存中的配置顺序和页面上看到的一致。
# -Dapollo.property.order.enable=true
# apollo.property.order.enable=false

# [可选] 将 Apollo 配置加载提到初始化日志系统之前，不过这会导致 Apollo 的启动过程无法通过日志的方式输出。
apollo.bootstrap.eagerLoad.enabled=true

# [可选] 自定义缓存路径
# 启动参数：-Dapollo.cache-dir=/opt/data/config-cache
# 系统参数：APOLLO_CACHE_DIR=/opt/data/config-cache
apollo.cache-dir=/opt/data/config-cache

# [可选] 配置访问密钥（如果配置中心要求使用访问密钥，则必须配置）
# 启动参数：-Dapollo.access-key.secret=1cf998c4e2ad4704b45a98a5
# 系统参数：APOLLO_ACCESS_KEY_SECRET=1cf998c4e2ad4704b45a98a5
# 如果是运行 JAR 文件，需要注意格式是：java -Dapollo.access-key.secret=1cf998c4e2ad4704b45a98a5 -jar xxx.jar
# apollo.access-key.secret=1cf998c4e2ad4704b45a98a509d15719

# [必选] 指定加载的命名空间，默认会加载 application
apollo.bootstrap.namespaces=application,FX.apollo,application.yml
```

!!! tip "Environment 可以通过以下3种方式的任意一个配置"

    **1）通过 Java System Property (推荐)**
    
    可以通过 Java 的 System Property env 来指定环境。
    
    - 在 Java 程序启动脚本中，可以指定 -Denv=YOUR-ENVIRONMENT
    - 如果是运行 JAR 文件，需要注意格式是 java -Denv=YOUR-ENVIRONMENT -jar xxx.jar
    - 注意 key 为全小写
    
    **2）通过操作系统的 System Environment**
    
    - 还可以通过操作系统的 System Environment ENV 来指定 ENV=YOUR-ENVIRONMENT
    - 注意 key 为全大写
    
    **3）通过配置文件**
    
    最后一个推荐的方式是通过配置文件来指定 env=YOUR-ENVIRONMENT
    
    - 对于 Mac/Linux，默认文件位置为 /opt/settings/server.properties
    - 对于 Windows，默认文件位置为 C:\opt\settings\server.properties

## 本地缓存路径

Apollo 客户端会把从服务端获取到的配置在本地文件系统缓存一份，用于在遇到服务不可用，或网络不通的时候，依然能从本地恢复配置，不影响应用正常运行。

本地缓存路径默认位于以下路径，所以请确保 /opt/data 或 C:\opt\data\ 目录存在，且应用有读写权限。

- Mac/Linux: /opt/data/{appId}/config-cache
- Windows: C:\opt\data\{appId}\config-cache

**或者，指定配置文件路径（推荐）：**

```properties
apollo.cache-dir=./config-cache
```

本地配置文件会以下面的文件名格式放置于本地缓存路径下：

```
{appId}+{cluster}+{namespace}.properties
```

- appId 就是应用自己的 appId（可规范命名规则），如 100004458
- cluster 就是应用使用的集群，一般在本地模式下没有做过配置的话，就是 default
- namespace 就是应用使用的配置 namespace，一般是 application

---

## 在 Spring 中使用

Spring 应用通常会使用 Placeholder 来注入配置，使用格式如 `${someKey:someDefaultValue}`。冒号前面的是 key，冒号后面的是默认值。
__建议在实际使用时尽量给出默认值，以免由于 key 没有定义导致运行时错误。__

!!! tip "Tips"

    从 Apollo v0.10.0 开始的版本支持 Placeholder 在运行时自动更新。

**1）假设有一个 TestJavaConfigBean，通过 Java Config 的方式可以使用 `@Value` 的方式注入：**

```java
public class TestJavaConfigBean {

  @Value("${timeout:100}")
  private int timeout;
  private int batch;

  @Value("${batch:200}")
  public void setBatch(int batch) {
    this.batch = batch;
  }

  public int getTimeout() {
    return timeout;
  }

  public int getBatch() {
    return batch;
  }
}
```

在 Configuration 类中按照下面的方式使用（假设应用默认的 application namespace 中有 timeout 和 batch 的配置项）：

```java

@Configuration
public class AppConfig {

  @Bean
  public TestJavaConfigBean javaConfigBean() {
    return new TestJavaConfigBean();
  }

}
```

**2）Spring Boot 提供了 `@ConfigurationProperties` 把配置注入到 bean 对象中。**

!!! tip "Tips"

    **Apollo Client 暂不支持配置属性自动更新，muyie-starter-apollo 组件重点解决了该问题。**

Apollo 也支持这种方式，下面的例子会把 redis.cache.expireSeconds 和 redis.cache.commandTimeout 分别注入到
SampleRedisConfig 的 expireSeconds 和 commandTimeout 字段中。

```java

@ConfigurationProperties(prefix = "redis.cache")
public class SampleRedisConfig {

  private int expireSeconds;
  private int commandTimeout;

  public void setExpireSeconds(int expireSeconds) {
    this.expireSeconds = expireSeconds;
  }

  public void setCommandTimeout(int commandTimeout) {
    this.commandTimeout = commandTimeout;
  }
}
```

在 Configuration 类中按照下面的方式使用（假设应用默认的 application namespace 中有 redis.cache.expireSeconds 和
redis.cache.commandTimeout 的配置项）：

```java

@Configuration
public class AppConfig {

  @Bean
  public SampleRedisConfig sampleRedisConfig() {
    return new SampleRedisConfig();
  }
}
```
