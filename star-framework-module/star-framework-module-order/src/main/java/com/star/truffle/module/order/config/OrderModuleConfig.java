/**create by framework at 2018年09月03日 14:31:32**/
package com.star.truffle.module.order.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.star.truffle.module.order.dao",
    "com.star.truffle.module.order.cache",
    "com.star.truffle.module.order.service",
    "com.star.truffle.module.order.controller",
    "com.star.truffle.module.order.properties",
})
public class OrderModuleConfig {

}
