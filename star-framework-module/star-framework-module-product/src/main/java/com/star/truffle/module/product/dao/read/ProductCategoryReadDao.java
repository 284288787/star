/**create by framework at 2019年03月07日 10:03:37**/
package com.star.truffle.module.product.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.product.dto.res.ProductCategoryResponseDto;

public interface ProductCategoryReadDao {

  public ProductCategoryResponseDto getProductCategory(Long productCateId);

  public List<ProductCategoryResponseDto> queryProductCategory(Map<String, Object> conditions);

  public Long queryProductCategoryCount(Map<String, Object> conditions);

}