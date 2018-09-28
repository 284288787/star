/**create by liuhua at 2018年7月13日 下午4:19:53**/
package com.star.truffle.jymgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

import com.star.truffle.common.config.EnableModuleStatic;
import com.star.truffle.core.security.EnableStarSecurity;
import com.star.truffle.module.user.config.EnableModuleUser;

@EnableModuleStatic
@EnableModuleUser
@EnableStarSecurity
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class JymgrApplication {

  public static void main(String[] args) {
    SpringApplication.run(JymgrApplication.class, args);
  }
}
