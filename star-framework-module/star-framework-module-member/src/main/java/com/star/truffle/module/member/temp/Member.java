/**create by liuhua at 2018年9月3日 上午11:37:17**/
package com.star.truffle.module.member.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldAdd;
import com.star.truffle.module.build.annotation.StarFieldEdit;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "消费者", createTable = true, tableName = "member_info", addPage = false, editPage = false, addButton = false, editButton = false, disabledButton = false, deleteButton = false)
public class Member {

  @StarField(caption = "memberId", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long memberId;
  
  @StarField(caption = "头像", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldAdd(inputType = InputType.text)
  @StarFieldEdit(inputType = InputType.text)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String head;           //头像
  
  @StarField(caption = "姓名", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private String name;
  
  @StarField(caption = "手机号", dsType = DsType.VARCHAR, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String mobile;
  
  @StarField(caption = "openid", dsType = DsType.VARCHAR, dsLength = 32)
  @StarFieldList(inputType = InputType.text)
  private String openId;         //微信唯一标识
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date createTime;
}
