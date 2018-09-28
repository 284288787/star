/**create by framework at 2018年09月04日 10:28:05**/
package com.star.truffle.module.product.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPicture {

  // id
  private Long id;
  // 商品ID
  private Long productId;
  // 图片类型 1主图 2多角图
  private Integer type;
  // 商品路径
  private String url;
}