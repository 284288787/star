/**create by framework at 2019年03月07日 11:36:40**/
package com.star.truffle.module.product.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.product.cache.CategoryProdcutRelationCache;
import com.star.truffle.module.product.domain.CategoryProdcutRelation;
import com.star.truffle.module.product.dto.req.CategoryProdcutRelationRequestDto;
import com.star.truffle.module.product.dto.res.CategoryProdcutRelationResponseDto;

@Service
public class CategoryProdcutRelationService {

  @Autowired
  private CategoryProdcutRelationCache categoryProdcutRelationCache;

  public Long saveCategoryProdcutRelation(CategoryProdcutRelation categoryProdcutRelation) {
    this.categoryProdcutRelationCache.saveCategoryProdcutRelation(categoryProdcutRelation);
    return categoryProdcutRelation.getId();
  }

  public void updateCategoryProdcutRelation(CategoryProdcutRelationRequestDto categoryProdcutRelationRequestDto) {
    this.categoryProdcutRelationCache.updateCategoryProdcutRelation(categoryProdcutRelationRequestDto);
  }

  public void deleteCategoryProdcutRelation(Long id) {
    this.categoryProdcutRelationCache.deleteCategoryProdcutRelation(id);
  }

  public void deleteCategoryProdcutRelation(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      this.categoryProdcutRelationCache.deleteCategoryProdcutRelation(id);
    }
  }

  public CategoryProdcutRelationResponseDto getCategoryProdcutRelation(Long id) {
    CategoryProdcutRelationResponseDto categoryProdcutRelationResponseDto = this.categoryProdcutRelationCache.getCategoryProdcutRelation(id);
    return categoryProdcutRelationResponseDto;
  }

  public List<CategoryProdcutRelationResponseDto> queryCategoryProdcutRelation(CategoryProdcutRelationRequestDto categoryProdcutRelationRequestDto) {
    return this.categoryProdcutRelationCache.queryCategoryProdcutRelation(categoryProdcutRelationRequestDto);
  }

  public Long queryCategoryProdcutRelationCount(CategoryProdcutRelationRequestDto categoryProdcutRelationRequestDto) {
    return this.categoryProdcutRelationCache.queryCategoryProdcutRelationCount(categoryProdcutRelationRequestDto);
  }

}