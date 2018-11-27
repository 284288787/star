/**create by liuhua at 2018年11月27日 上午9:55:13**/
package com.star.truffle.module.order.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "每天统计明细", createTable = true, tableName = "distributor_total", addPage = false, editPage = false)
public class DistributorTotal {

  @StarField(caption = "主键", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long id;
  
  @StarField(caption = "分销商", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text, hidden = true, substituteName = "distributorName")
  @StarFieldQuery(inputType = InputType.text, hidden = true, substituteName = "distributorName")
  private Long distributorId;
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d H:i:s")
  private Date createTime;
  
  @StarField(caption = "当天订单数", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer orderNum;
  
  @StarField(caption = "当天已卖商品数", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer productNum;
  
  @StarField(caption = "当天支付人数", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer payPeopleNum;
  
  @StarField(caption = "当天快递人数", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer useDespatchNum;
  
  @StarField(caption = "当天自主下单人数", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer BuySelfNum;
  
  @StarField(caption = "当天总支付的商品金额", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer payMoneyOfProduct;
  
  @StarField(caption = "当天总支付的运费", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer payMoneyOfDespatch;
  
  @StarField(caption = "当天直属分销商提成", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer kickbackSecond;  //直属分销商提成
  
  @StarField(caption = "当天上级分销商提成", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer kickbackFirst;   //上级分销商提成
}
