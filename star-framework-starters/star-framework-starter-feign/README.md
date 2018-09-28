## 统一框架feign处理

### 目的

* 将feignRetryer从业务代码，转义到了框架中。
*  增加配置信息。


### 如何引入? 步骤如下:

* 删除之前的所有feign依赖包括hystrix，引入如下依赖即可

```
<dependency>
  <groupId>com.huinong.truffle</groupId>
  <artifactId>hn-framework-starter-feign</artifactId>
</dependency>

```


* 相关配置如下，如无特殊需求，请采用默认配置，无需配置

```
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 60000   # 超时时间
hnFeignRetry:
  period: 100      # feign间隔周期, 单位ms  默认为100ms
  maxPeriod: 1000  # feign最大间隔周期, 单位ms  默认为1000ms
  maxAttempts: 0   # feign最大重试次数
  enabled: true     # 默认为true
ribbon:
  MaxAutoRetries: 0 # 对当前实例的重试次数
  MaxAutoRetriesNextServer: 1 # 切换实例的重试次数
  OkToRetryOnAllOperations: false #重试操作，建议false，防止数据不幂等性问题。
  ReadTimeout: 30000 # 请求处理的超时时间
  ConnectTimeout: 5000 # 请求连接超时时间
  okhttp:  
    enabled: true  # 启用okhttp作为底层的http client。
feign:
  hystrix:
    enabled: true  # 启用 hystrix

# 单个服务的配置
sample-cloud:    # 服务名称
  ribbon:
    NIWSServerListClassName: com.netflix.loadbalancer.ConfigurationBasedServerList
    listOfServers: localhost:8882 # 修改调用地址
    ConnectTimeout: 5000
    ReadTimeout: 30000
    OkToRetryOnAllOperations: false   #重试操作
```

### 统一默认配置文件

* local环境默认配置
```
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 60000   # 超时时间
hnFeignRetry:
  period: 100      # feign间隔周期, 单位ms  默认为100ms
  maxPeriod: 1000  # feign最大间隔周期, 单位ms   默认为1000ms
  maxAttempts: 0   # feign最大重试次数
  enabled: true     # 默认为true
ribbon:
  MaxAutoRetries: 0 # 对当前实例的重试次数
  MaxAutoRetriesNextServer: 1 # 切换实例的重试次数
  OkToRetryOnAllOperations: false #重试操作，建议false，防止数据不幂等性问题。
  ReadTimeout: 30000 # 请求处理的超时时间
  ConnectTimeout: 5000 # 请求连接超时时间
  okhttp:  
    enabled: true  # 启用okhttp作为底层的http client。
feign:
  hystrix:
    enabled: true  # 启用 hystrix
```

* dev环境默认配置
```
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 60000   # 超时时间
hnFeignRetry:
  period: 100      # feign间隔周期, 单位ms
  maxPeriod: 1000  # feign最大间隔周期, 单位ms
  maxAttempts: 0   # feign最大重试次数
  enabled: true     # 默认为true
ribbon:
  MaxAutoRetries: 0 # 对当前实例的重试次数
  MaxAutoRetriesNextServer: 1 # 切换实例的重试次数
  OkToRetryOnAllOperations: false #重试操作，建议false，防止数据不幂等性问题。
  ReadTimeout: 30000 # 请求处理的超时时间
  ConnectTimeout: 5000 # 请求连接超时时间
  okhttp:  
    enabled: true  # 启用okhttp作为底层的http client。
feign:
  hystrix:
    enabled: true  # 启用 hystrix
```

* test环境默认配置
```
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 60000   # 超时时间
  threadpool:
    default:
      coreSize: 100   #线程池大小
      
hnFeignRetry:
  period: 100      # feign间隔周期, 单位ms
  maxPeriod: 1000  # feign最大间隔周期, 单位ms
  maxAttempts: 0   # feign最大重试次数
  enabled: true     # 默认为true
ribbon:
  MaxAutoRetries: 0 # 对当前实例的重试次数
  MaxAutoRetriesNextServer: 1 # 切换实例的重试次数
  OkToRetryOnAllOperations: false #重试操作，建议false，防止数据不幂等性问题。
  ReadTimeout: 30000 # 请求处理的超时时间
  ConnectTimeout: 5000 # 请求连接超时时间
  okhttp:  
    enabled: true  # 启用okhttp作为底层的http client。
feign:
  hystrix:
    enabled: true  # 启用 hystrix
```

* pro环境默认配置
```
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 60000   # 超时时间
  threadpool:
    default:
      coreSize: 100   #线程池大小
hnFeignRetry:
  period: 100      # feign间隔周期, 单位ms
  maxPeriod: 1000  # feign最大间隔周期, 单位ms
  maxAttempts: 0   # feign最大重试次数
  enable: true     # 默认为true
ribbon:
  MaxAutoRetries: 0 # 对当前实例的重试次数
  MaxAutoRetriesNextServer: 1 # 切换实例的重试次数
  OkToRetryOnAllOperations: false #重试操作，建议false，防止数据不幂等性问题。
  ReadTimeout: 30000 # 请求处理的超时时间
  ConnectTimeout: 5000 # 请求连接超时时间
  okhttp:  
    enabled: true  # 启用okhttp作为底层的http client。
feign:
  hystrix:
    enabled: true  # 启用 hystrix
```


### 注意事项

> feign中使用的bean，要实现序列化，且需有序列id，并且对象有属性
