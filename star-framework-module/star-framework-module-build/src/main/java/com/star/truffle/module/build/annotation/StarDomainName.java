/**create by liuhua at 2018年8月16日 上午9:51:40**/
package com.star.truffle.module.build.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) 
@Retention(RetentionPolicy.RUNTIME)
public @interface StarDomainName {
  
  //业务名称 例如：教练信息
  String caption();
  //是否创建表
  boolean createTable() default false;
  //若创建表，是否删除同名表
  boolean existsDrop() default false;
  //表名称
  String tableName() default "";
  //主键生成参数1
  long idWorkerId() default 1;
  //主键生成参数2
  long idWorkerDataCenterId() default 1;
  //是否需要列表页面 ，如果不需要，则添加编辑删除启用按钮都没有
  boolean listPage() default true;
  //是否需要新增页面
  boolean addPage() default true;
  //是否需要编辑页面
  boolean editPage() default true;
  //是否需要新增按钮
  boolean addButton() default true;
  //是否需要新增按钮
  boolean editButton() default true;
  //是否需要禁用启用按钮
  boolean disabledButton() default true;
  //是否需要删除按钮
  boolean deleteButton() default true;
  //新增编辑页面的宽度
  String winWidth() default "500px";
  //新增编辑页面的高度
  String winHeight() default "400px";
  
}
