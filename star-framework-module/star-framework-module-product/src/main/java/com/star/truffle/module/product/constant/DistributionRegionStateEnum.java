/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.constant;

public enum DistributionRegionStateEnum { 

  enabled(1, "有效"), disabled(2, "禁用"), deleted(3, "删除");

  private int state;
  private String caption;

  private DistributionRegionStateEnum(int state, String caption) {
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