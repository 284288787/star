/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.dto.res;

import java.util.List;

import com.star.truffle.module.product.domain.Product;
import com.star.truffle.module.product.domain.ProductPicture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto extends Product {

  private List<ProductPicture> pictures;

  private String mainPictureUrl;
//  // 商品库存
//  private ProductInventory productInventory;
  // 1不限库存 2有库存
  private Integer numberType;
  // 库存总数
  private Integer number;
  // 已售数量
  private Integer soldNumber;
  // 提成类型 1商品 2活动
  private Integer type;
  // 分类名称
  private String cateName;
  // 当前时间 毫秒
  private Long currentTimeMillis = System.currentTimeMillis();
}