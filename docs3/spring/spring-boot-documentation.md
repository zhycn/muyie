# Spring Boot Documentation

!!! info "Spring Boot 文档目录及摘要，通过该文档来深入学习 Spring Boot 的配置和使用。"

    https://spring.io/projects/spring-boot

## [Overview](https://docs.spring.io/spring-boot/docs/current/reference/html/documentation.html)

!!! info "本节内容简要概述 Spring Boot 参考文档，类似于网站地图。"

About the Documentation, First Steps, and more.

1. First Steps
2. Upgrading From an Earlier Version
3. Developing with Spring Boot
4. Learning About Spring Boot Features
5. Web
6. Data
7. Messaging
8. IO
9. Container Images
10. Advanced Topics

## [Getting Started](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html)

!!! info "本节内容主要介绍了 Spring Boot，系统要求，Servlet 容器，安装 Spring Boot 和开发你的第一个 Spring Boot 应用程序。"

Introducing Spring Boot, System Requirements, Servlet Containers, Installing Spring Boot, and
Developing Your First Spring Boot Application.

- [System Requirements](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.system-requirements)
  - Spring Boot 2.7.x requires Java 8 and is compatible up to and including Java 18.
- [Installing Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.installing)
- [Developing Your First Spring Boot Application](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.first-application)

## [Upgrading Spring Boot Applications](https://docs.spring.io/spring-boot/docs/current/reference/html/upgrading.html)

!!! info "本节内容提供了有关如何从早期版本的 Spring Boot 升级的说明。"

Upgrading from 1.x, Upgrading to a new feature release, and Upgrading the Spring Boot CLI.

升级到新版本时，某些属性可能已重命名或删除。Spring Boot
提供了一种在启动时分析应用程序环境和打印诊断信息的方法，还可以在运行时为你临时迁移属性。要启用该功能，请将以下依赖项添加到你的项目中：

```xml title="Maven"
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-properties-migrator</artifactId>
    <scope>runtime</scope>
</dependency>
```

## [# Using Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html)

!!! info "本节内容详细地介绍了如何使用 Spring Boot。它涵盖了构建系统、自动配置以及如何运行应用程序等功能。"

Build Systems, Structuring Your Code, Configuration, Spring Beans and Dependency Injection,
DevTools, and more.

- [Build Systems](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems)
  - Spring Boot 建议使用 Maven 或 Gradle 构建项目。
  - Spring Boot 的每个版本都提供了它支持的依赖管理，你不需要在构建时为这些依赖提供版本。如有需要，你仍然可以指定版本并覆盖 Spring Boot 提供的版本。
- [Spring Boot application starters](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters)
  - 查找 Spring Boot 提供的 Starters，以及了解 Starters 的命名规范。
- [Structuring Your Code](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code)
- [Configuration Classes](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.configuration-classes)
  - 使用 `@Configuration` 注解声明配置类。
  - 使用 `@Import` 注解导入其他配置类，或者使用 `@ComponentScan` 自动获取所有 Spring 组件，包括 `@Configuration` 类。
  - 使用 `@ImportResource` 注解导入基于 XML 的配置。（不推荐）
- [Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.auto-configuration)
  - 使用 `@SpringBootApplication` 或 `@EnableAutoConfiguration` 注释来开启自动配置，通常用于启动类。
  - 使用 `@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })` 禁用特定的自动配置类。
  - 使用 `spring.autoconfigure.exclude` 属性来禁用特定的自动配置类。
- [Spring Beans and Dependency Injection](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.spring-beans-and-dependency-injection)
  - 使用任何标准的 Spring Framework 技术来定义 bean 及其依赖注入，然后通过 `@ComponentScan` 来加载到 Spring 容器管理。
  - 所有应用程序组件（`@Component`、`@Service`、`@Repository`、`@Controller`、`@RestController`等）都会自动注册为 Spring
    Bean。
- [Using the @SpringBootApplication Annotation](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.using-the-springbootapplication-annotation)

