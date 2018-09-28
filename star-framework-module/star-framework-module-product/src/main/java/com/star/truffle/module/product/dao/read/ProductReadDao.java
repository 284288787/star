/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.product.dto.res.ProductResponseDto;

public interface ProductReadDao {

  public ProductResponseDto getProduct(Long productId);

  public List<ProductResponseDto> queryProduct(Map<String, Object> conditions);

  public Long queryProductCount(Map<String, Object> conditions);

}