# MuYie BOM and server-side library

**MuYie 是一个基于 Spring Boot 和 Spring Cloud 的快速开发框架！**

## 背景

在项目开发时，经常会用到第三方库，开发人员通常都是根据经验或直接选择较新的版本，随意性较大。
随着业务复杂度的增加，集成的第三方库会越来越多，依赖关系也会变得更加复杂。
除非做过完整的兼容性测试，保证集成第三方库的版本不会出问题，且后续集成或升级都不会出问题，否则就有可能存在较大的隐性风险（如版本冲突）。

作为企业级项目开发，统一管理和维护一套开发框架是必要的。同时，将基础开发组件进行抽象和封装，统一规范，快速集成，让开发人员把更多的时间和精力放在业务功能上。

MuYie 的目标就是解决这些问题。
开发人员在添加第三方库时，不需要关心版本号，MuYie / Spring Boot / Spring Cloud 依赖管理会帮助我们提供一个最优的版本，
而且该版本是经过严格测试的，可以更好地兼容其它的组件。同时，你也可以根据自己的需要来升级或降级依赖版本。

MuYie 完全兼容 Spring Boot 和 Spring Cloud，通过 Spring Boot 从根本上简化开发体检。

## 环境要求

- JDK 8+
- Spring Boot 2.7.13
- Spring Cloud 2021.0.8

## 快速集成

1）在你的 Spring Boot 项目中将其作为父模块引入（**推荐**）：

```
<parent>
    <groupId>com.github.zhycn</groupId>
    <artifactId>muyie-dependencies</artifactId>
    <version>2.7.13-SNAPSHOT</version>
    <relativePath/>
</parent>
```

2）或者，在你的 Maven 项目中添加依赖管理：

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.github.zhycn</groupId>
            <artifactId>muyie-dependencies</artifactId>
            <version>2.7.13-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Spring Boot 项目想要生成可执行 JAR 文件，需要在启动程序的 pom.xml 中要添加 Spring Boot Maven 插件：

