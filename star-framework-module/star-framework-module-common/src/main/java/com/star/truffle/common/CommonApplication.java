/**create by liuhua at 2018年7月13日 上午9:34:46**/
package com.star.truffle.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CommonApplication {

  public static void main(String[] args) {
    SpringApplication.run(CommonApplication.class, args);
  }
}
