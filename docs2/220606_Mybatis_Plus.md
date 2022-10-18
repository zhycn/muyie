# MyBatis-Plus

[MyBatis-Plus](https://baomidou.com/) 是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

tsollu-starter-mybatis-plus 模块维护了一套较为常用的 MyBatis-Plus 配置，支持在项目开发中快速构建和使用。

## 快速开始

1）在你的 Spring Boot 项目中添加 Maven 依赖：

```
<dependency> 
    <groupId>com.tsollu</groupId>
    <artifactId>tsollu-starter-mybatis-plus</artifactId> 
    <version>${tsollu.version}</version>
</dependency>
```

2）然后，根据数据库选择驱动，以 H2 数据库为例：

```
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

3）在 `application.properties` 配置文件中添加 H2 数据库的相关配置：

```
# DataSource Configuration
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.schema=classpath:db/schema-h2.sql
spring.datasource.data=classpath:db/data-h2.sql
spring.datasource.url=jdbc:h2:mem:test
spring.datasource.username=root
spring.datasource.password=test
```

4）在 `application.properties` 配置文件中添加 MyBatis-Plus 配置：

```
## MyBatis-Plus Configuration
mybatis-plus.type-aliases-package=com.baomidou.mybatisplus.samples.quickstart.model
mybatis-plus.mapper-locations=classpath*:/mapper/**/*.xml
mybatis-plus.configuration.map-underscore-to-camel-case=true
mybatis-plus.configuration.default-executor-type=reuse
mybatis-plus.configuration.default-fetch-size=100
mybatis-plus.configuration.default-statement-timeout=30
mybatis-plus.configuration.cache-enabled=true
```

5）在 Spring Boot 启动类中添加 `@MapperScan` 注解，扫描 Mapper 文件夹：

```
@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

6）编写实体类和数据层对象：

```
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

编写 Mapper 包下的 `UserMapper` 接口：

```
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
```

7）开始使用：

```
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
```

## 配置详解

MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。官方配置文档：https://mybatis.org/mybatis-3/zh/configuration.html

```
## MyBatis-Plus Configuration
# 指定 MyBatis XML 配置文件的位置，默认未设置。
# 注意：Spring Boot 项目不支持 config-location 和 configuration.* 同时出现，因此不推荐使用该属性。
mybatis-plus.config-location=classpath*:mybatis-config.xml

# MyBatis Mapper 所对应的 XML 文件位置，告诉 Mapper 所对应的 XML 文件位置。
# 默认值：classpath*:/mapper/**/*.xml，多个用英文逗号分隔。
mybatis-plus.mapper-locations=classpath*:/mapper/**/*.xml

# MyBaits 别名包扫描路径，通过该属性可以给包中的类注册别名，默认未设置。
# 也可以使用注解 @Alias("author") 来指定类的别名。
mybatis-plus.type-aliases-package=com.baomidou.mybatisplus.samples.quickstart.model

# TypeHandler 扫描路径，如果配置了该属性，SqlSessionFactoryBean 会把该包下面的类注册为对应的 TypeHandler。
# 通常用于自定义类型转换，默认未设置。
mybatis-plus.type-handlers-package=

# 启动时是否检查 MyBatis XML 文件的存在，默认不检查。
mybatis-plus.check-config-location=false

# 通过该属性可指定 MyBatis 的执行器，MyBatis 的执行器总共有三种：
# ExecutorType.SIMPLE：该执行器类型不做特殊的事情，为每个语句的执行创建一个新的预处理语句（PreparedStatement）
# ExecutorType.REUSE：该执行器类型会复用预处理语句（PreparedStatement）
# ExecutorType.BATCH：该执行器类型会批量执行所有的更新语句
# 默认值：SIMPLE
mybatis-plus.executor-type=SIMPLE

# 是否开启自动驼峰命名规则（camel case）映射，默认值：true
mybatis-plus.configuration.map-underscore-to-camel-case=true

# 默认枚举处理类，如果配置了该属性，枚举将统一使用指定处理器进行处理。
# 默认为存储枚举类的名称。
mybatis-plus.configuration.default-enum-type-handler=org.apache.ibatis.type.EnumTypeHandler

# MyBatis 自动映射策略，通过该配置可指定 MyBatis 是否并且如何来自动映射数据表字段与对象的属性，总共有 3 种可选值：
# AutoMappingBehavior.NONE：不启用自动映射
# AutoMappingBehavior.PARTIAL：只对非嵌套的 resultMap 进行自动映射
# AutoMappingBehavior.FULL：对所有的 resultMap 都进行自动映射
# 默认值：PARTIAL
mybatis-plus.configuration.auto-mapping-behavior=PARTIAL

# 开启 Mybatis 二级缓存，默认值：true。
mybatis-plus.configuration.cache-enabled=true

# 为驱动的结果集获取数量（fetchSize）设置一个建议值。此参数只可以在查询设置中被覆盖。
mybatis-plus.configuration.default-fetch-size=100

# 设置超时时间，它决定数据库驱动等待数据库响应的秒数。
mybatis-plus.configuration.default-statement-timeout=30
```

## 注解

MyBatis-Plus 提供了一些注解，仅供参考：https://baomidou.com/pages/223848/

- @TableName
- @TableId
- @TableField
- @Version
- @EnumValue
- @TableLogic
- @SqlParser
- @KeySequence
- @InterceptorIgnore
- @OrderBy

## 核心功能

使用 MyBatis-Plus 提供的增强功能时，首先要对 MyBatis-Plus 有一定的了解，否则还是老老实实用 MyBatis 的原生功能。

> MyBatis-Plus 提供的增强功能具有一定的便利性，仍然推荐使用 MyBatis XML 配置的方式，这样 SQL 管理更加清晰。

1）CRUD 接口

- IService<T>: Service CRUD 接口
- BaseMapper<T>: Mapper CRUD 接口
- ActiveRecord 模式，在继承 BaseMapper<T> 接口后，实体类继承 Model<T> 即可进行 CRUD 操作。

2）条件构造器

- AbstractWrapper
- QueryWrapper
- UpdateWrapper

3）主键策略

**主键生成策略必须使用 INPUT**

内置支持：

* DB2KeyGenerator
* H2KeyGenerator
* KingbaseKeyGenerator
* OracleKeyGenerator
* PostgreKeyGenerator

如果内置支持不满足你的需求，可实现 IKeyGenerator 接口来进行扩展.

```
@KeySequence(value = "SEQ_ORACLE_STRING_KEY", clazz = String.class)
public class YourEntity {

    @TableId(value = "ID_STR", type = IdType.INPUT)
    private String idStr;

}
```

## 扩展

非必要不使用。

## 插件

MyBatis-Plus 提供了多个插件，可查看官方文档：https://baomidou.com/pages/2976a3/

tsollu-starter-mybatis-plus 模块提供了一个插件的默认配置：

```
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class KaddoMybatisPlusAutoConfiguration {

    /**
     * 默认的 MyBatis-Plus 插件配置，不满足需求可以在项目中重新配置，查看 MyBatis-Plus 官方文档了解更多详情。
     */
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件（单页分页条数限制5000条 - 默认无限制）
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(5000L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        // 乐观锁插件（在实体类的字段上加上 @Version 注解）
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
        // 防全表更新与删除插件（针对 update 和 delete 语句，阻止恶意的全表更新和删除）
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
        return interceptor;
    }

}
```

### 分页插件的使用

```
IPage<UserVo> selectPageVo(IPage<?> page, Integer state);
// or (class MyPage extends Ipage<UserVo>{ private Integer state; })
MyPage selectPageVo(MyPage page);
// or
List<UserVo> selectPageVo(IPage<UserVo> page, Integer state);
// IPage<Board> page = new Page();
```

```
<select id="selectPageVo" resultType="xxx.xxx.xxx.UserVo">
    SELECT id,name FROM user WHERE state=#{state}
</select>
```

- 如果返回类型是 IPage 则入参的 IPage 不能为 null，因为返回的 IPage 即入参的 IPage。
- 如果返回类型是 List 则入参的 IPage 可以为 null，但需要你手动处理分页 IPage.setRecords(返回的 List);
- 如果 xml 需要从 page 里取值，需要使用 `page.属性` 获取，也可以通过 @Param("page") 注解自定义。
