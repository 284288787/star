/**create by liuhua at 2018年9月3日 下午2:33:06**/
package com.star.truffle.module.order.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "订单", createTable = true, tableName = "order", addPage = false, editPage = false)
public class Order {

  @StarField(caption = "主键", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long orderId;
  
  @StarField(caption = "订单编号", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long orderCode;            //订单编号
  
  @StarField(caption = "订单类型", dsType = DsType.TINYINT, dsLength = 1, enumName = "OrderTypeEnum", enumOptTypes = "{\"type\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"self\": {\"type\": 1, \"caption\": \"自主下单\"}, \"behalf\": {\"type\": 2, \"caption\": \"代客下单\"}}")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:自主下单;2:代客下单'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"自主下单\",\"2\":\"代客下单\"}")
  private Integer type;              //订单类型：1自主下单 2代客下单
  
  @StarField(caption = "用户ID", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private Long memberId;             //消费者id,如果带客下单可为空
  
  @StarField(caption = "openid", dsType = DsType.VARCHAR, dsLength = 32)
  @StarFieldList(inputType = InputType.text)
  private String openId;             //微信唯一标识
  
  @StarField(caption = "用户姓名", dsType = DsType.VARCHAR, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String name;
  
  @StarField(caption = "用户手机号", dsType = DsType.VARCHAR, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String mobile;
  
  @StarField(caption = "收货类型", dsType = DsType.TINYINT, dsLength = 1, enumName = "DeliveryTypeEnum", enumOptTypes = "{\"type\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"self\": {\"type\": 1, \"caption\": \"自提\"}, \"express\": {\"type\": 2, \"caption\": \"快递\"}}")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:自提;2:快递'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"自提\",\"2\":\"快递\"}")
  private Integer deliveryType;      //收货类型 1自提 2快递
  
  @StarField(caption = "收货地址-省", dsType = DsType.BIGINT, dsLength = 20)
  private Long provinceId;
  
  @StarField(caption = "收货地址-市", dsType = DsType.BIGINT, dsLength = 20)
  private Long cityId;
  
  @StarField(caption = "收货地址-区县", dsType = DsType.BIGINT, dsLength = 20)
  private Long areaId;
  
  @StarField(caption = "收货地址", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldList(inputType = InputType.text)
  private String deliveryAddress;    //收货地址 快递必填
  
  @StarField(caption = "收件人", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldList(inputType = InputType.text)
  private String deliveryName;
  
  @StarField(caption = "收件人手机", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldList(inputType = InputType.text)
  private String deliveryMobile;
  
  @StarField(caption = "总金额", dsType = DsType.INT, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private Integer totalMoney;        //订单总金额
  
  @StarField(caption = "总提成", dsType = DsType.INT, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private Integer totalBrokerage;    //订单总提成
  
  @StarField(caption = "分销区域", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text, hidden = true, substituteName = "regionName")
  @StarFieldQuery(inputType = InputType.text, hidden = true, substituteName = "regionName")
  private Long regionId;             //分销区域id
  
  @StarField(caption = "分销商", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text, hidden = true, substituteName = "distributorName")
  @StarFieldQuery(inputType = InputType.text, hidden = true, substituteName = "distributorName")
  private Long distributorId;        //分销商id
  
  @StarField(caption = "自提地址", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldList(inputType = InputType.text)
  private String shopAddress;        //提货地址
  
  @StarField(caption = "店铺名称", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String shopName;           //店铺名称
  
  @StarField(caption = "店铺电话", dsType = DsType.VARCHAR, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String shopMobile;         //店铺电话
  
  @StarField(caption = "订单状态", dsType = DsType.TINYINT, dsLength = 1, enumName = "OrderStateEnum", enumOptTypes = "{\"state\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"nopay\": {\"state\": 1, \"caption\": \"待付款\"}, \"nosend\": {\"state\": 2, \"caption\": \"待提货\"}, \"success\": {\"state\": 3, \"caption\": \"已提货\"}, \"back\": {\"state\": 4, \"caption\": \"已退货\"}, \"delete\": {\"state\": 5, \"caption\": \"已删除\"}}")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:待付款;2:待提货;3:已提货;4:已退货;5:已删除'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"待付款\",\"2\":\"待提货\",\"3\":\"已提货\",\"4\":\"已退货\",\"5\":\"已删除\"}")
  private Integer state;             //状态：1待付款 2待提货 3已提货 4已退货 5已删除
  
  @StarField(caption = "提货码", dsType = DsType.VARCHAR, dsLength = 5)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String pickupCode;         //提货码
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d H:i:s")
  private Date createTime;           //下单时间
}