!!! info "使用 `@SpringBootApplication` 注解"

    在应用程序启动类上使用 `@SpringBootApplication` 注解来启用这三个功能：

    - `@EnableAutoConfiguration`: 启用 Spring Boot 的自动配置机制。
    - `@ComponentScan` 和 `@Component`: 对应用程序所在的包启用扫描。
    - `@SpringBootConfiguration`: 允许在上下文中注册额外的 bean 或导入额外的配置类。Spring 标准的替代方案 `@Configuration`，可帮助你在集成测试中进行配置检测。

    ```java
        // Same as @SpringBootConfiguration @EnableAutoConfiguration @ComponentScan
        @SpringBootApplication
        public class MyApplication {
        
            public static void main(String[] args) {
                SpringApplication.run(MyApplication.class, args);
            }
        
        }
    ```

- [Running Your Application](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.running-your-application)
  - 从 IDE 将 Spring Boot 应用程序作为 Java 应用程序运行。
  - 作为打包应用程序运行。`java -jar target/myapplication-0.0.1-SNAPSHOT.jar`
  - 使用 Maven 插件。`mvn spring-boot:run`
  - 使用 Gradle 插件。`gradle bootRun`
- [Developer Tools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)

## [# Core Features](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html)

Profiles, Logging, Security, Caching, Spring Integration, Testing, and more.

- [SpringApplication](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.spring-application)
  - [ ] Startup Failure
  - [ ] Lazy Initialization
  - [ ] Customizing the Banner
  - [ ] Customizing SpringApplication
  - [ ] Fluent Builder API
  - [ ] Application Events and Listeners
  - [ ] Web Environment
  - [ ] Using the ApplicationRunner or CommandLineRunner
