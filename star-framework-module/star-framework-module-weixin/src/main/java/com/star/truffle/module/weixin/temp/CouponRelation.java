/**create by liuhua at 2018年9月14日 下午4:46:14**/
package com.star.truffle.module.weixin.temp;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldAdd;
import com.star.truffle.module.build.annotation.StarFieldEdit;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "卡券关联关系", createTable = true, tableName = "coupon_relation")
public class CouponRelation {

  @StarField(caption = "ID", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long id;
  
  @StarField(caption = "业务类型", dsType = DsType.TINYINT, dsLength = 1, enumName = "CouponBusinessTypeEnum", 
      enumOptTypes = "{\"type\":\"int\",\"caption\":\"String\"}", 
      enumOptValues = "{\"cate\": {\"type\": 1, \"caption\": \"大分类\"}, \"product_cate\": {\"type\": 2, \"caption\": \"商品分类\"}, \"product\": {\"type\": 3, \"caption\": \"商品\"}, \"member\": {\"type\": 4, \"caption\": \"会员\"}}")
  @StarFieldAdd(inputType = InputType.select, inputValue = "{\"1\":\"大分类\",\"2\":\"商品分类\",\"3\":\"商品\",\"4\":\"会员\"}", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.select, inputValue = "{\"1\":\"大分类\",\"2\":\"商品分类\",\"3\":\"商品\",\"4\":\"会员\"}", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:大分类;2:商品分类;3:商品;4:会员'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"大分类\",\"2\":\"商品分类\",\"3\":\"商品\",\"4\":\"会员\"}")
  private Integer businessType;
  
  @StarField(caption = "业务ID", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long businessId;
  
  @StarField(caption = "卡券ID", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long couponId;
  
  @StarField(caption = "是否删除", dsType = DsType.TINYINT, dsLength = 1)
  private Integer deleted;
}
