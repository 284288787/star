/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.constant;

public enum DeliveryTypeEnum { 

  self(1, "自提"), express(2, "快递");

  private int type;
  private String caption;

  private DeliveryTypeEnum(int type, String caption) {
    this.type = type;
    this.caption = caption;
  }

  public int type() {
    return this.type;
  }

  public String caption() {
    return this.caption;
  }

}