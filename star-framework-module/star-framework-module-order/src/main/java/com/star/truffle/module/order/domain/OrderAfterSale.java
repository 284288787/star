/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class OrderAfterSale {

  // 主键
  private Long id;
  // 订单ID
  private Long orderId;
  // 售后单号
  private Long afterCode;
  // openid
  private String openId;
  // 分销商姓名
  private String name;
  // 分销商手机号
  private String mobile;
  // 总金额
  private Integer totalMoney;
  // 申请备注
  private String remark;
  // 售后状态
  private Integer state;
  // openid
  private String reason;
  // 创建日期
  private Date createTime;
}