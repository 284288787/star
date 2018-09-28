## 统一框架json处理

### 在目前的项目json处理的问题

*  存在多种json处理框架，比如gson，jackson，fastjson，未做统一，各框架转义差别可能带来一定的差异问题。
*  未规范json的统一配置。


### json框架目的

* 统一json处理，使用spring boot提供的默认json框架 jackson。
* 提供了简单的封装类供业务操作。




### 如何引入？步骤如下：

* 移除之前的gson和fastjson或者其他json框架的依赖，引入如下依赖：

```

<dependency>
  <groupId>com.star.truffle</groupId>
  <artifactId>star-framework-starter-web</artifactId>
</dependency>

```

* json 配置参照spring boot框架默认的配置

```

spring:
 jackson:
  date-format: # Date format string or a fully-qualified date format class name. For instance `yyyy-MM-dd HH:mm:ss`.
  default-property-inclusion: # Controls the inclusion of properties during serialization.
  deserialization: # Jackson on/off features that affect the way Java objects are deserialized.
  generator: # Jackson on/off features for generators.
  joda-date-time-format: # Joda date time format string. If not configured, "date-format" will be used as a fallback if it is configured with a format string.
  locale: # Locale used for formatting.
  mapper.*: # Jackson general purpose on/off features.
  parser.*: # Jackson on/off features for parsers.
  property-naming-strategy: # One of the constants on Jackson's PropertyNamingStrategy. Can also be a fully-qualified class name of a PropertyNamingStrategy subclass.。
  serialization: # Jackson on/off features that affect the way Java objects are serialized.
  time-zone: = # Time zone used when formatting dates. For instance `America/Los_Angeles`


```

* 格式化date样例

```

spring:
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8 # 注意填写时区
```

* 个性化日期格式

```
@Data
public class PersonDomain {
  /**使用JsonFormat 注解覆盖全局日期格式**/
  @JsonFormat(pattern = "yyy-MM-dd")
  private Date born;
  private String name;
 
}

```


*  json工具类使用

```

@Autowired
public StarJson starJson;
 
 
 
@Test
public void obj2Str(){
  Map<String,String> map = new HashMap();
  map.put("name","小刚");
  String json = hnJson.obj2string(map);
  Assert.assertEquals("{\"name\":\"小刚\"}",json);
}
 
@Test
public void strToObj(){
  String json = "{\"name\":\"小刚\"}";
  Map<String,Object> map = hnJson.str2obj(json,HashMap.class);
  Assert.assertEquals("小刚",(String)map.get("name"));
 
}


```