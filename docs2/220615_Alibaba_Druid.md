# Druid Spring Boot Starter

Alibaba Druid 是一个 JDBC 组件库，包含数据库连接池、SQL Parser 等组件，被大量业务和技术产品使用或集成，经历过严苛的线上业务场景考验，是值得信赖的技术产品。Druid
Spring Boot Starter 用于帮助你在 Spring Boot 项目中轻松集成 Druid 数据库连接池和监控。

https://github.com/alibaba/druid

## 快速使用

1）在你的 Spring Boot 项目中添加依赖配置（引入 Kaddo 框架不需要指定版本号）：

```
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>druid-spring-boot-starter</artifactId>
   <version>${alibaba-druid.version}</version>
</dependency>
```

2）添加数据源配置：

```
## Spring DruidDataSource Configuration
# spring.datasource.druid.enable=true
# spring.datasource.druid.name=DataSource-1
# spring.datasource.druid.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.druid.url=jdbc:mysql://localhost:3306/dbname?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull
spring.datasource.druid.username=root
spring.datasource.druid.password=123456
spring.datasource.druid.initial-size=10
spring.datasource.druid.min-idle=10
spring.datasource.druid.max-active=100
spring.datasource.druid.max-wait=6000
spring.datasource.druid.test-while-idle=true
spring.datasource.druid.test-on-borrow=false
spring.datasource.druid.test-on-return=false
spring.datasource.druid.validation-query=select 1
spring.datasource.druid.time-between-eviction-runs-millis=30000
spring.datasource.druid.min-evictable-idle-time-millis=600000
spring.datasource.druid.max-evictable-idle-time-millis=900000
spring.datasource.druid.keep-alive=true
```

3）Druid 支持根据 url 来自动探测 JDBC 驱动，已在 `druid-xxx.jar/META-INF/druid-driver.properties` 属性文件中定义。

```
jdbc:derby:=org.apache.derby.jdbc.EmbeddedDriver	 
jdbc:mysql:=com.mysql.jdbc.Driver
jdbc:log4jdbc:=net.sf.log4jdbc.DriverSpy
jdbc:oracle:=oracle.jdbc.driver.OracleDriver
jdbc:microsoft:=com.microsoft.jdbc.sqlserver.SQLServerDriver	 
jdbc:jtds:=net.sourceforge.jtds.jdbc.Driver	 
jdbc:postgresql:=org.postgresql.Driver	 
jdbc:fake:=com.alibaba.druid.mock.MockDriver	 
jdbc:hsqldb:=org.hsqldb.jdbcDriver	 
jdbc:db2:=COM.ibm.db2.jdbc.app.DB2Driver
jdbc:sqlite:=org.sqlite.JDBC	 
jdbc:ingres:=com.ingres.jdbc.IngresDriver	 
jdbc:h2:=org.h2.Driver	 
jdbc:mckoi:=com.mckoi.JDBCDriver
jdbc:clickhouse:=ru.yandex.clickhouse.ClickHouseDriver
jdbc:highgo:=com.highgo.jdbc.Driver
```

如果未能自动探测 JDBC 驱动，则需指定驱动类：

```
spring.datasource.druid.driver-class-name=com.mysql.cj.jdbc.Driver
```

## 连接池配置

Druid Spring Boot Starter 配置属性的名称完全遵照 Druid，你可以通过 Spring Boot 配置文件来配置 Druid 数据库连接池和监控。

- JDBC 配置：

```
# 单数据源时，Druid 兼容 JDBC 的配置如下：
spring.datasource.druid.url= # 或spring.datasource.url= 
spring.datasource.druid.username= # 或spring.datasource.username=
spring.datasource.druid.password= # 或spring.datasource.password=
spring.datasource.druid.driver-class-name= #或 spring.datasource.driver-class-name=
```

- 连接池配置详解：

