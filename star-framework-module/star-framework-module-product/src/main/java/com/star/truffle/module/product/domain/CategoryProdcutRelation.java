/**create by framework at 2019年03月07日 11:36:40**/
package com.star.truffle.module.product.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryProdcutRelation {

  // 主键ID
  private Long id;
  // 大分类ID
  private Long cateId;
  // 商品ID
  private Long productId;
}