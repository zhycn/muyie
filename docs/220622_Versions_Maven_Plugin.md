# Versions Maven Plugin

项目版本号管理，尤其是多模块项目的版本号变更。Spring Boot 依赖中已经包含了该插件，无须再次引入。

```
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>versions-maven-plugin</artifactId>
  <version>${versions-maven-plugin.version}</version>
</plugin>
```

几个重要的命令：

```
# 显示所有的依赖更新
mvn versions:display-dependency-updates

# 显示项目的插件更新
mvn versions:display-plugin-updates

# 显示项目的属性更新
mvn versions:display-property-updates

# 设置项目版本号
mvn versions:set -DnewVersion=1.0.1-SNAPSHOT -DgenerateBackupPoms=true

# 设置属性版本号
mvn versions:set-property -Dproperty=tsollu.version -DnewVersion=1.0.1-SNAPSHOT -DgenerateBackupPoms=true

# 移除 pom.xml.versionsBackup 备份文件
mvn versions:commit

# 基于 pom.xml.versionsBackup 备份文件回滚
mvn versions:revert
```

官方文档：

- [Versions Maven Plugin](https://www.mojohaus.org/versions-maven-plugin/index.html)
