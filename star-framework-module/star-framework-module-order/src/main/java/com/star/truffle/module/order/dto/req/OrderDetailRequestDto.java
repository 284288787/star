/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.req;

import java.util.Date;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.OrderDetail;

@Getter
@Setter
public class OrderDetailRequestDto extends OrderDetail {

  private Page pager;

  private String states;
  private String transportStates;
  private Date beginCreateTime;
  private Date endCreateTime;
  private Long distributorId;
  // 订单编号
  private Long orderCode;
  // 订单类型 1自主下单 2代客下单
  private Integer type;
  // 分销区域
  private Long regionId;
  // 店铺名称
  private String shopName;
  // 店铺电话
  private String shopMobile;
  // 用户手机号
  private String mobile;
  // 收货类型 1自提 2快递
  private Integer deliveryType;
  // 提货码
  private String pickupCode;
  // 快递单号
  private String expressNumber;
}