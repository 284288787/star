/**create by liuhua at 2018年8月16日 上午9:49:41**/
package com.star.truffle.module.build.dto;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldAdd;
import com.star.truffle.module.build.annotation.StarFieldEdit;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "学生信息", idWorkerId = 1, idWorkerDataCenterId = 2, createTable = true, tableName = "t_student")
public class Student {

  @StarField(caption = "主键", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long studentId;
  
  @StarField(caption = "姓名", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,10}", zhengzeMsg = "长度在2至10位", placeholder = "姓名什么的要输对")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,10}", zhengzeMsg = "长度在2至10位", placeholder = "姓名什么的要输对")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String name;
  
  @StarField(caption = "手机号码", dsType = DsType.VARCHAR, dsLength = 11)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = "mobile")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = "mobile")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String mobile;
  
  @StarField(caption = "性别", dsType = DsType.TINYINT, dsLength = 1, enumName = "SexEnum", enumOptTypes = "{\"val\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"man\": {\"val\": 1, \"caption\": \"男\"}, \"woman\": {\"val\": 0, \"caption\": \"女\"}}")
  @StarFieldAdd(inputType = InputType.select, inputValue = "{\"1\":\"男\",\"0\":\"女\"}", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.select, inputValue = "{\"1\":\"男\",\"0\":\"女\"}", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:男;0:女'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"男\",\"0\":\"女\"}")
  private Integer sex;
  
  @StarField(caption = "出生日期", dsType = DsType.DATETIME, dsLength = 0)
  @StarFieldAdd(inputType = InputType.date, inputValue = "Y-m-d", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.date, inputValue = "Y-m-d", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.text, inputValue = "Y-m-d")
  private Date birth;
  
  @StarField(caption = "地区", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldAdd(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "areaName")
  @StarFieldEdit(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "areaName")
  @StarFieldList(inputType = InputType.area, hidden = true, substituteName = "areaName")
  @StarFieldQuery(inputType = InputType.area, hidden = true, substituteName = "areaName")
  private Long areaId;
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date createTime;
  
  @StarField(caption = "更新日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date", defaultEditFieldValue = "new Date()", defaultEditFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date updateTime;
}
