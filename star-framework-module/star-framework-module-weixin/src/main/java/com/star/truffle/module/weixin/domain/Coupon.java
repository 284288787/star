/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.weixin.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Coupon {

  // 卡券ID
  private Long couponId;
  // 卡券标题
  private String title;
  // 微信卡券Id
  private String cardId;
  // 创建日期
  private Date createTime;
}