/**create by liuhua at 2019年1月24日 下午4:31:32**/
package com.star.truffle.module.weixin.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coupon {

  private Long couponId;
  private String title;
  private String intro;
  private String cardId;
  private Date createTime;
}
