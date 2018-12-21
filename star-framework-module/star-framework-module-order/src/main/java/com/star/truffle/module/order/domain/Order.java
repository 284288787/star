/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

  // 主键
  private Long orderId;
  // 订单编号
  private String orderCode;
  // 订单类型 1自主下单 2代客下单
  private Integer type;
  // 用户ID
  private Long memberId;
  // openid
  private String openId;
  // 用户姓名
  private String name;
  // 用户手机号
  private String mobile;
  // 收货类型 1自提 2快递
  private Integer deliveryType;
  // 收货地址-省
  private String provinceName;
  // 收货地址-市
  private String cityName;
  // 收货地址-区县
  private String areaName;
  // 收货地址
  private String deliveryAddress;
  // 收件人
  private String deliveryName;
  // 收件人手机
  private String deliveryMobile;
  // 快递费 单位分
  private Integer despatchMoney; 
  // 总金额 不包含运费
  private Integer totalMoney;
  // 二级分销商总提成
  private Integer totalBrokerage;
  // 一级分销商总提成
  private Integer totalBrokerageFirst;
  // 分销区域
  private Long regionId;
  // 分销商
  private Long distributorId;
  // 自提地址
  private String shopAddress;
  // 店铺名称
  private String shopName;
  // 店铺电话
  private String shopMobile;
  // 订单状态 1待付款 2待提货 3已提货 4已退货
  private Integer state;
  // 运输状态 1待发货 2已发货 3已完成
  private Integer transportState;
  // 是否删除 1是 0否
  private Integer deleted;
  // 提货码
  private String pickupCode;
  // 创建日期
  private Date createTime;
  // 1用户自己 2系统 3后台
  private Integer deleteUser;
  // 备注
  private String remark;
  // 快递单号
  private String expressNumber;
  // 含税价的利润
  private Integer profitHan;
  // 未税价的利润
  private Integer profitWei;
  // 优惠金额
  private Integer discountedPrice;
  // 退款商品的二级分销提成总和
  private Integer backBrokerage;
  // 退款商品的一级分销提成总和
  private Integer backBrokerageFirst;
}