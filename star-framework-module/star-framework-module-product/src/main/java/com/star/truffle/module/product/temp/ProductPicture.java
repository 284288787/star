/**create by liuhua at 2018年9月3日 上午10:24:36**/
package com.star.truffle.module.product.temp;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldAdd;
import com.star.truffle.module.build.annotation.StarFieldEdit;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "商品图片", createTable = true, tableName = "product_picture", listPage = false, addPage = false)
public class ProductPicture {

  @StarField(caption = "id", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  private Long id;
  
  @StarField(caption = "商品ID", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", hidden = true, substituteName = "productName")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", hidden = true, substituteName = "productName")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long productId;
  
  @StarField(caption = "图片类型", dsType = DsType.TINYINT, dsLength = 1, enumName = "ProductPictureTypeEnum", enumOptTypes = "{\"type\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"main\": {\"type\": 1, \"caption\": \"主图\"}, \"other\": {\"type\": 2, \"caption\": \"多角图\"}}")
  @StarFieldAdd(inputType = InputType.select, inputValue = "{\"1\":\"主图\",\"2\":\"多角图\"}", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.select, inputValue = "{\"1\":\"主图\",\"2\":\"多角图\"}", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:主图;2:多角图'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"主图\",\"2\":\"多角图\"}")
  private Integer type;       //1主图 2多角图
  
  @StarField(caption = "商品路径", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填")
  @StarFieldList(inputType = InputType.text)
  private String url;         //图片url
}
