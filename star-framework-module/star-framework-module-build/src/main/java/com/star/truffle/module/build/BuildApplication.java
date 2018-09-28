/**create by liuhua at 2018年7月13日 上午9:34:46**/
package com.star.truffle.module.build;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class BuildApplication {

  public static void main(String[] args) {
    SpringApplication.run(BuildApplication.class, args);
  }
}
