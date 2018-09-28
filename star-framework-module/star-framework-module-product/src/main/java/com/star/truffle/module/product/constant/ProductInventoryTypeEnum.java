/**create by framework at 2018年09月04日 10:28:05**/
package com.star.truffle.module.product.constant;

public enum ProductInventoryTypeEnum { 

  product(1, "商品"), active(2, "活动");

  private int type;
  private String caption;

  private ProductInventoryTypeEnum(int type, String caption) {
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