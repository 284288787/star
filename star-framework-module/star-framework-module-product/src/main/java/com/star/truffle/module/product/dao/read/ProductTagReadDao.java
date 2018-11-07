/**create by framework at 2018年11月07日 14:22:22**/
package com.star.truffle.module.product.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.product.dto.res.ProductTagResponseDto;

public interface ProductTagReadDao {

  public ProductTagResponseDto getProductTag(Long tagId);

  public List<ProductTagResponseDto> queryProductTag(Map<String, Object> conditions);

  public Long queryProductTagCount(Map<String, Object> conditions);

}