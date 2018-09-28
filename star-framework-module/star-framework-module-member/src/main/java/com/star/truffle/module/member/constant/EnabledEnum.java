/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.constant;

public enum EnabledEnum { 

  enabled(1, "可用"), disabled(0, "禁用");

  private int val;
  private String caption;

  private EnabledEnum(int val, String caption) {
    this.val = val;
    this.caption = caption;
  }

  public int val() {
    return this.val;
  }

  public String caption() {
    return this.caption;
  }

}