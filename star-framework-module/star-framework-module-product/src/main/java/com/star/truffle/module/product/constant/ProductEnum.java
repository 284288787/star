/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.constant;

public enum ProductEnum { 

  onshelf(1, "上架"), presell(2, "预售"), sellout(3, "售罄"), offshelf(4, "下架"), disabled(5, "禁用"), deleted(6, "删除");

  private int state;
  private String caption;

  private ProductEnum(int state, String caption) {
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