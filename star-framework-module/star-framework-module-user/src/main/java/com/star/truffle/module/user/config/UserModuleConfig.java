/**create by liuhua at 2018年7月12日 下午2:31:49**/
package com.star.truffle.module.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.star.truffle.module.user.dao",
    "com.star.truffle.module.user.cache",
    "com.star.truffle.module.user.service",
    "com.star.truffle.module.user.controller",
    "com.star.truffle.module.user.properties",
})
public class UserModuleConfig {

}
