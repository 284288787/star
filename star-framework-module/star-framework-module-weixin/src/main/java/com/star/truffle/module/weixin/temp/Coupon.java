/**create by liuhua at 2019年1月24日 下午4:31:32**/
package com.star.truffle.module.weixin.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldAdd;
import com.star.truffle.module.build.annotation.StarFieldEdit;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "卡券", createTable = true, tableName = "coupon")
public class Coupon {

  @StarField(caption = "卡券ID", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long couponId;
  
  @StarField(caption = "卡券标题", dsType = DsType.VARCHAR, dsLength = 10)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{1,10}", zhengzeMsg = "长度在1至10个字", placeholder = "卡券标题")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{1,10}", zhengzeMsg = "长度在1至10个字", placeholder = "卡券标题")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String title;
  
  @StarField(caption = "微信卡券Id", dsType = DsType.VARCHAR, dsLength = 40)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{20,40}", zhengzeMsg = "长度在20至40个字符", placeholder = "微信卡券Id")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{20,40}", zhengzeMsg = "长度在20至40个字符", placeholder = "微信卡券Id")
  @StarFieldList(inputType = InputType.text)
  private String cardId;
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date createTime;
}
