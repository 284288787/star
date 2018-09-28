/**create by liuhua at 2018年7月16日 下午4:24:48**/
package com.star.truffle.common.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE })
@Documented
@Import({StaticAutoConfig.class})
public @interface EnableModuleStatic {

}
