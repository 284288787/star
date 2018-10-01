/**create by liuhua at 2018年10月1日 下午5:34:36**/
package com.star.truffle.module.order.dto.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnterOrder {

  // 邮费
  private Integer despatchMoney;
  // 商品列表
  private List<ShoppingCartResponseDto> products;
  // 默认收获地址
  private DeliveryAddressResponseDto deliveryAddress;
}
