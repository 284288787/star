/**create by liuhua at 2018年7月13日 下午4:19:53**/
package com.star.truffle.jymgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

import com.star.truffle.common.config.EnableModuleStatic;
import com.star.truffle.core.security.EnableStarSecurity;
import com.star.truffle.module.alibaba.config.EnableModuleAlibaba;
import com.star.truffle.module.member.config.EnableModuleMember;
import com.star.truffle.module.order.config.EnableModuleOrder;
import com.star.truffle.module.product.config.EnableModuleProduct;
import com.star.truffle.module.user.config.EnableModuleUser;
import com.star.truffle.module.weixin.config.EnableModuleWeixin;

@EnableModuleOrder
@EnableModuleProduct
@EnableModuleMember
@EnableModuleAlibaba
@EnableModuleWeixin
@EnableModuleUser
@EnableModuleStatic
@EnableStarSecurity
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class BlyxApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlyxApplication.class, args);
  }
}
