# Lombok 使用指南

Lombok 是一个 Java 库，能自动插入编辑器和构建工具中，简化 Java 开发。
通过添加注解的方式，不需要为类编写 getter 或 equals 等方法，同时可以自动化日志变量。

简而言之：Lombok 能以简单的注解形式来简化 Java 代码，提高开发人员的开发效率。

官网链接：https://projectlombok.org/

!!! tip "Tips"

    以前的 Java 项目中，充斥着太多不友好的代码：POJO 的 getter / setter / toString；异常处理；I/O 流的关闭操作等等，
    这些样板代码既没有技术含量，又影响着代码的美观，Lombok 应运而生。对于 Lombok 的使用，也存在着不少争论。
    但是，IDEA 2020 最后一个发布版本已经内置了 Lombok 插件，并且 SpringBoot 2.1.x 之后的版本也在 Starter 中内置了 Lombok 依赖。
    为什么他们都要支持 Lombok 呢？本文就来聊聊 Lombok 的使用，看看它有何神奇之处！

主要特征：

- [@Getter and @Setter](https://projectlombok.org/features/GetterSetter)
- [@NonNull](https://projectlombok.org/features/NonNull)
- [@ToString](https://projectlombok.org/features/ToString)
- [@EqualsAndHashCode](https://projectlombok.org/features/EqualsAndHashCode)
- [@NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor](https://projectlombok.org/features/constructor)
- [@Data](https://projectlombok.org/features/Data)
- [@SneakyThrows](https://projectlombok.org/features/SneakyThrows)

## 快速使用

1）在你的 Spring Boot 项目的中添加依赖配置：

```xml

<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <scope>provided</scope>
</dependency>
```

2）常用注解说明：

| 注解                       | 说明                                                                                                            |
|--------------------------|---------------------------------------------------------------------------------------------------------------|
| @Setter                  | 注解在类或字段，注解在类时为所有字段生成setter方法，注解在字段上时只为该字段生成setter方法。                                                          |
| @Getter                  | 注解在类或字段，注解在类时为所有字段生成getter方法，注解在字段上时只为该字段生成getter方法。                                                          |
| @ToString                | 注解在类，生成toString方法。                                                                                            |
| @EqualsAndHashCode       | 注解在类，生成hashCode和equals方法。                                                                                     |
| @NoArgsConstructor       | 注解在类，生成无参的构造方法。                                                                                               |
| @RequiredArgsConstructor | 注解在类，为类中需要特殊处理的字段生成构造方法，比如final和被 `@NonNull` 注解的字段。                                                           |
| @AllArgsConstructor      | 注解在类，生成包含类中所有字段的构造方法。                                                                                         |
| @NonNull                 | 注解在字段或方法，非空校验，抛出NullPointerException的异常。                                                                      |
| @Data                    | 注解在类，等价于`@Getter` `@Setter` `@RequiredArgsConstructor` `@ToString` `@EqualsAndHashCode`。                      |
| @SneakyThrows            | 注解在方法，抛出异常（慎用）。                                                                                               |
| @Slf4j                   | 注解在类，生成log变量，严格意义来说是常量。等价于：<br>private static final Logger log=LoggerFactory.getLogger(UserController.class); |

## 安装插件

在 IntelliJ IDEA 或者 Eclipse 中使用 Lombok 需要安装 Lombok Plugin。
因此，Lombok 也存在一定风险，在一些开发工具中没有 Project Lombok 支持选择。
IDE 和 JDK 升级也存在破裂的风险，以及围绕项目的目标和实施存在争议。

- Eclipse: https://projectlombok.org/setup/eclipse
- IntelliJ IDEA: https://projectlombok.org/setup/intellij
