/**create by liuhua at 2019年2月19日 下午2:00:19**/
package com.star.truffle.module.order.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "物流信息", createTable = true, tableName = "expressinformation", addPage = false, editPage = false)
public class ExpressInformation {

  @StarField(caption = "主键", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long id;
  @StarField(caption = "编号", dsType = DsType.VARCHAR, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String code;
  @StarField(caption = "寄件人", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String sender;
  @StarField(caption = "寄件人手机号", dsType = DsType.VARCHAR, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String senderMobile;
  @StarField(caption = "寄件人座机号", dsType = DsType.VARCHAR, dsLength = 15)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String senderTel;
  @StarField(caption = "寄件人地址", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String senderAddress;
  @StarField(caption = "收件人", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String receiver;
  @StarField(caption = "收件人手机号", dsType = DsType.VARCHAR, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String receiverMobile;
  @StarField(caption = "收件人座机号", dsType = DsType.VARCHAR, dsLength = 15)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String receiverTel;
  @StarField(caption = "收件人地址", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String receiverAddress;
  @StarField(caption = "物品信息", dsType = DsType.VARCHAR, dsLength = 100)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String goodsInfo;
  @StarField(caption = "快递公司", dsType = DsType.VARCHAR, dsLength = 60)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String expressCompany;
  @StarField(caption = "快递网点", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String pointName;
  @StarField(caption = "快递单号", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String trackingNumber;
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d H:i:s")
  private Date createTime;
}
