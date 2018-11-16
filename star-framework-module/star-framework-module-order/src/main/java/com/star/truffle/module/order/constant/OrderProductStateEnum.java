/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.constant;

public enum OrderProductStateEnum { 

  ready(1, "待发货"), sending(2, "已发货"), finish(3, "已完成");

  private int state;
  private String caption;

  private OrderProductStateEnum(int state, String caption) {
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