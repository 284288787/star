/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.constant;

public enum AfterSaleTypeEnum { 

  back(1, "退货"), exchange(2, "换货");

  private int type;
  private String caption;

  private AfterSaleTypeEnum(int type, String caption) {
    this.type = type;
    this.caption = caption;
  }

  public int getType() {
    return this.type;
  }

  public String getCaption() {
    return this.caption;
  }

}