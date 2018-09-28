/**create by framework at 2018年09月04日 10:28:05**/
package com.star.truffle.module.product.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInventory {

  // id
  private Long id;
  // 商品ID
  private Long productId;
  // 1不限库存 2有库存
  private Integer numberType;
  // 库存总数
  private Integer number;
  // 已售数量
  private Integer soldNumber;
  // 提成类型 1商品 2活动
  private Integer type;
}