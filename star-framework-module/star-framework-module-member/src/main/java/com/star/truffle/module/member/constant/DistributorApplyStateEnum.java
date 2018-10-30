/**create by liuhua at 2018年10月30日 上午10:00:13**/
package com.star.truffle.module.member.constant;

public enum DistributorApplyStateEnum {

  normal(1, "待审核"), pass(2, "通过"), nopass(3, "不通过"), deleted(4, "删除");
  
  private int state;
  private String caption;
  
  private DistributorApplyStateEnum(int state, String caption) {
    this.state = state;
    this.caption = caption;
  }
  
  public int getState() {
    return this.state;
  }
  
  public String getCaption() {
    return this.caption;
  }
}
