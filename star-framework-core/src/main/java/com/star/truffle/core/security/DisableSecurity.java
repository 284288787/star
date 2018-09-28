/**create by liuhua at 2018年7月14日 下午5:18:19**/
package com.star.truffle.core.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE })
@Documented
@Import({SecurityConfigDisable.class})
public @interface DisableSecurity {

}
