/**create by liuhua at 2018年8月16日 上午9:56:22**/
package com.star.truffle.module.build.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.star.truffle.module.build.constant.InputType;

/**
 * 显示在编辑界面的元素描述
 * @author liuhua
 *
 */
@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME)
public @interface StarFieldEdit {

  InputType inputType();
  
  //如果是inputType = select 为{1:"男",0:"女"}
  //如果是inputType = date   为Y-m-d
  String inputValue() default "";
  
  String placeholder() default "";
  
  //有值表示必填
  String requiredMsg() default "";
  
  //mobile email money 
  String zhengze() default "";
  
  String zhengzeMsg() default "";
  
  //是否在界面隐藏，例如areaId
  boolean hidden() default false;
  
  //替身名称，例如areaName,只有hidden=true时，且该值有值，才有效
  String substituteName() default "";
}
