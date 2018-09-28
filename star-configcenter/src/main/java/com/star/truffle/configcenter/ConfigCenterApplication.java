/** create by liuhua at 2018年6月6日 上午9:32:20 **/
package com.star.truffle.configcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigCenterApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(ConfigCenterApplication.class, args);
  }
}
