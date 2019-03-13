/**create by framework at 2018年11月07日 09:43:30**/
package com.star.truffle.module.product.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.star.truffle.module.product.domain.Category;

@Getter
@Setter
public class CategoryResponseDto extends Category {

  private List<ProductResponseDto> products;
}