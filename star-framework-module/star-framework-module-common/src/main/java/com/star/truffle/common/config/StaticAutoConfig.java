/**create by liuhua at 2018年7月16日 下午4:25:07**/
package com.star.truffle.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
  "com.star.truffle.common.service",
  "com.star.truffle.common.controller",
  "com.star.truffle.common.properties"
})
public class StaticAutoConfig {

}
