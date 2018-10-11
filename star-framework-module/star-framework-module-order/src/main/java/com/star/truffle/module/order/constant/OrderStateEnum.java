/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.constant;

public enum OrderStateEnum { 

  nopay(1, "待付款"), nosend(2, "待提货"), success(3, "已提货"), back(4, "已退货");

  private int state;
  private String caption;

  private OrderStateEnum(int state, String caption) {
    this.state = state;
    this.caption = caption;
  }

  public int state() {
    return this.state;
  }

  public String caption() {
    return this.caption;
  }

}