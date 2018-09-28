/**create by framework at 2018年09月07日 09:48:09**/
package com.star.truffle.module.weixin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.star.truffle.module.weixin.dao",
    "com.star.truffle.module.weixin.cache",
    "com.star.truffle.module.weixin.service",
    "com.star.truffle.module.weixin.controller",
    "com.star.truffle.module.weixin.properties",
})
public class WeixinModuleConfig {

}
