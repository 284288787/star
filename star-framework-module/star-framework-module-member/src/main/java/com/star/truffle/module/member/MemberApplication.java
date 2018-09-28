/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.star.truffle.common.config.EnableModuleStatic;
import com.star.truffle.core.security.DisableSecurity;
import com.star.truffle.module.alibaba.config.EnableModuleAlibaba;
import com.star.truffle.module.user.config.EnableModuleUser;

@EnableModuleUser
@EnableModuleAlibaba
@EnableModuleStatic
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@EnableDiscoveryClient
@DisableSecurity
public class MemberApplication {

  public static void main(String[] args) {
    SpringApplication.run(MemberApplication.class, args);
  }
}
