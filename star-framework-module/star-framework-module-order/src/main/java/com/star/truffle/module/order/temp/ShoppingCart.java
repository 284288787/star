/**create by liuhua at 2018年9月21日 上午11:29:59**/
package com.star.truffle.module.order.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.constant.DsType;

@StarDomainName(caption = "购物车", createTable = true, tableName = "shopping_cart", listPage = false, addPage = false, editPage = false)
public class ShoppingCart {

  @StarField(caption = "主键", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  private Long cartId;
  
  @StarField(caption = "供应ID", dsType = DsType.BIGINT, dsLength = 20)
  private Long productId;
  
  @StarField(caption = "会员ID", dsType = DsType.BIGINT, dsLength = 20)
  private Long memberId;
  
  @StarField(caption = "是否选中", dsType = DsType.TINYINT, dsLength = 1)
  private Integer checked;    //是否选中 1是 0否
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  private Date createTime;
}
