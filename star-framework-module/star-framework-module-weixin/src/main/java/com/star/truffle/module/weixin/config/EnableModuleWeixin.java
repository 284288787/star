/**create by framework at 2018年09月07日 09:48:09**/
package com.star.truffle.module.weixin.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE })
@Documented
@Import({WeixinModuleConfig.class})
public @interface EnableModuleWeixin {

}
