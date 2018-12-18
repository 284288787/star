/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.constant;

public enum AfterSaleEnum { 

  pending(1, "待处理"), pass(2, "通过"), nopass(3, "未通过"),
  send(4, "已寄件"), doing(5, "处理中"), finish(6, "完成"), 
  cancel(7, "已取消"), delete(8, "已删除");

  private int state;
  private String caption;

  private AfterSaleEnum(int state, String caption) {
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