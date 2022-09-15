# 依赖版本

本文提供了 MuYie 管理的依赖版本的详细信息，完全支持 Spring Boot 和 Spring Cloud 管理的所有依赖项。

## 坐标管理

以下列出了 MuYie 管理的依赖版本的详细信息，当你添加的依赖管理未指定版本时，将使用以下版本号。Spring Boot
的依赖版本清单请查看文档：https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html

| Group ID                    | Artifact ID                             | Version  | Version Property            | Documentation                                                                             |
|-----------------------------|-----------------------------------------|----------|-----------------------------|-------------------------------------------------------------------------------------------|
| org.springframework.boot    | spring-boot-starter-parent              | 2.7.2    | spring-boot.version         | [Spring Boot](https://spring.io/projects/spring-boot)                                     |
| org.springframework.cloud   | spring-cloud-dependencies               | 2021.0.3 | spring-cloud.version        | [Spring Cloud](https://spring.io/projects/spring-cloud)                                   |
| org.redisson                | redisson-spring-boot-starter            | 3.17.5   | redisson.version            | [Redisson](https://github.com/redisson/redisson)                                          |
| com.alibaba                 | druid-spring-boot-starter               | 1.2.9    | alibaba-druid.version       | [Druid](https://github.com/alibaba/druid)                                                 |
| com.alibaba                 | fastjson                                | 1.2.83   | alibaba-fastjson.version    | [Fastjson](https://github.com/alibaba/fastjson)                                           |
| com.alibaba.csp             | sentinel-core                           | 1.8.4    | alibaba-sentinel.version    | [Sentinel](https://sentinelguard.io/zh-cn/index.html)                                     |
| com.ctrip.framework.apollo  | apollo-client                           | 2.0.1    | apollo-client.version       | [ApolloConfig](https://www.apolloconfig.com/)                                             |
| org.bouncycastle            | bcpkix-jdk15on                          | 1.70     | bouncycastle.version        | [Bouncy Castle Crypto](https://github.com/open-keychain/bouncycastle)                     |
| org.bouncycastle            | bcprov-jdk15to18                        | 1.70     | bouncycastle.version        | [Bouncy Castle Crypto](https://github.com/open-keychain/bouncycastle)                     |
| org.apache.dubbo            | dubbo-bom                               | 3.0.10   | dubbo.version               | [Apache Dubbo](https://dubbo.apache.org/zh/)                                              |
| org.apache.dubbo            | dubbo-dependencies-zookeeper            | 3.0.10   | dubbo.version               | [Apache Dubbo](https://dubbo.apache.org/zh/)                                              |
| org.apache.dubbo            | dubbo                                   | 3.0.10   | dubbo.version               | [Apache Dubbo](https://dubbo.apache.org/zh/)                                              |
| org.apache.dubbo            | dubbo-spring-boot-starter               | 3.0.10   | dubbo.version               | [Apache Dubbo](https://dubbo.apache.org/zh/)                                              |
| com.google.guava            | guava                                   | 31.1-jre | guava.version               | [Guava](https://github.com/google/guava)                                                  |
| cn.hutool                   | hutool-all                              | 5.8.5    | hutool.version              | [Hutool](https://hutool.cn/)                                                              |
| org.mybatis.spring.boot     | mybatis-spring-boot-starter             | 2.2.2    | mybatis-springboot.version  | [MyBatis-Spring-Boot-Starter](https://github.com/mybatis/spring-boot-starter)             |
| com.github.pagehelper       | pagehelper-spring-boot-starter          | 1.4.2    | mybatis-pagehelper.version  | [PageHelper-Spring-Boot-Starter](https://github.com/pagehelper/pagehelper-spring-boot)    |
| com.baomidou                | mybatis-plus-boot-starter               | 3.5.2    | mybatis-plus.version        | [MyBatis-Plus](https://baomidou.com/)                                                     |
| com.baomidou                | dynamic-datasource-spring-boot-starter  | 3.5.1    | dynamic-datasource.version  | [Dynamic DataSource](https://github.com/baomidou/dynamic-datasource-spring-boot-starter)  |
| org.zalando                 | problem-spring-web                      | 0.27.1   | problem-spring-web.version  | [Zalando Problem](https://github.com/zalando/problem/)                                    |
| com.squareup.retrofit2      | retrofit                                | 2.9.0    | retrofit.version            | [Retrofit](https://square.github.io/retrofit/)                                            |
| com.squareup.retrofit2      | converter-jackson                       | 2.9.0    | retrofit.version            | [Retrofit](https://square.github.io/retrofit/)                                            |
| org.springdoc               | springdoc-openapi-ui                    | 1.6.9    | springdoc.version           | [SpringDoc](https://springdoc.org/)                                                       |

在集成 MuYie 提供的依赖管理后，你在项目中添加依赖管理时，不需要指定依赖包的版本号。如下所示：

```xml title="添加依赖管理"
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
</dependency>
```

## 覆盖版本

MuYie / Spring Boot / Spring Cloud 项目集成的第三方库的版本信息都是使用 `<properties />` 来定义的，
这就使得你可以很容易的升级或降级某个依赖包的版本。在你的项目中指定依赖包的版本号就能覆盖之前的版本：

```xml title="覆盖版本属性"
<properties>
  <foo.version>1.2.0.RELEASE</foo.version>
</properties>
```

!!! tip "Tips"

    在使用 MuYie 项目的依赖管理时，应根据项目的实际运行环境来合理选择版本号，除非必须，否则不建议修改版本信息。
    在修改依赖包的版本信息时，可通过 IDE 点击坐标来查看依赖包对应的属性名。

## 已知问题

由于 **Google Guava** 类库的广泛使用，引用不同的项目时可能存在不兼容情况。这时候需要我们手动指定合适的版本号以保证项目能够正常运行。

```xml title="指定版本属性"
<properties>
  <guava.version>31.1-jre</guava.version>
</properties>
```
