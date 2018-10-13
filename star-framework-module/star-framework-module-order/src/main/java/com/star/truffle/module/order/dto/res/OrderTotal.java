/**create by liuhua at 2018年10月13日 上午10:22:24**/
package com.star.truffle.module.order.dto.res;

import lombok.Data;

@Data
public class OrderTotal {

  private Integer idx;
  private Long distributorId;
  private Integer totalOrderNum;
  private Integer totalOrderMoney;
  private Integer totalOrderBrokerage;
  private Integer totalDespatchMoney;
  private Integer fansNum;
  private Integer soldNum;
  private String shopCode;
  private String shopName;
  private String name;
}
