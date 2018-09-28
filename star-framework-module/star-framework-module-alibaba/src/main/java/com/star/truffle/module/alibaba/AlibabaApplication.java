/**create by framework at 2018年09月19日 17:06:47**/
package com.star.truffle.module.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.star.truffle.common.config.EnableModuleStatic;
import com.star.truffle.core.security.DisableSecurity;

@EnableModuleStatic
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@EnableDiscoveryClient
@DisableSecurity
public class AlibabaApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlibabaApplication.class, args);
  }
}
