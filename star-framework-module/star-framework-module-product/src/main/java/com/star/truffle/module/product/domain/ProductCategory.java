/**create by framework at 2019年03月07日 10:03:37**/
package com.star.truffle.module.product.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ProductCategory {

  // 分类ID
  private Long productCateId;
  // 分类名称
  private String productCateName;
  // 创建日期
  private Date createTime;
}