- [Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
  - [ ] Accessing Command Line Properties
  - [ ] JSON Application Properties
  - [ ] External Application Properties
- [Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles)
- [Logging](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.logging)
- [Internationalization](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.internationalization)
- [JSON](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.json)
- [Task Execution and Scheduling](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.task-execution-and-scheduling)
- [Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [Creating Your Own Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration)
- [Kotlin support](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.kotlin)

## [# Web](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html)

Servlet Web, Reactive Web, GraphQL, Embedded Container Support, Graceful Shutdown, and more.

- [Servlet Web Applications](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet)
- [Reactive Web Applications](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.reactive)
- [Graceful Shutdown](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.graceful-shutdown)
- [Spring Security](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.security)
- [Spring Session](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.spring-session)
- [Spring for GraphQL](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.graphql)
- [Spring HATEOAS](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.spring-hateoas)

## [# Data](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html)

SQL and NOSQL data access.

- [SQL Databases](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql)
- [Working with NoSQL Technologies](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.nosql)

## [# IO](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html)

Caching, Quartz Scheduler, REST clients, Sending email, Spring Web Services, and more.

- [Caching](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.caching)
- [Hazelcast](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.hazelcast)
- [Quartz Scheduler](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.quartz)
- [Sending Email](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.email)
- [Validation](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.validation)
- [Calling REST Services](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.rest-client)
- [Web Services](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.webservices)
- [Distributed Transactions with JTA](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.jta)

## [# Messaging](https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html)

JMS, AMQP, Apache Kafka, RSocket, WebSocket, and Spring Integration.

- [JMS](https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html#messaging.jms)
- [AMQP](https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html#messaging.amqp)
- [Apache Kafka Support](https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html#messaging.kafka)
- [RSocket](https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html#messaging.rsocket)
- [Spring Integration](https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html#messaging.spring-integration)
- [WebSockets](https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html#messaging.websockets)

## [Container Images](https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html)

!!! info "Spring Boot 应用程序可以使用 Dockerfiles 进行容器化，可以在任何地方运行。"

Efficient container images and Building container images with Dockerfiles and Cloud Native
Buildpacks.

- [Efficient container images](https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html#container-images.efficient-images)
- [Dockerfiles](https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html#container-images.dockerfiles)

## [Production-ready Features](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)

???+ info "Spring Boot 包含许多附加功能，可帮助你在将应用程序推送到生产环境时对其进行监控和管理。"

    `spring-boot-actuator` 模块提供了 Spring Boot 的所有生产就绪功能，可帮助你在将应用程序推送到生产环境时对其进行监控和管理。
    你可以选择使用 HTTP 端点或 JMX来管理和监视你的应用程序。审计、健康和指标收集也可以自动应用于你的应用程序。

    ```xml title="Maven"
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>
    ```

    启动应用程序，浏览器访问：http://localhost:8080/actuator

    ```json
    {
        "_links":{
            "self":{
                "href":"http://localhost:8080/actuator",
                "templated":false
            },
            "health":{
                "href":"http://localhost:8080/actuator/health",
                "templated":false
            },
            "health-path":{
                "href":"http://localhost:8080/actuator/health/{*path}",
                "templated":true
            }
        }
    }
    ```

    默认情况下，只有 `health` 端点对外暴露。要更改公开的端点，请使用以下技术特定 include 和 exclude 属性：

    ```properties
    management.endpoints.jmx.exposure.exclude=
    management.endpoints.jmx.exposure.include=*
    management.endpoints.web.exposure.exclude=
    management.endpoints.web.exposure.include=health
    ```

    **有关 actuator 的更多配置可查看官方文档，如无必要保持默认配置即可。**

Monitoring, Metrics, Auditing, and more.

1. Enabling Production-ready Features
2. Endpoints
3. Monitoring and Management over HTTP
4. Monitoring and Management over JMX
5. Loggers
6. Metrics
7. Auditing
8. HTTP Tracing
9. Process Monitoring
10. Cloud Foundry Support

## [Deploying Spring Boot Applications](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html)

!!! info "部署 Spring Boot 应用程序"

    在部署应用程序时，Spring Boot 的灵活打包选项提供了大量选择。
    你可以将 Spring Boot 应用程序部署到各种云平台、虚拟机/真实机上，或者 Unix 系统。

Deploying to the Cloud, and Installing as a Unix application.

- [Deploying to the Cloud](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html#deployment.cloud)
- [Installing Spring Boot Applications](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html#deployment.installing)

## [Spring Boot CLI](https://docs.spring.io/spring-boot/docs/current/reference/html/cli.html)

!!! info "Spring Boot CLI"

    Spring Boot CLI 是一个命令行工具，如果你想快速开发 Spring 应用程序，可以使用它。
    它允许你运行 Groovy 脚本，这意味着你拥有熟悉的类似 Java 的语法，而无需太多样板代码。
    你还可以引导一个新项目或为其编写自己的命令。

Installing the CLI, Using the CLI, Configuring the CLI, and more.

## [Build Tool Plugins](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins.html)

!!! info "Spring Boot 为 Maven 和 Gradle 提供了构建工具插件，这些插件提供了多种功能，包括可执行 jar 的打包。"

Maven Plugin, Gradle Plugin, Antlib, and more.

- [Spring Boot Maven 插件](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)
- [Spring Boot Gradle 插件](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/)

## [“How-to” Guides](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html)

!!! info "本节内容提供了使用 Spring Boot 时经常出现的一些常见问题的答案。它的覆盖范围并不详尽，但确实涵盖了很多方面。"

Application Development, Configuration, Embedded Servers, Data Access, and many more.

1. Spring Boot Application
2. Properties and Configuration
3. Embedded Web Servers
4. Spring MVC
5. Jersey
6. HTTP Clients
7. Logging
8. Data Access
9. Database Initialization
10. Messaging
11. Batch Applications
12. Actuator
13. Security
14. Hot Swapping
15. Testing
16. Build
17. Traditional Deployment

## Appendix

!!! info "附录"

- [Application Properties - 应用属性配置参数](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
  - Common application properties that you can use to configure your application.
- [Configuration Metadata - 配置元数据](https://docs.spring.io/spring-boot/docs/current/reference/html/configuration-metadata.html)
  - Metadata that you can use to describe configuration properties.
- [Auto-configuration Classes - 自动配置类](https://docs.spring.io/spring-boot/docs/current/reference/html/auto-configuration-classes.html)
  - Auto-configuration classes provided by Spring Boot.
- [Test Auto-configuration Annotations - 测试自动配置注解](https://docs.spring.io/spring-boot/docs/current/reference/html/test-auto-configuration.html)
  - Test auto-configuration annotations that you can use to test slices of your application.
- [Executable Jars - 可执行的 Jar 文件](https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html)
  - Spring Boot’s executable jars, their launchers, and their format.
- [Dependency Versions - 依赖版本](https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html)
  - Details of the dependencies that are managed by Spring Boot.
