/**create by liuhua at 2018年10月9日 上午11:29:44**/
package com.star.truffle.module.order.constant;

public enum DeleteUserTypeEnum {

  self(1, "自己"), system(2, "系统"), background(3, "后台");
  
  private int type;
  private String caption;
  
  private DeleteUserTypeEnum(int type, String caption) {
    this.type = type;
    this.caption = caption;
  }
  
  public int getType() {
    return type;
  }
  
  public String getCaption() {
    return caption;
  }
}
