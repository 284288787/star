/**create by liuhua at 2018年11月7日 上午9:38:40**/
package com.star.truffle.module.product.temp;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "大分类商品关系", createTable = true, tableName = "category_product_relation")
public class CategoryProdcutRelation {
  
  @StarField(caption = "主键ID", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long id;
  
  @StarField(caption = "大分类ID", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long cateId;

  @StarField(caption = "商品ID", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long productId;
}
