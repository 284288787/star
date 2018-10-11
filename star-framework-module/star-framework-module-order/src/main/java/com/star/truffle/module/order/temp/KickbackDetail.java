/**create by liuhua at 2018年10月11日 上午9:57:30**/
package com.star.truffle.module.order.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "提成明细", createTable = true, tableName = "kickback_detail", addPage = false, editPage = false)
public class KickbackDetail {

  @StarField(caption = "主键", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long id;
  
  @StarField(caption = "分销商", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text, hidden = true, substituteName = "distributorName")
  @StarFieldQuery(inputType = InputType.text, hidden = true, substituteName = "distributorName")
  private Long distributorId;       // 分销商id
  
  @StarField(caption = "日期节点", dsType = DsType.DATETIME, dsLength = 0)
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date pointTime;           // 提成计算日期节点
  
  @StarField(caption = "总金额", dsType = DsType.INT, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  private Integer totalMoney;       // 可提成总金额
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d H:i:s")
  private Date createTime;          // 提成计算时间
  
  @StarField(caption = "订单状态", dsType = DsType.TINYINT, dsLength = 1, enumName = "KickbackStateEnum", enumOptTypes = "{\"state\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"auditing\": {\"state\": 1, \"caption\": \"审核中\"}, \"remittance\": {\"state\": 2, \"caption\": \"汇款中\"}, \"nopass\": {\"state\": 3, \"caption\": \"未通过\"}, \"finish\": {\"state\": 4, \"caption\": \"已完成\"}}")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:审核中;2:汇款中;3:未通过;4:已完成'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"审核中\",\"2\":\"汇款中\",\"3\":\"未通过\",\"4\":\"已完成\"}")
  private Integer state;            // 状态： 1审核中 2汇款中 3未通过 4已完成
  
  @StarField(caption = "未通过原因", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldList(inputType = InputType.text)
  private String reject;            // 未通过的原因
}