```
# 启动 Druid 连接池，默认开启
# 多数据源配置时，该参数不生效
spring.datasource.druid.enable=true

# 指定驱动类名，默认从 URL 中自动探测
spring.datasource.druid.driver-class-name=com.mysql.cj.jdbc.Driver

# 设置数据库连接
spring.datasource.druid.url=jdbc:mysql://localhost:3306/dbname

# 设置数据库用户名
spring.datasource.druid.username=root

# 设置数据库密码
spring.datasource.druid.password=123456

# 允许访问底层连接，默认值：true
spring.datasource.druid.access-to-underlying-connection-allowed=true

# 启用异步关闭连接，默认值：false
# 如果 removeAbandoned = true，则自动开启。
spring.datasource.druid.async-close-connection-enable=false

# 开启异步初始化，默认值：false
spring.datasource.druid.async-init=false

# 失败后跳过即用于失败重连，默认值：false
# true 表示向数据库请求连接失败后，就算后端数据库恢复正常也不进行重连，因此一定要配置 false
spring.datasource.druid.break-after-acquire-failure=false

# 检查SQL执行时间，默认值：false
spring.datasource.druid.check-execute-time=false

# 启动清除过滤器，默认值：true
spring.datasource.druid.clear-filters-enable=true

# 连接属性配置，多个用英文分号隔开
spring.datasource.druid.connect-properties=

# 连接出错尝试几次重新连接，默认值：1
spring.datasource.druid.connection-error-retry-attempts=1

# 指定数据库类型，默认自动探测
spring.datasource.druid.db-type=

# 事务是否自动提交，默认值：true
spring.datasource.druid.default-auto-commit=true

# 指定连接默认的 catalog，默认未设置
spring.datasource.druid.default-catalog=

# 是否设置默认连接只读，默认未设置
spring.datasource.druid.default-read-only=true

# 指定连接的事务的默认隔离级别，默认未设置。
# -1 数据库默认隔离级别
# 1 未提交读
# 2 读写提交
# 4 可重复读
# 8 串行化
spring.datasource.druid.default-transaction-isolation=

# 当创建连接池时，创建失败后是否立即抛异常，默认值：false
spring.datasource.druid.fail-fast=false

# 设置过滤器别名，多个使用英文逗号隔开，默认值：default
# 生产环境不建议使用其他过滤器。
spring.datasource.druid.filters=stat

# 初始化异常则抛出异常，默认值：true
spring.datasource.druid.init-exception-throw=true

# 初始化全局变量，默认值：false
spring.datasource.druid.init-global-variants=false

# 初始化变量，默认值：false
spring.datasource.druid.init-variants=false

# 初始化连接池大小，默认值：0
# 建议与 minIdle 大小保持一致
spring.datasource.druid.initial-size=10

# 开启 keepAlive 操作，默认值：false
# 打开 keepAlive 之后的效果
# 1、初始化连接池时会填充到 minIdle 数量。
# 2、连接池中的 minIdle 数量以内的连接，空闲时间超过 minEvictableIdleTimeMillis，则会执行 keepAlive 操作。
# 3、当网络断开等原因产生的由 ExceptionSorter 检测出来的死连接被清除后，自动补充连接到 minIdle 数量。
spring.datasource.druid.keep-alive=true

# 两次 keepAlive 操作的时间间隔，默认值：120000（单位毫秒）
spring.datasource.druid.keep-alive-between-time-millis=120000

# 指定连接数据库的超时时间，默认无限制（单位秒）
spring.datasource.druid.login-timeout=

# 设置最大连接数，默认值：8
spring.datasource.druid.max-active=100

# 最大创建任务数，默认值：3
spring.datasource.druid.max-create-task-count=3

# 连接保持空闲而不被驱逐的最大时间，默认值：1800000毫秒=30分钟
spring.datasource.druid.max-evictable-idle-time-millis=1800000

# 最大打开的 prepared-statement 数量，默认值：-1（无限制）
spring.datasource.druid.max-open-prepared-statements=-1

# 设置最大等待时间，默认值：-1（单位毫秒）
spring.datasource.druid.max-wait=6000

# 允许的最大线程等待数，默认值：-1（无限制）
spring.datasource.druid.max-wait-thread-count=-1

# 销毁线程时检测当前连接的最后活动时间和当前时间差大于该值时，关闭当前连接即一个连接在池中最小生存的时间。默认值：1800000毫秒=30分钟
spring.datasource.druid.min-evictable-idle-time-millis=1800000

# 设置最小连接数，默认值：0
spring.datasource.druid.min-idle=10

# 指定连接池名称，未设置则随机生成：`"DataSource-" + System.identityHashCode(this);`
spring.datasource.druid.name=DataSource-1

# 设置获取连接时的重试次数，默认值：0
spring.datasource.druid.not-full-timeout-retry-count=0

# 用于控制当 OnFatalError 发生时最大使用连接数量，用于控制异常发生时并发执行SQL的数量，减轻数据库恢复的压力。默认值：0
spring.datasource.druid.on-fatal-error-max-active=0

# 是否是 Oracle 数据库，默认值：false
spring.datasource.druid.oracle=false

# 物理最大连接数，默认值：-1（无限制）
spring.datasource.druid.phy-max-use-count=-1

# 物理超时时间，默认值：-1（无限制，单位毫秒）
spring.datasource.druid.phy-timeout-millis=-1

# oracle 设为 true，mysql 设为 false。分库分表较多推荐设置为 false，默认值：false
spring.datasource.druid.pool-prepared-statements=false

# 打开PSCache，并且指定每个连接上PSCache的大小，默认值：10
# poolPreparedStatements 默认为 false，属性文件中将 poolPreparedStatements 设置为 true，则该值生效。
# 若属性文件中设置该值且大于0时，poolPreparedStatements 会自动设置为 true。
spring.datasource.druid.max-pool-prepared-statement-per-connection-size=10

# 查询超时时间，默认值：0（无限制，单位秒）
spring.datasource.druid.query-timeout=0

# 指定当连接超过废弃超时时间时，是否立刻删除该连接，默认值：false
spring.datasource.druid.remove-abandoned=false

# 废弃连接超时指定时间的连接，默认值：5分钟
spring.datasource.druid.remove-abandoned-timeout-millis=300 * 1000

# 共享预处理语句，默认值：false
spring.datasource.druid.share-prepared-statements=false

# 从连接池借用连接时，是否测试该连接，默认值：false
spring.datasource.druid.test-on-borrow=false

# 申请连接时检测，如果空闲时间大于 timeBetweenEvictionRunsMillis，执行 validationQuery 检测连接是否有效。默认值：false
spring.datasource.druid.test-on-return=false

# 归还连接时会执行 validationQuery 检测连接是否有效，默认值：true
spring.datasource.druid.test-while-idle=true

# 指定两次错误连接的最大时间间隔，默认值：500毫秒
spring.datasource.druid.time-between-connect-error-millis=500

# 既作为检测的间隔时间又作为 testWhileIdel 执行的依据即此值决定是否空闲，因此此值一定要设置合理。
# 即一个空闲线程，最大的生成时间，检测需要关闭的空闲连接。默认值：60000毫秒
spring.datasource.druid.time-between-eviction-runs-millis=30000

# 事务查询超时时间，默认值：0（小于或等于 0 时取 query-timeout 的值）
spring.datasource.druid.transaction-query-timeout=0

# 事务时间阈值，默认值：0（单位毫秒）
spring.datasource.druid.transaction-threshold-millis=0

# 使用非公平锁，默认未设置
spring.datasource.druid.use-unfair-lock=true

# 使用 Oracle 隐式缓存，默认值：true
spring.datasource.druid.use-oracle-implicit-cache=true

# 指定连接的有效检查类，默认未设置
spring.datasource.druid.valid-connection-checker=

# 用来检测连接是否有效的 SQL 必须是一个查询语句，默认未设
spring.datasource.druid.validation-query=select 1

# 检测连接是否有效的超时时间，默认值：-1（单位秒）
spring.datasource.druid.validation-query-timeout=-1
```

