/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ShoppingCart {

  // 主键
  private Long cartId;
  // 供应ID
  private Long productId;
  // 会员ID
  private Long memberId;
  // 数量
  private Integer num;
  // 是否选中
  private Integer checked;
  // 创建日期
  private Date createTime;
}