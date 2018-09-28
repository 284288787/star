/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.constant;

public enum OrderTypeEnum { 

  self(1, "自主下单"), behalf(2, "代客下单");

  private int type;
  private String caption;

  private OrderTypeEnum(int type, String caption) {
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