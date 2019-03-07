/**create by framework at 2019年03月07日 11:36:40**/
package com.star.truffle.module.product.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.product.domain.CategoryProdcutRelation;

@Getter
@Setter
public class CategoryProdcutRelationRequestDto extends CategoryProdcutRelation {

  private Page pager;
}