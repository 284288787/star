/**create by liuhua at 2018年8月16日 上午9:56:22**/
package com.star.truffle.module.build.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.star.truffle.module.build.constant.InputType;
import com.star.truffle.module.build.constant.TextAlign;

/**
 * 显示在列表的元素描述
 * @author liuhua
 *
 */
@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME)
public @interface StarFieldList {

  InputType inputType();
  
  //如果是inputType = select 为{1:"男",0:"女"}
  //如果是inputType = date   为Y-m-d
  String inputValue() default "";
  
  //宽度，没有单位表示百分比
  String width() default "50";
  
  //是否可排序
  boolean sort() default true;
  
  //是否可编辑
  boolean edit() default false;
  
  TextAlign align() default TextAlign.center;
  
  boolean hidden() default false;
  
  String substituteName() default "";
  
}
