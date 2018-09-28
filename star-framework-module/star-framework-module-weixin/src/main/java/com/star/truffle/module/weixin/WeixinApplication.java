/**create by framework at 2018年09月07日 09:48:09**/
package com.star.truffle.module.weixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.star.truffle.core.security.DisableSecurity;
import com.star.truffle.module.order.config.EnableModuleOrder;

@EnableModuleOrder
@SpringBootApplication
@EnableDiscoveryClient
@DisableSecurity
public class WeixinApplication {

  public static void main(String[] args) {
    SpringApplication.run(WeixinApplication.class, args);
  }
}
