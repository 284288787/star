/**create by framework at 2018年09月19日 17:06:47**/
package com.star.truffle.module.alibaba.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE })
@Documented
@Import({AlibabaModuleConfig.class})
public @interface EnableModuleAlibaba {

}
