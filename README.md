# MuYie BOM and server-side library for all Spring Boot projects

This project is used by the Spring Boot. This is the Bill of Materials and server-side library:

- muyie-dependencies
- muyie-framework

This project provides a pom file that you can either import into your Spring Boot Application's bom, or use as your Spring Boot Application's parent pom. 

- JDK 8+
- Spring Boot 2.1.9.RELEASE
- Spring Cloud Greenwich.SR3

## Quick Start

The MuYie BOM uses Maven's support for dependency management to provide dependency versions to your Spring Boot Application's build. To consume this dependency management you can import into your Spring Boot Application's pom: 

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.github.zhycn</groupId>
            <artifactId>muyie-dependencies</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Alternatively, rather than importing the MuYie BOM, you may prefer to use it as your Spring Boot Application pom's parent: 

```
<parent>
    <groupId>com.github.zhycn</groupId>
    <artifactId>muyie-dependencies</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath/>
</parent>
```

Importing the dependency into your Spring Boot Application's pom, it provides more common services and supports auto configurations:

```
<dependency>
    <groupId>com.github.zhycn</groupId>
    <artifactId>muyie-framework</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Overriding a version

To override a property in Maven you can then declare the property in your pom’s `<properties>` section with the desired value:

```
<properties>
    <commons-lang3.version>3.8.1</commons-lang3.version>
</properties>
```