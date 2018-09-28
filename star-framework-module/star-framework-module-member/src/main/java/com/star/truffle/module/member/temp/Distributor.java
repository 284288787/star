/**create by liuhua at 2018年9月3日 上午11:50:24**/
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

@StarDomainName(caption = "分销商", createTable = true, tableName = "distributor")
public class Distributor {

  @StarField(caption = "distributorId", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long distributorId;    
  
  @StarField(caption = "头像", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldAdd(inputType = InputType.text)
  @StarFieldEdit(inputType = InputType.text)
  private String head;           //头像
  
  @StarField(caption = "姓名", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".{2,10}", zhengzeMsg = "2至10个字", placeholder = "分销商姓名")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".{2,10}", zhengzeMsg = "2至10个字", placeholder = "分销商姓名")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String name;           //联系人姓名
  
  @StarField(caption = "店铺名称", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".{2,40}", zhengzeMsg = "2至20个字")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".{2,40}", zhengzeMsg = "2至20个字")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String shopName;       //店铺名称
  
  @StarField(caption = "店铺编码", dsType = DsType.VARCHAR, dsLength = 32, defaultAddFieldValue = "001")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String shopCode;       //店铺code
  
  @StarField(caption = "手机号", dsType = DsType.VARCHAR, dsLength = 11)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = "mobile")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = "mobile")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String mobile;
  
  @StarField(caption = "分销区域", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必选")
  @StarFieldList(inputType = InputType.text, hidden = true, substituteName = "regionName")
  @StarFieldQuery(inputType = InputType.text, hidden = true, substituteName = "regionName")
  private Long regionId;
  
  @StarField(caption = "街道地址", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".{2,40}", zhengzeMsg = "2至20个字")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".{2,40}", zhengzeMsg = "2至20个字")
  @StarFieldList(inputType = InputType.text)
  private String address;        //街道地址 门牌号
  
  @StarField(caption = "店铺编码", dsType = DsType.TINYINT, dsLength = 32, defaultAddFieldValue = "1", enumName = "EnabledEnum", enumOptTypes = "{\"val\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"enabled\": {\"val\": 1, \"caption\": \"可用\"}, \"disabled\": {\"val\": 0, \"caption\": \"禁用\"}}")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:可用;0:禁用'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"可用\",\"0\":\"禁用\"}")
  private Integer enabled;       //状态 1有效 0无效
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date createTime;
  
  @StarField(caption = "更新日期", dsType = DsType.DATETIME, dsLength = 0, defaultEditFieldValue = "new Date()", defaultEditFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date updateTime;
  
  @StarField(caption = "openid", dsType = DsType.VARCHAR, dsLength = 32)
  @StarFieldList(inputType = InputType.text)
  private String openId;         //微信唯一标识
  
  @StarField(caption = "粉丝数", dsType = DsType.INT, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private Integer fansNum;          //粉丝数，下面买过东西的会员人数
  @StarField(caption = "已售件数", dsType = DsType.INT, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private Integer soldNum;          //已出售商品件数
}
