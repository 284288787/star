/**create by framework at 2018年09月03日 09:54:38**/
package com.star.truffle.module.product.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.star.truffle.module.product.dao",
    "com.star.truffle.module.product.cache",
    "com.star.truffle.module.product.service",
    "com.star.truffle.module.product.controller",
    "com.star.truffle.module.product.properties",
    "com.star.truffle.module.product.job",
})
public class ProductModuleConfig {

}
