# MuYie BOM and server-side library

**MuYie 是一个基于 Spring Boot 和 Spring Cloud 的快速开发框架！**

## 概述

在项目构建时，经常会用到第三方库，开发人员通常都是根据经验或直接选择较新的版本，随意性较大。
随着业务复杂度的增加，集成的第三方库会越来越多，依赖关系也会变得更加复杂。
除非做过完整的兼容性测试，保证集成第三方库的版本不会出问题，且后续集成或升级都不会出问题，否则就有可能存在较大的隐性风险（如版本冲突）。

MuYie 的主要目的就在于帮助我们解决这些问题。
开发人员在添加第三方库时，不需要关心版本号，MuYie / Spring Boot / Spring Cloud 依赖管理会帮助我们提供一个最优的版本，
而且该版本是经过严格测试的，可以更好地兼容其它的组件。同时，你也可以根据自己的需要来升级或降级依赖版本。

MuYie 完全兼容 Spring Boot 和 Spring Cloud，致力于提供基础开发组件的封装，通过 Spring Boot 从根本上简化开发体检。

## 快速开始

1）在你的 Spring Boot 项目中将其作为父模块引入（**推荐**）：

```
<parent>
    <groupId>com.github.zhycn</groupId>
    <artifactId>muyie-dependencies</artifactId>
    <version>${muyie-framework.version}</version>
    <relativePath/>
</parent>
```

2）或者，在你的 Maven 项目中添加 MuYie 提供的依赖管理：

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.github.zhycn</groupId>
            <artifactId>muyie-dependencies</artifactId>
            <version>${muyie-framework.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

!!! tip "Tips"

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
