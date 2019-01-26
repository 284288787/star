/**create by liuhua at 2019年1月24日 上午10:28:34**/
package com.star.truffle.module.weixin.constant;

public enum CardTypeEnum {
  GROUPON("团购券"), CASH("代金券"), DISCOUNT("折扣券"), GIFT("兑换券"),
  GENERAL_COUPON("优惠券");
  
  private String caption;
  
  private CardTypeEnum(String caption) {
    this.caption = caption;
  }
  
  public String getCaption() {
    return caption;
  }
}
