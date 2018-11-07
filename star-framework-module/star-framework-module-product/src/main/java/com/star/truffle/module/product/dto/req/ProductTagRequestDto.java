/**create by framework at 2018年11月07日 14:22:22**/
package com.star.truffle.module.product.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.product.domain.ProductTag;

@Getter
@Setter
public class ProductTagRequestDto extends ProductTag {

  private Page pager;
}