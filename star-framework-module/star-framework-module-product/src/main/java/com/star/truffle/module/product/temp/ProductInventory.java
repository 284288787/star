/**create by liuhua at 2018年9月3日 上午10:19:31**/
package com.star.truffle.module.product.temp;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldAdd;
import com.star.truffle.module.build.annotation.StarFieldEdit;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "商品库存", createTable = true, tableName = "product_inventory", listPage = false, addPage = false)
public class ProductInventory {

  @StarField(caption = "id", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  private Long id;
  
  @StarField(caption = "商品ID", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", hidden = true, substituteName = "productName")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", hidden = true, substituteName = "productName")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long productId;
  
  @StarField(caption = "库存总数", dsType = DsType.INT, dsLength = 10)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze="number", zhengzeMsg="请输入正整数")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze="number", zhengzeMsg="请输入正整数")
  @StarFieldList(inputType = InputType.text)
  private Integer number;      //库存总数
  
  @StarField(caption = "已售数量", dsType = DsType.INT, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private Integer soldNumber;  //已售数量
  
  @StarField(caption = "提成类型", dsType = DsType.TINYINT, dsLength = 1, defaultAddFieldValue = "1", defaultEditFieldValue = "1", enumName = "ProductInventoryTypeEnum", enumOptTypes = "{\"type\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"product\": {\"type\": 1, \"caption\": \"商品\"}, \"active\": {\"type\": 2, \"caption\": \"活动\"}}")
  private Integer type;        //库存类型：1商品 2活动
}
