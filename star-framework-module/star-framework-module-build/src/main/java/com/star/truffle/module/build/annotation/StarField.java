/**create by liuhua at 2018年8月16日 上午9:56:22**/
package com.star.truffle.module.build.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.star.truffle.module.build.constant.DsType;

@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME)
public @interface StarField {

  //名称 例如：姓名
  String caption();
  //是否主键
  boolean primary() default false;
  //表字段名称，若为空取属性名
  String dsName() default "";
  //字段类型
  DsType dsType();
  //字段长度
  int dsLength();
  //若值是枚举 例如性别 则可以设置 枚举类型名称 SexEnum
  String enumName() default "";
  //枚举类型括号内的数据类型，及属性名称，例如：{\"val\":\"int\",\"caption\":\"String\"}
  //表示SexEnum(int val, String caption)
  //如果不需要括号参数，则该值为空，则为SexEnum
  String enumOptTypes() default "";
  //枚举类型的值的选项，例如: {\"man\": {\"val\": 1, \"caption\": \"男\"}, \"woman\": {\"val\": 0, \"caption\": \"女\"}}
  //表示 man(1, "男"), woman(0, "女");
  //如果enumOptTypes为空,则该值应该为["man", "woman"]，最终枚举为man,woman
  String enumOptValues() default "";
  
  //某些字段的值不由用户填写，例如创建日期，该值应为 java.util.Date
  //如果类型为java.lang包下则不用填写
  String defaultAddFieldValueType() default "";
  //例如 new Date()
  String defaultAddFieldValue() default "";
  
  //某些字段的值不由用户填写，例如更新日期，该值应为 java.util.Date
  //如果类型为java.lang包下则不用填写
  String defaultEditFieldValueType() default "";
  //例如 new Date()
  String defaultEditFieldValue() default "";
}
