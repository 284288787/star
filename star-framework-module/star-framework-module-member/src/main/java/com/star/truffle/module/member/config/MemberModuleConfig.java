/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.star.truffle.module.member.dao",
    "com.star.truffle.module.member.cache",
    "com.star.truffle.module.member.service",
    "com.star.truffle.module.member.controller",
    "com.star.truffle.module.member.properties",
})
public class MemberModuleConfig {

}
