## 统一框架的缓存插件


### 之前base包提供了一些方式来使用redis缓存

*  使用自定义的注解，大家通过注解来完成缓存的操作，
* 使用base包里面提供的RedisService来进行操作，但是提供的redis操作方式很少
* 通过创建的RedisTemplate来执行操作，但是序列化的方式不统一
  为了解决以上的一些问题，并且规范redis的操作，在统一框架层面进行了调整和修改，现在不提供任何自定义的方法，仅仅是提供配置和初始化redis的实例，由业务部门自己选择使用方式
  
  
### 如何配置redis组件呢？ 主要就是下面几个步骤

* 引入redis-starter插件，并且把之前的redis相关的pom依赖全部去掉

```

<dependency>
  <groupId>com.star.truffle</groupId>
  <artifactId>star-framework-starter-redis</artifactId>
</dependency>

```


### 详细解释配置文件

* 集群模式

```

spring:
  redis:
    timeout: 2s   #操作超时的情况，默认60s
    cluster.nodes:
      - 10.10.3.51:2001
      - 10.10.3.51:2002
      - 10.10.3.51:2003
    lettuce:
      pool:
        max-active: 10
        max-idle: 10
        max-wait: 1000s
  cache:
    redis:
      time-to-live: 300s   # key的超时时间

```


* 单机模式

```
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    timeout: 2s   #操作超时的情况，默认60s
    lettuce:
      pool:
        max-active: 10
        max-idle: 10
        max-wait: 1000s
  cache:
    redis:
      time-to-live: 300s
```


### 操作方式

* 放弃了之前使用Jedis和JedisCluster的方式

* 改成了使用redisTemplate的方式

可以直接注入template进行使用

```
@Autowired
private StringRedisTemplate stringRedisTemplate;
```

* 之前使用redis cache的使用方式不变

###  与之前的区别和可能存在的问题，所以希望大家能够注意

* 废弃了之前的base包里面自定义的注解

* 废弃了redisService的操作方式

* 如果有使用RedisTemplate或者使用springCacheManager这个bean来直接进行操作的话，可能由于之前定义的序列化方式不统一造成的影响
