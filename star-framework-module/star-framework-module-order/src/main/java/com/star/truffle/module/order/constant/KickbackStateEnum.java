/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.constant;

public enum KickbackStateEnum { 

  auditing(1, "审核中"), remittance(2, "汇款中"), nopass(3, "未通过"), finish(4, "已完成");

  private int state;
  private String caption;

  private KickbackStateEnum(int state, String caption) {
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