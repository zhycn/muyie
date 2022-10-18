# MySQL URL 连接参数设置

MySQL URL 连接可以携带参数，通用配置模板如下：

```
# 通过URL设置连接参数
spring.datasource.url=jdbc:mysql://localhost:3306/dbname?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&failOverReadOnly=false&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull
```

## 参数说明

以下是部分比较常用的参数。

### serverTimezone

设置时区。

MySQL高版本中如果不设置可能会有问题。UTC表示全球标准时间，国内建议使用中国标准时间：

```
# 上海时间
serverTimezone=Asia/Shanghai

# 北京时间东八区
serverTimezone=GMT%2B8
```

### characterEncoding

设置字符集的编码类型。

```
characterEncoding=utf-8
```

### useUnicode

是否使用编码集。

```
useUnicode=true
```

### autoReconnect

当数据库连接中断时，是否自动重新连接？缺省为 false。

```
autoReconnect=true
```

关联的两个参数：

- maxReconnects：当 autoReconnect 设置为 true 时，重试连接的次数，缺省为 3 次。
- initialTimeout：当 autoReconnect 设置为 true 时，两次重连之间的时间间隔，缺省为 2 秒。

### autoReconnectForPools

是否使用针对数据库连接池的重连策略。缺省为 false。

```
autoReconnectForPools=true
```

### failOverReadOnly

自动重连成功后，连接是否设置为只读？缺省为 true。

当 autoReconnect 设置为 true 时，建议将此参数设置为 false。

```
failOverReadOnly=false
```

### noAccessToProcedureBodies

JDBC 调用存储过程时需要有 show create procudure 权限或是有表 mysql.proc 的 select 的权限，可以添加该参数。缺省为 false。

```
noAccessToProcedureBodies=true
```

这样做会存在一些影响：

- 调用存储过程时，将没有类型检查，设为字符串类型，并且所有的参数设为 in 类型，但是在调用 registerOutParameter 时，不抛出异常。
- 存储过程的查询结果无法使用 getXXX(String parameterName) 的形式获取，只能通过 getXXX(int parameterIndex) 的方式获取。

更好的方式是：给 MySQL 普通用户授予相应的权限。

```
grant select on mysql.proc to 'user'@%;
```

### useSSL

MySQL高版本支持是否使用 useSSL，缺省为 true。

```
useSSL=true
```

### allowMultiQueries

缺省为 false。

1. 可以在 SQL 语句后携带分号，实现多语句执行。
2. 可以执行批处理，同时发出多个 SQL 语句。

```
allowMultiQueries=true
```

### zeroDateTimeBehavior

datetime 字段的值全部为 0 时的处理方式。

```
zeroDateTimeBehavior=convertToNull
```
