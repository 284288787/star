/**create by framework at 2018年09月03日 14:31:32**/
package com.star.truffle.module.order.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE })
@Documented
@Import({OrderModuleConfig.class})
public @interface EnableModuleOrder {

}
