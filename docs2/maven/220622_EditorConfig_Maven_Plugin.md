# EditorConfig Maven Plugin

EditorConfig 帮助开发人员在不同的编辑器和 IDE 之间定义和维护一致的编码样式。

[EditorConfig](https://editorconfig.org/) helps maintain consistent coding styles for multiple
developers working on the same project across various editors and IDEs. The EditorConfig project
consists of **a file format** for defining coding styles and a collection of **text editor plugins**
that enable editors to read the file format and adhere to defined styles. EditorConfig files are
easily readable and they work nicely with version control systems.

[editorconfig-maven-plugin](https://ec4j.github.io/editorconfig-maven-plugin/index.html) is a Maven
plugin for checking whether project files comply with format rules defined
in [.editorconfig](https://editorconfig.org/) files and eventually also for fixing the violations.

官方文档：

- [EditorConfig](https://editorconfig.org/)
- [editorconfig-maven-plugin](https://ec4j.github.io/editorconfig-maven-plugin/index.html)

## 快速使用

1）在你的 Spring Boot 项目根目录中添加 `.editorconfig` 文件：

```
# EditorConfig helps developers define and maintain consistent
# coding styles between different editors and IDEs
# https://editorconfig.org/

root = true

[*]

# Change these settings to your own preference
indent_style = space
indent_size = 4

# We recommend you to keep these unchanged
end_of_line = lf
charset = utf-8
trim_trailing_whitespace = true
insert_final_newline = true

[*.md]
trim_trailing_whitespace = false
```

2）在你的 Spring Boot 项目中集成插件管理（Kaddo-框架已集成插件管理，开发者可以跳过）：

```
<build>
  <pluginManagement>
    <plugins>
      <plugin>
        <groupId>org.ec4j.maven</groupId>
        <artifactId>editorconfig-maven-plugin</artifactId>
        <version>${editorconfig-maven-plugin.version}</version>
        <executions>
          <execution>
            <id>check</id>
            <phase>verify</phase>
            <goals>
              <goal>check</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <excludes>
            <!-- Note that maven submodule directories and many non-source file patterns are excluded by default -->
            <!-- You can exclude further files from processing: -->
            <!-- <exclude>src/main/**/*.whatever</exclude> -->
          </excludes>
          <!-- All files are included by default: -->
          <!-- <includes> -->
          <!--   <include>**</include> -->
          <!-- </includes> -->
        </configuration>
      </plugin>
    </plugins>
  </pluginManagement>
</build>
```

3）在你的 Spring Boot 项目中使用插件（Kaddo-框架已集成插件管理，使用更加方便）：

```
<build>
  <plugins>
    <plugin>
      <groupId>org.ec4j.maven</groupId>
      <artifactId>editorconfig-maven-plugin</artifactId>
    </plugin>
  </plugins>
</build>
```

4）执行命令（会自动处理代码格式）：

```
mvn editorconfig:format
```
