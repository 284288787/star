/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.weixin.constant;

public enum CouponBusinessTypeEnum { 

  cate(1, "大分类"), product_cate(2, "商品分类"), product(3, "商品"), member(4, "会员");

  private int type;
  private String caption;

  private CouponBusinessTypeEnum(int type, String caption) {
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