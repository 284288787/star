/**create by liuhua at 2018年9月3日 上午11:41:36**/
package com.star.truffle.module.product.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldAdd;
import com.star.truffle.module.build.annotation.StarFieldEdit;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "分销区域", idWorkerId = 1, idWorkerDataCenterId = 2, createTable = true, tableName = "distribution_region")
public class DistributionRegion {

  @StarField(caption = "区域ID", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long regionId;
  
  @StarField(caption = "区域名称", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,10}", zhengzeMsg = "长度在2至10位", placeholder = "区域名称，简单叫法")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,10}", zhengzeMsg = "长度在2至10位", placeholder = "区域名称，简单叫法")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String name;
  
  @StarField(caption = "省份", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldAdd(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "provinceName")
  @StarFieldEdit(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "provinceName")
  @StarFieldList(inputType = InputType.area, hidden = true, substituteName = "provinceName")
  @StarFieldQuery(inputType = InputType.area, hidden = true, substituteName = "provinceName")
  private Long provinceId;    //省
  
  @StarField(caption = "城市", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldAdd(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "cityName")
  @StarFieldEdit(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "cityName")
  @StarFieldList(inputType = InputType.area, hidden = true, substituteName = "cityName")
  @StarFieldQuery(inputType = InputType.area, hidden = true, substituteName = "cityName")
  private Long cityId;        //市
  
  @StarField(caption = "区县", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldAdd(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "areaName")
  @StarFieldEdit(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "areaName")
  @StarFieldList(inputType = InputType.area, hidden = true, substituteName = "areaName")
  @StarFieldQuery(inputType = InputType.area, hidden = true, substituteName = "areaName")
  private Long areaId;        //区县
  
  @StarField(caption = "乡镇/街道", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldAdd(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "townName")
  @StarFieldEdit(inputType = InputType.area, requiredMsg = "必选", hidden = true, substituteName = "townName")
  @StarFieldList(inputType = InputType.area, hidden = true, substituteName = "townName")
  @StarFieldQuery(inputType = InputType.area, hidden = true, substituteName = "townName")
  private Long townId;        //乡镇 街道  例如 同属芙蓉区，坡子街 药王街 都可能有一个分销商
  
  @StarField(caption = "区域状态", dsType = DsType.TINYINT, dsLength = 1, enumName = "DistributionRegionStateEnum", enumOptTypes = "{\"state\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"onshelf\": {\"state\": 1, \"caption\": \"有效\"}, \"disabled\": {\"state\": 2, \"caption\": \"禁用\"}, \"deleted\": {\"state\": 3, \"caption\": \"删除\"}}")
  @StarFieldAdd(inputType = InputType.select, inputValue = "{\"1\":\"有效\",\"2\":\"禁用\",\"3\":\"删除\"}", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.select, inputValue = "{\"1\":\"有效\",\"2\":\"禁用\",\"3\":\"删除\"}", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:有效;2:禁用;3:删除'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"有效\",\"2\":\"禁用\",\"3\":\"删除\"}")
  private Integer state;      //状态 1有效 2禁用 3删除
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date createTime;
  
  @StarField(caption = "更新日期", dsType = DsType.DATETIME, dsLength = 0, defaultEditFieldValue = "new Date()", defaultEditFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date updateTime;
  
  @StarField(caption = "粉丝数", dsType = DsType.INT, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private Integer fansNum;       //粉丝数，下面买过东西的会员人数
  
  @StarField(caption = "已售件数", dsType = DsType.INT, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private Integer soldNum;       //已出售商品件数
}
