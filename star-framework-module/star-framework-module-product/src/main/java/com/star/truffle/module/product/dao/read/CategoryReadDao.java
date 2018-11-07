/**create by framework at 2018年11月07日 09:43:30**/
package com.star.truffle.module.product.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.product.dto.res.CategoryResponseDto;

public interface CategoryReadDao {

  public CategoryResponseDto getCategory(Long cateId);

  public List<CategoryResponseDto> queryCategory(Map<String, Object> conditions);

  public Long queryCategoryCount(Map<String, Object> conditions);

}