```
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

## 依赖管理

MuYie 完全支持 Spring Boot 和 Spring Cloud 管理的所有依赖项。 以下列出了 MuYie 管理的依赖版本的详细信息：

| Group ID                        | Artifact ID                  | Version    | Version Property          | Documentation                                                            |
|---------------------------------|------------------------------|------------|---------------------------|--------------------------------------------------------------------------|
| org.springframework.boot        | spring-boot-starter-parent   | 2.7.13     | spring-boot.version       | [Spring Boot](https://spring.io/projects/spring-boot)                    |
| org.springframework.cloud       | spring-cloud-dependencies    | 2021.0.8   | spring-cloud.version      | [Spring Cloud](https://spring.io/projects/spring-cloud)                  |
| org.redisson                    | redisson-spring-boot-starter | 3.22.0     | redisson.version          | [Redisson](https://github.com/redisson/redisson)                         |
| com.alibaba                     | druid-spring-boot-starter    | 1.2.18     | alibaba-druid.version     | [Druid](https://github.com/alibaba/druid)                                |
| com.alibaba.fastjson2           | fastjson2                    | 2.0.35     | alibaba-fastjson2.version | [Fastjson2](https://github.com/alibaba/fastjson2)                        |
| commons-io                      | commons-io                   | 2.13.0     | commons-io.version        | [Apache Commons IO](https://github.com/apache/commons-io)                |
| com.ctrip.framework.apollo      | apollo-client                | 2.1.0      | apollo-client.version     | [ApolloConfig](https://www.apolloconfig.com/)                            |
| com.google.guava                | guava                        | 32.1.1-jre | guava.version             | [Guava](https://github.com/google/guava)                                 |
| com.baomidou                    | mybatis-plus-boot-starter    | 3.5.3.1    | mybatis-plus.version      | [MyBatis-Plus](https://baomidou.com/)                                    |
| com.squareup.retrofit2          | retrofit                     | 2.9.0      | retrofit.version          | [Retrofit](https://square.github.io/retrofit/)                           |
| cn.hutool                       | hutool-all                   | 5.8.20     | hutool.version            | [Hutool](https://hutool.cn/)                                             |
| org.zalando                     | problem-spring-web           | 0.27.0     | problem-spring.version    | [Zalando Problem](https://github.com/zalando/problem/)                   |
| org.reflections                 | reflections                  | 0.10.2     | reflections.version       | [Java runtime metadata analysis](https://github.com/ronmamo/reflections) |
| org.springdoc                   | springdoc-openapi-ui         | 1.7.0      | springdoc.version         | [SpringDoc](https://springdoc.org/)                                      |
| org.lz4                         | lz4-java                     | 1.8.0      | lz4-java.version          | [LZ4 compression for Java](https://github.com/lz4/lz4-java/)             |
| org.jsoup                       | jsoup                        | 1.16.1     | jsoup.version             | [Jsoup](https://jsoup.org/)                                              |
| org.htmlunit                    | htmlunit                     | 3.3.0      | htmlunit.version          | [HtmlUnit](https://htmlunit.sourceforge.io/)                             |
| io.jsonwebtoken                 | jjwt-api                     | 0.11.5     | jjwt.version              | [JJWT](https://github.com/jwtk/jjwt)                                     |
| io.jsonwebtoken                 | jjwt-impl                    | 0.11.5     | jjwt.version              | [JJWT](https://github.com/jwtk/jjwt)                                     |
| io.jsonwebtoken                 | jjwt-jackson                 | 0.11.5     | jjwt.version              | [JJWT](https://github.com/jwtk/jjwt)                                     |

在集成 MuYie 提供的依赖管理后，你在项目中添加依赖管理时，不需要指定依赖包的版本号。如下所示：

```xml title="添加依赖管理"
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson-spring-boot-starter</artifactId>
</dependency>
```

## 覆盖版本

MuYie / Spring Boot / Spring Cloud 项目集成的第三方库的版本信息都是使用 `<properties />` 来定义的，
这就使得你可以很容易的升级或降级某个依赖包的版本。在你的项目中指定依赖包的版本号就能覆盖之前的版本：

```xml title="覆盖版本属性"
<!-- 应根据项目的实际运行环境来合理选择版本号，除非必须，否则不建议修改版本信息。 -->
<!-- 在修改依赖包的版本信息时，可通过 IDE 点击坐标来查看依赖包对应的属性名。 -->
<properties>
  <guava.version>32.1.1-jre</guava.version>
</properties>
```

## 组件

### muyie-starter-apollo

Spring Boot 与 Apollo Client 集成。 该组件解决的问题是：当你在 Spring Boot 中使用 `@ConfigurationProperties`
注解配置参数时，Apollo Client 无法自动更新。

```xml
<dependency>
  <groupId>com.github.zhycn</groupId>
  <artifactId>muyie-starter-apollo</artifactId>
</dependency>
```

- [ApolloConfig - 官方文档](https://www.apolloconfig.com/)
- [Spring Boot 与 Apollo Client 集成详解](https://blog.csdn.net/weixin_50549211/article/details/129375048)

### muyie-starter-mybatis

Spring Boot 与 MyBatis-Plus 集成，让数据层代码编写更简单方便。

```xml

<dependency>
  <groupId>com.github.zhycn</groupId>
  <artifactId>muyie-starter-mybatis</artifactId>
</dependency>
```

- [MyBatis-Plus - 官方文档](https://www.baomidou.com/)
- [dynamic-datasource - 动态数据源（未集成）](https://github.com/baomidou/dynamic-datasource-spring-boot-starter)

### muyie-starter-redis

Spring Boot 与 Redis/Redisson 集成，开箱即用。提供了 Redis 对象序列化配置和基于 Redis 的 CacheManager 配置。

```xml

<dependency>
  <groupId>com.github.zhycn</groupId>
  <artifactId>muyie-starter-redis</artifactId>
</dependency>
```

- [Redisson - 官方文档](https://github.com/redisson/redisson)
- [Redis.cn - 中文网](http://www.redis.cn/)

