/**create by framework at 2019年03月07日 11:36:40**/
package com.star.truffle.module.product.dao.write;

import java.util.List;
import com.star.truffle.module.product.domain.CategoryProdcutRelation;
import com.star.truffle.module.product.dto.req.CategoryProdcutRelationRequestDto;
import com.star.truffle.module.product.dto.res.CategoryProdcutRelationResponseDto;

public interface CategoryProdcutRelationWriteDao {

  public int saveCategoryProdcutRelation(CategoryProdcutRelation categoryProdcutRelation);

  public int batchSaveCategoryProdcutRelation(List<CategoryProdcutRelation> categoryProdcutRelations);

  public int updateCategoryProdcutRelation(CategoryProdcutRelationRequestDto categoryProdcutRelationDto);

  public int deleteCategoryProdcutRelation(Long id);

  public CategoryProdcutRelationResponseDto getCategoryProdcutRelation(Long id);

  public int deleteCategoryProdcutRelationByProductId(Long productId);

}