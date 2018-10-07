/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryAddress {

  // 主键
  private Long id;
  // 用户ID
  private Long memberId;
  // 收件人
  private String name;
  // 收件人电话
  private String mobile;
  // 省
  private Long provinceId;
  // 市
  private Long cityId;
  // 区县
  private Long areaId;
  // 省
  private String provinceName;
  // 市
  private String cityName;
  // 区县
  private String areaName;
  // 详细地址
  private String address;
  // 是否默认
  private Integer def;
}