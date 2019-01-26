/**create by liuhua at 2019年1月24日 上午11:47:45**/
package com.star.truffle.module.weixin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CardReq {

  private String cardType;
  private Groupon groupon;               //团购券
  private Cash cash;                     //代金券
  private Discount discount;             //折扣券
  private Gift gift;                     //兑换券
  private GeneralCoupon generalCoupon;   //优惠券
}