## 过滤器配置

Druid 内置的过滤器类及别名，已在 `druid-xxx.jar/META-INF/druid-filter.properties` 属性文件中定义：

```
druid.filters.default=com.alibaba.druid.filter.stat.StatFilter
druid.filters.stat=com.alibaba.druid.filter.stat.StatFilter
druid.filters.mergeStat=com.alibaba.druid.filter.stat.MergeStatFilter
druid.filters.counter=com.alibaba.druid.filter.stat.StatFilter
druid.filters.encoding=com.alibaba.druid.filter.encoding.EncodingConvertFilter
druid.filters.log4j=com.alibaba.druid.filter.logging.Log4jFilter
druid.filters.log4j2=com.alibaba.druid.filter.logging.Log4j2Filter
druid.filters.slf4j=com.alibaba.druid.filter.logging.Slf4jLogFilter
druid.filters.commonlogging=com.alibaba.druid.filter.logging.CommonsLogFilter
druid.filters.commonLogging=com.alibaba.druid.filter.logging.CommonsLogFilter
druid.filters.wall=com.alibaba.druid.wall.WallFilter
druid.filters.config=com.alibaba.druid.filter.config.ConfigFilter
druid.filters.haRandomValidator=com.alibaba.druid.pool.ha.selector.RandomDataSourceValidateFilter
```

