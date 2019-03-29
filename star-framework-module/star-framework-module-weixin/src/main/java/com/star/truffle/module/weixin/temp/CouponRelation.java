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
  
  @StarField(caption = "卡券标题", dsType = DsType.VARCHAR, dsLength = 10)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{1,10}", zhengzeMsg = "长度在1至10个字", placeholder = "卡券标题")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{1,10}", zhengzeMsg = "长度在1至10个字", placeholder = "卡券标题")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Integer businessType;
  private Long businessId;
  private Long couponId;
}
