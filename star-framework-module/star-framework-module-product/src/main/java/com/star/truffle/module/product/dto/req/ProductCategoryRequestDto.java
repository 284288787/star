/**create by framework at 2019年03月07日 10:03:37**/
package com.star.truffle.module.product.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.product.domain.ProductCategory;

@Getter
@Setter
public class ProductCategoryRequestDto extends ProductCategory {

  private Page pager;
}