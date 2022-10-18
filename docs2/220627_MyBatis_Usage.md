---
tags:

- HTML5
- JavaScript
- CSS
- Other

---

# MyBatis Spring Boot Starter

MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的
XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

https://mybatis.org/mybatis-3/zh/index.html

[MyBatis-Spring-Boot-Starter](https://github.com/mybatis/spring-boot-starter/blob/master/mybatis-spring-boot-autoconfigure/src/site/zh/markdown/index.md)
可以帮助你更快地在 [Spring Boot](https://spring.io/projects/spring-boot) 之上构建 MyBatis 应用。

[MyBatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) 是 MyBatis 最方便的分页插件。

## 快速开始

1）在你的 Spring Boot 项目中添加 Maven 依赖：

```
<dependency> 
    <groupId>com.tsollu</groupId>
    <artifactId>tsollu-starter-mybatis</artifactId> 
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

4）在 `application.properties` 配置文件中添加 MyBatis 配置（配置参数与 MyBatis-Plus 基本一样，MyBatis
官方配置文档：[https://mybatis.org/mybatis-3/zh/configuration.html](https://mybatis.org/mybatis-3/zh/configuration.html)
）：

```
## MyBatis Configuration
mybatis.type-aliases-package=com.mybatis.samples.quickstart.model
mybatis.mapper-locations=classpath*:/mapper/**/*.xml
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.configuration.default-executor-type=reuse
mybatis.configuration.default-fetch-size=100
mybatis.configuration.default-statement-timeout=30
mybatis.configuration.cache-enabled=true
```

5）在 Spring Boot 启动类中添加 `@MapperScan` 注解，扫描 Mapper 文件夹：

```
@SpringBootApplication
@MapperScan("com.mybatis.samples.quickstart.mapper")
public class SampleMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleMybatisApplication.class, args);
    }

}
```

6）编写数据层对象，假设我们有下面的 mapper ：

```
@Mapper
public interface CityMapper {

  @Select("SELECT * FROM CITY WHERE state = #{state}")
  City findByState(@Param("state") String state);

}
```

7）你只需要创建一个 Spring boot 应用，像下面这样，将 mapper 注入进去（ Spring 4.3 以上可用）。

```
@SpringBootApplication
//@MapperScan("com.mybatis.samples.quickstart.mapper")
public class SampleMybatisApplication implements CommandLineRunner {

  private final CityMapper cityMapper;

  public SampleMybatisApplication(CityMapper cityMapper) {
    this.cityMapper = cityMapper;
  }

