## 统一框架数据库插件

> 以前的关于数据库的配置和mybatis的配置限制的地方很多，还需要写代码进行配置，现在全部由框架统一做了适配，业务方只需要配置相关的配置文件
  现在为了兼容之前的仅支持了jdbcTemplate的方式和mybatis的方式，后续会提供更多的使用方式，比如spring-jpa等
  使用需要完成下面几个步骤：
  

* 加入mybatis的pom.xml依赖

```
<dependency>
  <groupId>com.star.truffle</groupId>
  <artifactId>star-framework-starter-mybatis</artifactId>
</dependency>

```  

* 配置文件中加入对应的配置

```

hnMysql:
  writeDemo:
    username: root
    password: password
    url: jdbc:mysql://127.0.0.1:3306/hnframeworktest?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false
    pool:
      maxActive: 30
    mybatisConfig: mapper/mybatis-config.xml
    mapperLocation: mapper/example/write/*.xml
    mapperScanner: com.star.framework.sample.mybatis.dao.write
    aliasPackage: com.star.framework.sample.mybatis.entity

  readDemo:
    username: root
    password: password
    url: jdbc:mysql://127.0.0.1:3306/hnframeworktest?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false
    pool:
      maxActive: 50
    mybatisConfig: mapper/mybatis-config.xml
    mapperLocation: mapper/example/read/*.xml
    mapperScanner: com.star.framework.sample.mybatis.dao.read
    aliasPackage: com.star.framework.sample.mybatis.entity

pagehelper:
  pageSizeZero: true

log4jdbc.sqltiming:
  warn.threshold: 300   # 300ms 会打印warn级别的日志
  error.threshold: 500  # 500ms 会打印error级别的日志

logging.level.jdbc:
  sqlonly: 'OFF'
  sqltiming: WARN
  audit: 'OFF'
  resultset: 'OFF'
  connection: 'OFF'
```

### 分环境的配置

* local环境默认配置
```
log4jdbc.sqltiming:
  warn.threshold: 300   # 300ms 会打印warn级别的日志
  error.threshold: 500  # 500ms 会打印error级别的日志

logging.level.jdbc:
  sqlonly: 'OFF'
  sqltiming: WARN
  audit: 'OFF'
  resultset: 'OFF'
  connection: 'OFF'
```

* dev环境默认配置
```
log4jdbc.sqltiming:
  warn.threshold: 300   # 300ms 会打印warn级别的日志
  error.threshold: 500  # 500ms 会打印error级别的日志

logging.level.jdbc:
  sqlonly: 'OFF'
  sqltiming: WARN
  audit: 'OFF'
  resultset: 'OFF'
  connection: 'OFF'
```

* test环境默认配置
```
log4jdbc.sqltiming:
  warn.threshold: 300   # 300ms 会打印warn级别的日志
  error.threshold: 500  # 500ms 会打印error级别的日志

logging.level.jdbc:
  sqlonly: 'OFF'
  sqltiming: WARN
  audit: 'OFF'
  resultset: 'OFF'
  connection: 'OFF'
```

* pro环境默认配置
```
log4jdbc.sqltiming:
  warn.threshold: 300   # 300ms 会打印warn级别的日志
  error.threshold: 500  # 500ms 会打印error级别的日志

logging.level.jdbc:
  sqlonly: 'OFF'
  sqltiming: WARN
  audit: 'OFF'
  resultset: 'OFF'
  connection: 'OFF'
```


* 在@Springboot注解排除掉默认的datasource的加载和增加@EnableTransactionManagement注解(为了事务)


```
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class
})
@EnableTransactionManagement

```

*  代码层面主要要使用事务的话，通过在在方法上面加上@Transactional注解，并且加入value来指定事务类型

```

// value的值是名称 + Tx， 例子:
@Transactional(value = "writeDemoTx")
public String mybatisTx() {
    // TODO ....
}

```


* 如果之前的项目使用了Mybatis的分页的话，需要修改类的名称

```
MyBatisBasePage page = new MyBatisBasePage();  //用MyBatisBasePage替换之前的BasePage
PageHelper.startPage(pageNo, page.getPageSize());
List<DemoInfo> demoInfoList =  demoInfoRead.getAllDemoInfo(page);
MybatisPageValue<DemoInfo> pageValue = new MybatisPageValue<>(demoInfoList);  //用MybatisPageValue替换之前的PageValue


```


* 如果想增加额外的配置，可以在 mybatis-config.xml 中加入mybatis的自定义配置，不写的话，则使用默认配置

```

<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE configuration PUBLIC
        "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
  <!-- 全局参数 -->
  <settings>
    <!-- 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。 -->
    <setting name="lazyLoadingEnabled" value="true"/>
    <!-- 当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载。 -->
    <setting name="aggressiveLazyLoading" value="true"/>
    <!-- 允许JDBC 生成主键。需要驱动器支持。如果设为了true，这个设置将强制使用被生成的主键，有一些驱动器不兼容不过仍然可以执行。  default:false  -->
    <setting name="useGeneratedKeys" value="true"/>
    <!-- 使用驼峰命名法转换字段。 -->
    <setting name="mapUnderscoreToCamelCase" value="true"/>
    <!-- 设置但JDBC类型为空时,某些驱动程序 要指定值,default:OTHER，插入空值时不需要指定类型 -->
    <setting name="jdbcTypeForNull" value="NULL"/>
  </settings>
 
  <typeAliases>
    <typeAlias alias="DemoInfo" type="com.star.framework.sample.mybatis.entity.DemoInfo"/>
  </typeAliases>
</configuration>

```