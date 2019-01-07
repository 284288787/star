/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.star.truffle.module.order.domain.OrderDetail;

@Getter
@Setter
public class OrderDetailResponseDto extends OrderDetail {

  private Long memberId;
  private String name;
  private String mobile;
  private String head;
  private Date createTime;
  
  private String distributorName;
  private String orderCode;
  //订单状态 1待付款 2待提货 3已提货 4已退货
  private Integer state;
  private String pickupCode;
  // 订单类型 1自主下单 2代客下单
  private Integer type;
  // 收件人
  private String deliveryName;
  // 收件人手机
  private String deliveryMobile;
  // 收货地址-省
  private String provinceName;
  // 收货地址-市
  private String cityName;
  // 收货地址-区县
  private String areaName;
  // 收货地址
  private String deliveryAddress;
  //收货类型 1自提 2快递
  private Integer deliveryType;
  //店铺名称
  private String shopName;
  // 自提地址
  private String shopAddress;
  // 店铺电话
  private String shopMobile;
  // 订单备注
  private String remark;
  // 1已经售后处理 0未售后处理
  private Integer saleafter;
}