/**create by framework at 2019年02月19日 14:19:41**/
package com.star.truffle.module.order.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ExpressInformation {

  // 主键
  private Long id;
  // 编号
  private String code;
  // 寄件人
  private String sender;
  // 寄件人手机号
  private String senderMobile;
  // 寄件人座机号
  private String senderTel;
  // 寄件人地址
  private String senderAddress;
  // 收件人
  private String receiver;
  // 收件人手机号
  private String receiverMobile;
  // 收件人座机号
  private String receiverTel;
  // 收件人地址
  private String receiverAddress;
  // 物品信息
  private String goodsInfo;
  // 快递公司
  private String expressCompany;
  // 快递网点
  private String pointName;
  // 快递单号
  private String trackingNumber;
  // 创建日期
  private Date createTime;
  // 状态 1正常 2不可查
  private Integer state;
}