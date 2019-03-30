/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.weixin.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRelation {

  // ID
  private Long id;
  // 业务类型
  private Integer businessType;
  // 业务ID
  private Long businessId;
  // 卡券ID
  private Long couponId;
  // 是否删除
  private Integer deleted;
}