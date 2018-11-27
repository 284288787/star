/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class OrderDetail {

  // 主键
  private Long id;
  // 订单ID
  private Long orderId;
  // 供应ID
  private Long productId;
  // 供应标题
  private String title;
  // 供应主图
  private String mainPictureUrl;
  // 供应原价
  private Integer originalPrice;
  // 供应售价
  private Integer price;
  // 单件提成
  private Integer brokerage;
  // 单件提成
  private Integer brokerageFirst;
  // 提货时间
  private Date pickupTime;
  // 供应规格
  private String specification;
  // 购买数量
  private Integer count;
  // 供应信息
  private String productInfo;
}