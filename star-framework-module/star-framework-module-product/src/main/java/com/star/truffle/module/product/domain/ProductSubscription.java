/**create by framework at 2018年09月18日 10:59:57**/
package com.star.truffle.module.product.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ProductSubscription {

  // ID
  private Long id;
  // 供应ID
  private Long productId;
  // 用户ID
  private Long memberId;
  // 创建日期
  private Date createTime;
}