/**create by framework at 2018年11月07日 09:43:30**/
package com.star.truffle.module.product.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.product.domain.Category;

@Getter
@Setter
public class CategoryRequestDto extends Category {

  private Page pager;
}