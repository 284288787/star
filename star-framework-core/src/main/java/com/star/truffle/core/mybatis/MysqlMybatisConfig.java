/**create by liuhua at 2018年7月2日 上午11:35:35**/
package com.star.truffle.core.mybatis;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import lombok.Data;

@Data
public class MysqlMybatisConfig {
  private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

  //datasource对应的mybatis-config.xml文件
  private String mybatisConfig;

  //datasource对应的扫描路径
  private String mapperScanner;

  //sql对象使用的bean路径
  private String aliasPackage;

  //对应sql的xml文件地址
  private String[] mapperLocation;

  private String typeHandlersPackage;


  private ExecutorType executorType;

  private Properties configurationProperties;

  @NestedConfigurationProperty
  private Configuration configuration;

  public Resource[] resolveMapperLocations() {
    return Stream.of(Optional.ofNullable(this.mapperLocation).orElse(new String[0]))
        .flatMap(location -> Stream.of(getResources(location)))
        .toArray(Resource[]::new);
  }

  private Resource[] getResources(String location) {
    try {
      return resourceResolver.getResources(location);
    } catch (IOException e) {
      return new Resource[0];
    }
  }
}
