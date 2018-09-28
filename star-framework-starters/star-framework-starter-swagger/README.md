## 统一框架swagger处理

### 目前项目中的swagger使用：

* 每个业务代码中都要写入重复的swagger启动代码，造成代码冗余。

### swagger框架目的：

* 关注swagger的配置即可，不用书写swagger的启动代码。


### 如何引入？步骤如下：

* 移除项目之前所有的swagger依赖，引入如下依赖


```
<dependency>
  <groupId>com.star.truffle</groupId>
  <artifactId>star-framework-starter-web</artifactId>
</dependency>

```

* 配置如下：

```

hnSwagger:  #惠农swagger配置
 basePackage: com.star.framework.sample.swagger.web.controller  #swagger扫描的api包，不能为空
 enable: true  # 是否开启swagger，建议生产关闭，默认true
 title: 惠农架构组  # 标题
 description: 惠农架构组描述 #描述

```

* 如果有需要生成swagger.json文件， 可以直接访问/v2/api-docs路径获得