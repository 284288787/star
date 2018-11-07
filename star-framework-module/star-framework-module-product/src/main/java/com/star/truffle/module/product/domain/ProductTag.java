/**create by framework at 2018年11月07日 14:22:22**/
package com.star.truffle.module.product.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ProductTag {

  // 标签ID
  private Long tagId;
  // 标签名称
  private String tagName;
  // 标签类型
  private Integer type;
  // 创建日期
  private Date createTime;
}