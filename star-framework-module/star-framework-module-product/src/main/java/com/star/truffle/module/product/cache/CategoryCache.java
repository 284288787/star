/**create by framework at 2018年11月07日 09:43:30**/
package com.star.truffle.module.product.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.product.dao.read.CategoryReadDao;
import com.star.truffle.module.product.dao.write.CategoryWriteDao;
import com.star.truffle.module.product.domain.Category;
import com.star.truffle.module.product.dto.req.CategoryRequestDto;
import com.star.truffle.module.product.dto.res.CategoryResponseDto;

@Service
public class CategoryCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private CategoryWriteDao categoryWriteDao;
  @Autowired
  private CategoryReadDao categoryReadDao;

  @CachePut(value = "module-product-category", key = "'category_cateId_'+#result.cateId", condition = "#result != null and #result.cateId != null")
  public CategoryResponseDto saveCategory(Category category){
    this.categoryWriteDao.saveCategory(category);
    CategoryResponseDto categoryResponseDto = this.categoryWriteDao.getCategory(category.getCateId());
    return categoryResponseDto;
  }

  @CachePut(value = "module-product-category", key = "'category_cateId_'+#result.cateId", condition = "#result != null and #result.cateId != null")
  public CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto){
    this.categoryWriteDao.updateCategory(categoryRequestDto);
    CategoryResponseDto categoryResponseDto = this.categoryWriteDao.getCategory(categoryRequestDto.getCateId());
    return categoryResponseDto;
  }

  @CacheEvict(value = "module-product-category", key = "'category_cateId_'+#cateId", condition = "#cateId != null")
  public int deleteCategory(Long cateId){
    return this.categoryWriteDao.deleteCategory(cateId);
  }

  @Cacheable(value = "module-product-category", key = "'category_cateId_'+#cateId", condition = "#cateId != null")
  public CategoryResponseDto getCategory(Long cateId){
    CategoryResponseDto categoryResponseDto = this.categoryReadDao.getCategory(cateId);
    return categoryResponseDto;
  }

  public List<CategoryResponseDto> queryCategory(CategoryRequestDto categoryRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(categoryRequestDto);
    return this.categoryReadDao.queryCategory(conditions);
  }

  public Long queryCategoryCount(CategoryRequestDto categoryRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(categoryRequestDto);
    return this.categoryReadDao.queryCategoryCount(conditions);
  }

}