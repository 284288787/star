/**create by framework at 2019年03月07日 11:36:40**/
package com.star.truffle.module.product.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.product.dao.read.CategoryProdcutRelationReadDao;
import com.star.truffle.module.product.dao.write.CategoryProdcutRelationWriteDao;
import com.star.truffle.module.product.domain.CategoryProdcutRelation;
import com.star.truffle.module.product.dto.req.CategoryProdcutRelationRequestDto;
import com.star.truffle.module.product.dto.res.CategoryProdcutRelationResponseDto;

@Service
public class CategoryProdcutRelationCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private CategoryProdcutRelationWriteDao categoryProdcutRelationWriteDao;
  @Autowired
  private CategoryProdcutRelationReadDao categoryProdcutRelationReadDao;

  public CategoryProdcutRelationResponseDto saveCategoryProdcutRelation(CategoryProdcutRelation categoryProdcutRelation){
    this.categoryProdcutRelationWriteDao.saveCategoryProdcutRelation(categoryProdcutRelation);
    CategoryProdcutRelationResponseDto categoryProdcutRelationResponseDto = this.categoryProdcutRelationWriteDao.getCategoryProdcutRelation(categoryProdcutRelation.getId());
    return categoryProdcutRelationResponseDto;
  }

  public CategoryProdcutRelationResponseDto updateCategoryProdcutRelation(CategoryProdcutRelationRequestDto categoryProdcutRelationRequestDto){
    this.categoryProdcutRelationWriteDao.updateCategoryProdcutRelation(categoryProdcutRelationRequestDto);
    CategoryProdcutRelationResponseDto categoryProdcutRelationResponseDto = this.categoryProdcutRelationWriteDao.getCategoryProdcutRelation(categoryProdcutRelationRequestDto.getId());
    return categoryProdcutRelationResponseDto;
  }

  public int deleteCategoryProdcutRelation(Long id){
    return this.categoryProdcutRelationWriteDao.deleteCategoryProdcutRelation(id);
  }

  public CategoryProdcutRelationResponseDto getCategoryProdcutRelation(Long id){
    CategoryProdcutRelationResponseDto categoryProdcutRelationResponseDto = this.categoryProdcutRelationReadDao.getCategoryProdcutRelation(id);
    return categoryProdcutRelationResponseDto;
  }

  public List<CategoryProdcutRelationResponseDto> queryCategoryProdcutRelation(CategoryProdcutRelationRequestDto categoryProdcutRelationRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(categoryProdcutRelationRequestDto);
    return this.categoryProdcutRelationReadDao.queryCategoryProdcutRelation(conditions);
  }

  public Long queryCategoryProdcutRelationCount(CategoryProdcutRelationRequestDto categoryProdcutRelationRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(categoryProdcutRelationRequestDto);
    return this.categoryProdcutRelationReadDao.queryCategoryProdcutRelationCount(conditions);
  }

}