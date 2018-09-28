/**create by liuhua at 2018年9月14日 下午4:46:14**/
package com.star.truffle.module.product.temp;

public class ProductActivity {

  private Long id;
  private Long productId;
  private String cardId;          //卡券id
  private String cardType;        //卡类型 团购券：GROUPON; 折扣券：DISCOUNT; 礼品券：GIFT; 代金券：CASH; 通用券：GENERAL_COUPON;
  private String cardName;        //卡券名称
  private Integer getLimit;       //没人限领次数
  
  private Long provinceId;        //参加活动的省，可选
  private Long cityId;            //参加活动的市 ，可选
  private Long areaId;            //参加活动的区县，可选
  private Long townId;            //参加活动的乡镇、街道，可选
  private Long regionId;          //参加活动的店，可选
}
