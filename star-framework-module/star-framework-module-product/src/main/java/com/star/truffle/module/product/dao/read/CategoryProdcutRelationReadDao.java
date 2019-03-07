/**create by framework at 2019年03月07日 11:36:40**/
package com.star.truffle.module.product.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.product.dto.res.CategoryProdcutRelationResponseDto;

public interface CategoryProdcutRelationReadDao {

  public CategoryProdcutRelationResponseDto getCategoryProdcutRelation(Long id);

  public List<CategoryProdcutRelationResponseDto> queryCategoryProdcutRelation(Map<String, Object> conditions);

  public Long queryCategoryProdcutRelationCount(Map<String, Object> conditions);

}