/**create by liuhua at 2018年8月16日 上午9:56:22**/
package com.star.truffle.module.build.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.star.truffle.module.build.constant.InputType;

/**
 * 显示在列表的元素描述
 * @author liuhua
 *
 */
@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME)
public @interface StarFieldQuery {

  InputType inputType();
  
  //如果是inputType = select 为{1:"男",0:"女"}
  //如果是inputType = date   为Y-m-d
  String inputValue() default "";
  
  //是否隐藏
  boolean hidden() default false;
  
  //如果为隐藏，是否需要替身，例如 areaName
  String substituteName() default "";
}
