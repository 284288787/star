/**create by framework at 2018年09月04日 10:28:05**/
package com.star.truffle.module.product.constant;

public enum ProductPictureTypeEnum { 

  main(1, "主图"), other(2, "多角图");

  private int type;
  private String caption;

  private ProductPictureTypeEnum(int type, String caption) {
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