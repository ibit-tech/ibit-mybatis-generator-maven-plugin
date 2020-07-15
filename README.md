# ibit-mybatis-generator-maven-plugin 说明

## maven  引入

```xml
<!--代码生成-->
<plugin>
    <artifactId>ibit-mybatis-generator-maven-plugin</artifactId>
    <groupId>tech.ibit</groupId>
    <version>1.0</version>
    <configuration>
        <!--相关配置-->
    </configuration>
</plugin>
```

指定的 ibit-mybatis 2.1+。

支持 `generate` 和 `generate-with-config`

* `generate` 在 \<configuration\> 中配置参数
* `generate-with-config` 则使用 \<configFile\> 指定配置文件，在配置文件中进行配置

## 相关配置说明

| 名称 | 说明 | 是否必填 | 默认值 |
| --- | --- | --- | --- |
| configFile | 配置文件（用于 generate-with-config） |  是 | / |
| driverName | jdbc 驱动类 | 是 | / |
| jdbcUrl | jdbc url | 是 | / |
| username | 数据库用户 | 是 | / |
| password | 数据库密码 | 是 | / |
| defaultProjectDir | 默认项目文件夹 | 否 | ${project.basedir} |
| defaultBasePackage | 默认包路径 | 否 | 空串 |
| entityProjectDir | entity 项目文件夹 | 否 | ${defaultProjectDir} |
| entityBasePackage | entity 基础包路径 | 否 | ${defaultBasePackage} |
| mapperProjectDir | mapper 项目文件夹 | 否 | ${defaultProjectDir} |
| mapperBasePackage | mapper 基础包路径 | 否 | ${defaultBasePackage} |
| author | 作者 | 否 | 空串 |
| withAll | 是否生成全部（entity + mapper）| 否 | false |
| withEntity | 是否生成 entity | 否 | false |
| withMapper | 是否生成 mapper | 否 | false |
| override | 是否覆盖已有文件 | 否| false |
| tables | 表名，多个用","分割 | 是 | / |