除 StatFilter 作为默认过滤器外，其他都为关闭状态。配置过滤器的方法如下：

```
# 设置过滤器别名，多个使用英文逗号隔开，默认值：default
# 生产环境不建议使用其他过滤器。
spring.datasource.druid.filters=stat
```

### StatViewServlet

Druid 管控台配置，考虑到安全问题默认为关闭，如需开启，建议设置密码或白名单以保障安全。配置开启后，才能打开管控台页面查看监控信息。

浏览器访问：http://127.0.0.1:8080/druid/index.html

```
## DruidStatViewServletConfiguration
# 是否开启管控台页面访问，默认值：false
spring.datasource.druid.stat-view-servlet.enabled=true
# URL 映射路径，默认值：/druid/*
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
# 是否允许重置，默认允许
# 注意：设置为 false 后，管控台的重置按钮并不会消失，但数据不会重置
spring.datasource.druid.stat-view-servlet.reset-enable=false
# 管控台登录用户名，默认未设置
spring.datasource.druid.stat-view-servlet.login-username=admin
# 管控台登录密码，默认未设置
spring.datasource.druid.stat-view-servlet.login-password=admin123
# 管控台访问白名单，默认值：127.0.0.1
spring.datasource.druid.stat-view-servlet.allow=127.0.0.1,10.177.37.15
# 管控台访问黑名单，默认未设置
spring.datasource.druid.stat-view-servlet.deny=10.177.37.14
```

### WebStatFilter

开启 Web 请求监控，默认为关闭。配置开启后，可以在 Druid 管控台查看 Web 请求的监控信息。

```
## DruidWebStatFilterConfiguration
# 是否开启 Web 请求的统计，默认值：false
spring.datasource.druid.web-stat-filter.enabled=true
# 以下参数保持默认配置即可，生产环境不建议启用
# spring.datasource.druid.web-stat-filter.url-pattern=
# spring.datasource.druid.web-stat-filter.exclusions=
# spring.datasource.druid.web-stat-filter.session-stat-enable=
# spring.datasource.druid.web-stat-filter.session-stat-max-count=
# spring.datasource.druid.web-stat-filter.principal-session-name=
# spring.datasource.druid.web-stat-filter.principal-cookie-name=
# spring.datasource.druid.web-stat-filter.profile-enable=
```

### StatFilter

用于统计监控信息，默认开启。

```
## StatFilter
# 是否开启统计，默认值：true
spring.datasource.druid.filter.stat.enabled=true
# 启动连接堆跟踪，默认值：false
spring.datasource.druid.filter.stat.connection-stack-trace-enable=false
# 是否在日志中打印慢SQL，默认值：false
spring.datasource.druid.filter.stat.log-slow-sql=true
# 设置慢SQL的执行时长，默认值：3000毫秒
spring.datasource.druid.filter.stat.slow-sql-millis=3000
# 设置慢SQL的日志级别，默认值：ERROR
spring.datasource.druid.filter.stat.slow-sql-log-level=ERROR
# 是否合并SQL，默认值：false
spring.datasource.druid.filter.stat.merge-sql=false
```

### WallFilter

这是一个特殊的过滤器，用于监控 SQL 安全，避免 SQL 注入，默认为关闭。在 Spring 中配置启用 WallFilter 时，需要先注入 StatFilter 开启监控、WallFilter
配置各种信息、WallConfig 配置拦截 SQL 的规则。

WallConfig 配置参数较多，通常情况下保持默认配置即可。WallConfig 配置详情文档：https://www.cnblogs.com/soundcode/p/6483899.html

```
## WallFilter
# 是否开启 SQL 监控，默认值：false
spring.datasource.druid.filter.wall.enabled=true
```

### LogFilter

