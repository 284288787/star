/**create by liuhua at 2018年11月7日 下午1:48:26**/
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

@StarDomainName(caption = "商品标签", createTable = true, tableName = "product_tag")
public class ProductTag {

  @StarField(caption = "标签ID", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long tagId;
  
  @StarField(caption = "标签名称", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".{2,25}", zhengzeMsg = "长度在2至25个字", placeholder = "商品标题")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".{2,25}", zhengzeMsg = "长度在2至25个字", placeholder = "商品标题")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String tagName;
  
  @StarField(caption = "标签类型", dsType = DsType.TINYINT, dsLength = 1, defaultAddFieldValue = "1")
  private Integer type;
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date createTime;
}
