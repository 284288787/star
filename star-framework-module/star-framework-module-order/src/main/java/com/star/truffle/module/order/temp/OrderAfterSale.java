/**create by liuhua at 2018年9月3日 下午4:54:57**/
package com.star.truffle.module.order.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "订单售后", createTable = true, tableName = "order_aftersale", addPage = false, addButton = false, deleteButton = false, disabledButton = false)
public class OrderAfterSale {

  @StarField(caption = "主键", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long id;
  
  @StarField(caption = "订单ID", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long orderId;
  
  @StarField(caption = "售后单号", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long afterCode;          //售后单号
  
  @StarField(caption = "openid", dsType = DsType.VARCHAR, dsLength = 32)
  @StarFieldList(inputType = InputType.text)
  private String openId;           //微信唯一标识
  
  @StarField(caption = "分销商姓名", dsType = DsType.VARCHAR, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private String name;
  
  @StarField(caption = "分销商手机号", dsType = DsType.VARCHAR, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String mobile;
  
  @StarField(caption = "总金额", dsType = DsType.INT, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private Integer totalMoney;      //订单总金额
  
  @StarField(caption = "申请备注", dsType = DsType.VARCHAR, dsLength = 500)
  @StarFieldList(inputType = InputType.text)
  private String remark;           //申请备注
  
  @StarField(caption = "售后状态", dsType = DsType.TINYINT, dsLength = 1, enumName = "OrderStateEnum", enumOptTypes = "{\"state\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"pending\": {\"state\": 1, \"caption\": \"待处理\"}, \"pass\": {\"state\": 2, \"caption\": \"通过\"}, \"nopass\": {\"state\": 3, \"caption\": \"未通过\"}, \"cancel\": {\"state\": 4, \"caption\": \"已取消\"}, \"delete\": {\"state\": 5, \"caption\": \"已删除\"}}")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:待处理;2:通过;3:不通过;4:已取消;5:已删除'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"待处理\",\"2\":\"通过\",\"3\":\"不通过\",\"4\":\"已取消\",\"5\":\"已删除\"}")
  private Integer state;           //状态：1待处理 2通过 3不通过 4已取消 5已删除
  
  @StarField(caption = "openid", dsType = DsType.VARCHAR, dsLength = 32)
  private String reason;           //审核结果描述
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d H:i:s")
  private Date createTime;
}
