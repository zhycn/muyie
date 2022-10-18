# Spring Boot HikariCP

[HikariCP](https://github.com/brettwooldridge/HikariCP) 是一个高性能的 JDBC 连接池组件。

Spring Boot 2.x 将其作为默认的连接池组件，项目中添加 `spring-boot-starter-jdbc` 或 `spring-boot-starter-data-jpa`
模块后，HikariCP 依赖会被自动引入。

## 快速使用

1）在你的 Spring Boot 项目中添加依赖配置：

```
<dependency>
    <groupId>com.tsollu</groupId>
    <artifactId>tsollu-starter-mybatis</artifactId>
    <version>${tsollu.version}</version>
</dependency>
```

以下常用的 ORM 组件中已经包含了 JDBC 依赖，不需要重复引入：

- spring-boot-starter-data-jdbc
- spring-boot-starter-data-jpa
- mybatis-spring-boot-starter
- mybatis-plus-boot-starter

2）添加数据源配置：

```
## Spring HikaraDataSource Configuration
# spring.datasource.type=com.zaxxer.hikari.HikariDataSource
# spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# spring.datasource.name=HikariCP-1
spring.datasource.url=jdbc:mysql://localhost:3306/dbname?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.maximum-pool-size=100
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.pool-name=HikaraPool-1
```

## 连接池配置

1）在 Spring Boot 项目中，一个简单的 Spring DataSource 配置，通常只需要设置数据库连接、用户名和密码三个参数。

```
## Spring DataSourceProperties

# 设置连接池类型，默认自动获取（可选）
spring.datasource.type=com.zaxxer.hikari.HikariDataSource

# 设置数据库驱动，默认自动获取（可选）
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# 生成唯一的数据源名称，设置与否，都会优先取 name 的值（可选）
spring.datasource.generate-unique-name=true

# 设置数据源名称，默认会生成唯一的数据源名称，如：HikariPool-1（可选）
spring.datasource.name=HikariCP-1

# 设置数据库连接（必选）
spring.datasource.url=jdbc:mysql://localhost:3306/dbname?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull

# 设置数据库用户名（必选）
spring.datasource.username=root

# 设置数据库密码（必选）
spring.datasource.password=123456
```

2）在 Spring Boot 项目中， Spring DataSource 会使用默认的配置启用 HikaraCP 数据库连接池。我们也可以通过属性文件来优化 HikariCP
的配置项，尤其是连接池大小的设置。

```
## Spring HikariConfig

# 事务自动提交 - 默认值：true
spring.datasource.hikari.auto-commit=true

# 连接测试查询 - Using the JDBC4 <code>Connection.isValid()</code> method to test connection validity can be more efficient on some databases and is recommended.
# 如果你的驱动程序支持JDBC4，强烈建议不要设置此属性。
spring.datasource.hikari.connection-test-query=select 1

# 连接超时时间 - 默认值：30秒。
spring.datasource.hikari.connection-timeout=30000

# 连接池中允许闲置的最长时间 - 默认值：10分钟
spring.datasource.hikari.idle-timeout=600000

# 一个连接生命时长（毫秒），超时而没被使用则被释放 - 默认值：30分钟
spring.datasource.hikari.max-lifetime=1800000

# 连接池中允许的最大连接数，包括闲置和使用中的连接 - 默认值：10
spring.datasource.hikari.maximum-pool-size=100

# 连接池中允许的最小空闲连接数 - 默认值：10。
spring.datasource.hikari.minimum-idle=10

# 连接被测试活动的最长时间 - 默认值：5秒。
spring.datasource.hikari.validation-timeout=5000

# 指定连接池的名称 - 默认自动生成
spring.datasource.hikari.pool-name=HikaraPool-1
```

## 多数据源配置

1）添加多数据源配置：

```
## Spring HikaraDataSource Configuration
# spring.datasource.one.driver-class-name=com.mysql.cj.jdbc.Driver
# spring.datasource.one.name=HikariCP-1
spring.datasource.one.url=jdbc:mysql://localhost:3306/kaddo-sit?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull
spring.datasource.one.username=root
spring.datasource.one.password=123456
spring.datasource.one.hikari.connection-timeout=30000
spring.datasource.one.hikari.idle-timeout=600000
spring.datasource.one.hikari.max-lifetime=1800000
spring.datasource.one.hikari.maximum-pool-size=100
spring.datasource.one.hikari.minimum-idle=10
spring.datasource.one.hikari.pool-name=HikaraPool-1

## Spring HikaraDataSource Configuration
# spring.datasource.two.driver-class-name=com.mysql.cj.jdbc.Driver
# spring.datasource.two.name=HikariCP-2
spring.datasource.two.url=jdbc:mysql://localhost:3306/kaddo-uat?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull
spring.datasource.two.username=root
spring.datasource.two.password=123456
spring.datasource.two.hikari.connection-timeout=30000
spring.datasource.two.hikari.idle-timeout=600000
spring.datasource.two.hikari.max-lifetime=1800000
spring.datasource.two.hikari.maximum-pool-size=100
spring.datasource.two.hikari.minimum-idle=10
spring.datasource.two.hikari.pool-name=HikaraPool-2
```

2）创建多数据源：

```
@Bean
@Primary
@ConfigurationProperties("spring.datasource.one")
public DataSourceProperties dataSourcePropertiesOne() {
	return new DataSourceProperties();
}

@Bean
@Primary
@ConfigurationProperties("spring.datasource.one.hikari")
public HikariDataSource dataSourceOne(DataSourceProperties properties) {
	return KaddoDataSourceBuilder.createHikariDataSource(properties);
}

@Bean
@ConfigurationProperties("spring.datasource.two")
public DataSourceProperties dataSourcePropertiesTwo() {
	return new DataSourceProperties();
}

@Bean
@ConfigurationProperties("spring.datasource.two.hikari")
public HikariDataSource dataSourceTwo(@Qualifier("dataSourcePropertiesTwo") DataSourceProperties properties) {
	return KaddoDataSourceBuilder.createHikariDataSource(properties);
}
```

3）使用多数据源：

```
@Autowired
private HikariDataSource dataSourceOne;

@Autowired
@Qualifier("dataSourceTwo")
private HikariDataSource dataSourceTwo;
```

配置多数据源的注意事项：

* 配置多数据源时，最好通过 @Primary 指定默认数据源。
* 创建数据源对象时，建议使用 HikariDataSource 代替 DataSource。
* 创建数据源对象时，注意通过 @Qualifier("dataSourcePropertiesTwo") 来指定数据源的配置属性对象。
* 创建数据源对象时，注意创建 Bean 的方法名，最好通过 @Bean("dataSourceOne") 来指定数据源对象的名称。
* 多数据源通常结合 ORM 框架一起使用，具体可参考 Kaddo 框架的 ORM 配置。
