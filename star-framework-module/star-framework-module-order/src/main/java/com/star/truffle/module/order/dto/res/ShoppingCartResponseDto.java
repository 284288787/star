/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.star.truffle.module.order.domain.ShoppingCart;

@Getter
@Setter
public class ShoppingCartResponseDto extends ShoppingCart {

  // 商品状态 1上架 2预售 3售罄 4下架 5禁用 6删除
  private Integer state;
  // 商品标题
  private String title;
  // 商品原价
  private Integer originalPrice;
  // 商品售价
  private Integer price;
  // 商品规格 例如：1袋
  private String specification;
  
  private String mainPictureUrl;
  
  private Date pickupTime;
}