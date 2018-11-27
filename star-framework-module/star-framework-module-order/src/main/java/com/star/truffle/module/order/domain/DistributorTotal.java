/**create by framework at 2018年11月27日 10:12:39**/
package com.star.truffle.module.order.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class DistributorTotal {

  // 主键
  private Long id;
  // 分销商
  private Long distributorId;
  // 订单创建日期
  private Date createTime;
  // 当天订单数
  private Integer orderNum;
  // 当天已卖商品数
  private Integer productNum;
  // 当天支付人数
  private Integer payPeopleNum;
  // 当天快递人数
  private Integer useDespatchNum;
  // 当天自提人数
  private Integer unuseDespatchNum;
  // 当天自主下单人数
  private Integer buySelfNum;
  // 当天代客下单人数
  private Integer unbuySelfNum;
  // 当天总支付的商品金额
  private Integer payMoneyOfProduct;
  // 当天总支付的运费
  private Integer payMoneyOfDespatch;
  // 当天直属分销商提成
  private Integer kickbackSecond;
  // 当天上级分销商提成
  private Integer kickbackFirst;
}