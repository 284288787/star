/**create by framework at 2018年09月19日 17:06:47**/
package com.star.truffle.module.alibaba.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.star.truffle.module.alibaba.dao",
    "com.star.truffle.module.alibaba.cache",
    "com.star.truffle.module.alibaba.service",
    "com.star.truffle.module.alibaba.controller",
    "com.star.truffle.module.alibaba.properties",
})
public class AlibabaModuleConfig {

}
