/**create by framework at 2018年11月07日 09:43:30**/
package com.star.truffle.module.product.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Category {

  // 分类ID
  private Long cateId;
  // 分类名称
  private String cateName;
  // 创建日期
  private Date createTime;
}