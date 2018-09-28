/**create by liuhua at 2018年6月6日 下午4:49:27**/
package com.star.truffle.eurkacenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurkaApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(EurkaApplication.class, args);
}
}