  public static void main(String[] args) {
    SpringApplication.run(SampleMybatisApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println(this.cityMapper.findByState("CA"));
  }

}
```

## 如何使用分页插件

推荐以下写法，更多查看官方文档：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md

```
// Mapper接口方式的调用，推荐这种使用方式。
PageHelper.startPage(1, 10);
List<User> list = userMapper.selectIf(1);

// ISelect 接口方式
// jdk6,7用法，创建接口
Page<User> page = PageHelper.startPage(1, 10).doSelectPage(new ISelect() {
    @Override
    public void doSelect() {
        userMapper.selectGroupBy();
    }
});

// jdk8 lambda用法
Page<User> page = PageHelper.startPage(1, 10).doSelectPage(()-> userMapper.selectGroupBy());

// 也可以直接返回 PageInfo，注意 doSelectPageInfo 方法和 doSelectPage
pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(new ISelect() {
    @Override
    public void doSelect() {
        userMapper.selectGroupBy();
    }
});

// 对应的lambda用法
pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(() -> userMapper.selectGroupBy());

// count查询，返回一个查询语句的count数
long total = PageHelper.count(new ISelect() {
    @Override
    public void doSelect() {
        userMapper.selectLike(user);
    }
});

// lambda
total = PageHelper.count(()->userMapper.selectLike(user));
```

## 进阶篇 - 配置

https://mybatis.org/mybatis-3/zh/configuration.html

MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。 配置文档的顶层结构如下：

- configuration（配置）
- properties（属性）
- settings（设置）
- typeAliases（类型别名）
- typeHandlers（类型处理器）
- objectFactory（对象工厂）
- plugins（插件）
- environments（环境配置）
  - environment（环境变量）
  - transactionManager（事务管理器）
  - dataSource（数据源）
- databaseIdProvider（数据库厂商标识）
- mappers（映射器）

> 提示：在 Spring Boot 项目中集成 MyBatis-Spring-Boot-Starter 后，不再依赖 mybatis-config.xml
> 配置文件，其中的大部分配置都可以省略，但有必要了解。

## 进阶篇 - XML映射文件

https://mybatis.org/mybatis-3/zh/sqlmap-xml.html

MyBatis 的真正强大在于它的语句映射，这是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95%
的代码。MyBatis 致力于减少使用成本，让用户能更专注于 SQL 代码。

SQL 映射文件只有很少的几个顶级元素（按照应被定义的顺序列出）：

- resultMap – 描述如何从数据库结果集中加载对象，是最复杂也是最强大的元素。
- sql – 可被其它语句引用的可重用语句块。
- insert – 映射插入语句。
- update – 映射更新语句。
- delete – 映射删除语句。
- select – 映射查询语句。

**高级结果集映射：**

```
<!-- 非常复杂的结果映射 -->
<resultMap id="detailedBlogResultMap" type="Blog">
  <constructor>
    <idArg column="blog_id" javaType="int"/>
  </constructor>
  <result property="title" column="blog_title"/>
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
    <result property="favouriteSection" column="author_favourite_section"/>
  </association>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <association property="author" javaType="Author"/>
    <collection property="comments" ofType="Comment">
      <id property="id" column="comment_id"/>
    </collection>
    <collection property="tags" ofType="Tag" >
      <id property="id" column="tag_id"/>
    </collection>
    <discriminator javaType="int" column="draft">
      <case value="1" resultType="DraftPost"/>
    </discriminator>
  </collection>
</resultMap>
```

**1）关联**

```
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
  </association>
</resultMap>
```

**2）集合**

```
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <result property="body" column="post_body"/>
  </collection>
</resultMap>
```

**3）鉴别器**

```
<resultMap id="vehicleResult" type="Vehicle">
  <id property="id" column="id" />
  <result property="vin" column="vin"/>
  <result property="year" column="year"/>
  <result property="make" column="make"/>
  <result property="model" column="model"/>
  <result property="color" column="color"/>
  <discriminator javaType="int" column="vehicle_type">
    <case value="1" resultMap="carResult"/>
    <case value="2" resultMap="truckResult"/>
    <case value="3" resultMap="vanResult"/>
    <case value="4" resultMap="suvResult"/>
  </discriminator>
</resultMap>
```

## 进阶篇 - 动态SQL

https://mybatis.org/mybatis-3/zh/dynamic-sql.html

动态 SQL 是 MyBatis 的强大特性之一。如果你使用过 JDBC 或其它类似的框架，你应该能理解根据不同条件拼接 SQL
语句有多痛苦，例如拼接时要确保不能忘记添加必要的空格，还要注意去掉列表最后一个列名的逗号。利用动态 SQL，可以彻底摆脱这种痛苦。

使用动态 SQL 并非一件易事，但借助可用于任何 SQL 映射语句中的强大的动态 SQL 语言，MyBatis 显著地提升了这一特性的易用性。

如果你之前用过 JSTL 或任何基于类 XML 语言的文本处理器，你对动态 SQL 元素可能会感觉似曾相识。在 MyBatis 之前的版本中，需要花时间了解大量的元素。借助功能强大的基于 OGNL
的表达式，MyBatis 3 替换了之前的大部分元素，大大精简了元素种类，现在要学习的元素种类比原来的一半还要少。

* if
* choose (when, otherwise)
* trim (where, set)
* foreach

1）if - 使用动态 SQL 最常见情景是根据条件包含 where 子句的一部分。

```
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="author != null and author.name != null">
    AND author_name like #{author.name}
  </if>
</select>
```

2）choose (when, otherwise)

```
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```

3）trim (where, set)

```
<selectid="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG
  <where>
    <iftest="state != null">
         state = #{state}
    </if>
    <iftest="title != null">
        AND title like #{title}
    </if>
    <iftest="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```

*where* 元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。而且，若子句的开头为 “AND” 或 “OR”，*where* 元素也会将它们去除。

如果 *where* 元素与你期望的不太一样，你也可以通过自定义 trim 元素来定制 *where* 元素的功能。比如，和 *where* 元素等价的自定义 trim 元素为：

```
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ...
</trim>
```

4）foreach - 动态 SQL 的另一个常见使用场景是对集合进行遍历（尤其是在构建 IN 条件语句的时候）。

```
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  <where>
    <foreach item="item" index="index" collection="list"
        open="ID in (" separator="," close=")" nullable="true">
          #{item}
    </foreach>
  </where>
</select>
```

## 进阶篇 - JavaAPI

https://mybatis.org/mybatis-3/zh/java-api.html

- SqlSession - 使用 MyBatis 的主要 Java 接口就是 SqlSession。你可以通过这个接口来执行命令，获取映射器实例和管理事务。
- SqlSessionFactory - SqlSessionFactory 有六个方法创建 SqlSession 实例。

### 注解

MyBatis 提供了和 XML 配置等同的注解，个人觉得还是 XML 配置更加直观。（不推荐）

- `@CacheNamespace`
- `@Property`
- `@CacheNamespaceRef`
- `@ConstructorArgs`
- `@Arg`
- `@TypeDiscriminator`
- `@Case`
- `@Results`
- `@Result`
- `@One`
- `@Many`
- `@MapKey`
- `@Options`

* `@Insert`
* `@Update`
* `@Delete`
* `@Select`
* `@Param`
* `@SelectKey`
* `@ResultMap`
* `@ResultType`
* `@Flush`

这个例子展示了如何使用 @SelectKey 注解来在插入前读取数据库序列的值：

```
@Insert("insert into table3 (id, name) values(#{nameId}, #{name})")
@SelectKey(statement="call next value for TestSequence", keyProperty="nameId", before=true, resultType=int.class)
int insertTable3(Name name);
```
