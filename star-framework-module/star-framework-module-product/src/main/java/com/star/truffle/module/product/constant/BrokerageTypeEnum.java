/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.constant;

public enum BrokerageTypeEnum { 

  money(1, "固定金额"), percent(2, "售价百分比");

  private int type;
  private String caption;

  private BrokerageTypeEnum(int type, String caption) {
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