Druid 内置提供了四种 LogFilter（Log4jFilter、Log4j2Filter、CommonsLogFilter、Slf4jLogFilter），用于输出 JDBC 执行的日志。这些
Filter 都是 Filter-Chain 扩展机制中的
Filter，所以配置方式可以参考这里：[Druid 中使用 log4j2 进行日志输出](https://github.com/alibaba/druid/wiki/Druid%E4%B8%AD%E4%BD%BF%E7%94%A8log4j2%E8%BF%9B%E8%A1%8C%E6%97%A5%E5%BF%97%E8%BE%93%E5%87%BA)

尽管 Druid 支持日志过滤器，但在生产环境几乎是不可能开启日志配置的。所以，这里只作了解，实际应用比较复杂。

```
# 是否开启日志，默认值：false
spring.datasource.druid.filter.slf4j.enabled=false
spring.datasource.druid.filter.log4j.enabled=false
spring.datasource.druid.filter.log4j2.enabled=false
spring.datasource.druid.filter.commons-log.enabled=false
```

### EncodingConvertFilter

由于历史原因，一些数据库保存数据的时候使用了错误编码，需要做编码转换。通过 connectionProperties 来配置客户端编码和服务器端编码。

```
# 是否开启字符编码转换，默认值：false
spring.datasource.druid.filter.encoding.enabled=true
# 指定客户端和服务端的字符编码
spring.datasource.druid.connection-properties=clientEncoding=UTF-8;serverEncoding=ISO-8859-1
```

### ConfigFilter

ConfigFilter的作用包括：

1. 从配置文件中读取配置
2. 从远程http文件中读取配置
3. 为数据库密码提供加密功能

详情查看官方文档：[使用 ConfigFilter](https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter)

```
# 是否开启配置，默认值：false
spring.datasource.druid.filter.config.enabled=true
# 密码加密处理后，需要开启解密
spring.datasource.druid.connection-properties=config.decrypt=true
```

## 多数据源配置

Spring Boot 2.X 版本不再支持配置继承，多数据源的话每个数据源的所有配置都需要单独配置，否则配置不会生效。

1）添加多数据配置：

```
## Spring DruidDataSource Configuration
# spring.datasource.druid.one.name=DataSource-1
# spring.datasource.druid.one.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.druid.one.url=jdbc:mysql://localhost:3306/dbname1?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull
spring.datasource.druid.one.username=root
spring.datasource.druid.one.password=123456
spring.datasource.druid.one.initial-size=10
spring.datasource.druid.one.min-idle=10
spring.datasource.druid.one.max-active=100
spring.datasource.druid.one.max-wait=6000
spring.datasource.druid.one.test-while-idle=true
spring.datasource.druid.one.test-on-borrow=false
spring.datasource.druid.one.test-on-return=false
spring.datasource.druid.one.validation-query=select 1
spring.datasource.druid.one.time-between-eviction-runs-millis=30000
spring.datasource.druid.one.min-evictable-idle-time-millis=600000
spring.datasource.druid.one.max-evictable-idle-time-millis=900000
spring.datasource.druid.one.keep-alive=true


## Spring DruidDataSource Configuration
# spring.datasource.druid.two.name=DataSource-1
# spring.datasource.druid.two.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.druid.two.url=jdbc:mysql://localhost:3306/dbname2?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&noAccessToProcedureBodies=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull
spring.datasource.druid.two.username=root
spring.datasource.druid.two.password=123456
spring.datasource.druid.two.initial-size=10
spring.datasource.druid.two.min-idle=10
spring.datasource.druid.two.max-active=100
spring.datasource.druid.two.max-wait=6000
spring.datasource.druid.two.test-while-idle=true
spring.datasource.druid.two.test-on-borrow=false
spring.datasource.druid.two.test-on-return=false
spring.datasource.druid.two.validation-query=select 1
spring.datasource.druid.two.time-between-eviction-runs-millis=30000
spring.datasource.druid.two.min-evictable-idle-time-millis=600000
spring.datasource.druid.two.max-evictable-idle-time-millis=900000
spring.datasource.druid.two.keep-alive=true
```

2）创建多数据源：

```
@Primary
@Bean
@ConfigurationProperties("spring.datasource.druid.one")
public DruidDataSource dataSourceOne() {
	return DruidDataSourceBuilder.create().build();
}

@Bean
@ConfigurationProperties("spring.datasource.druid.two")
public DruidDataSource dataSourceTwo() {
	return DruidDataSourceBuilder.create().build();
}
```

3）使用多数据源：

```
@Autowired
private DruidDataSource dataSourceOne;

@Autowired
@Qualifier("dataSourceTwo")
private DruidDataSource dataSourceTwo;
```

配置多数据源的注意事项：

- 配置多数据源时，最好通过 @Primary 指定默认数据源。
- Druid 创建数据源对象时，建议使用 DruidDataSource 代替 DataSource。
- Druid 创建数据源对象时，注意创建 Bean 的方法名，最好通过 @Bean("dataSourceOne") 来指定数据源对象的名称。
- 多数据源通常结合 ORM 框架一起使用，具体可参考 Kaddo 框架的 ORM 配置。
