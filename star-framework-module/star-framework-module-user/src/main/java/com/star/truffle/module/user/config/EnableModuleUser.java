/**create by liuhua at 2018年7月12日 下午2:33:26**/
package com.star.truffle.module.user.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE })
@Documented
@Import({UserModuleConfig.class})
public @interface EnableModuleUser {

}
