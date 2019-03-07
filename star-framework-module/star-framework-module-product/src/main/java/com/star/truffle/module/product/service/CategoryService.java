/**create by framework at 2018年11月07日 09:43:30**/
package com.star.truffle.module.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.common.choosedata.ChooseDataIntf;
import com.star.truffle.common.choosedata.GridColumn;
import com.star.truffle.common.choosedata.GridPagerResponse;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.product.cache.CategoryCache;
import com.star.truffle.module.product.domain.Category;
import com.star.truffle.module.product.dto.req.CategoryRequestDto;
import com.star.truffle.module.product.dto.res.CategoryResponseDto;

@Service
public class CategoryService implements ChooseDataIntf {

  @Autowired
  private CategoryCache categoryCache;
  @Autowired
  private StarJson starJson;

  public Long saveCategory(Category category) {
    category.setCreateTime(new Date());
    this.categoryCache.saveCategory(category);
    return category.getCateId();
  }

  public void updateCategory(CategoryRequestDto categoryRequestDto) {
    this.categoryCache.updateCategory(categoryRequestDto);
  }

  public void deleteCategory(Long cateId) {
    this.categoryCache.deleteCategory(cateId);
  }

  public void deleteCategory(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] cateIds = idstr.split(",");
    for (String str : cateIds) {
      Long cateId = Long.parseLong(str);
      this.categoryCache.deleteCategory(cateId);
    }
  }

  public CategoryResponseDto getCategory(Long cateId) {
    CategoryResponseDto categoryResponseDto = this.categoryCache.getCategory(cateId);
    return categoryResponseDto;
  }

  public List<CategoryResponseDto> queryCategory(CategoryRequestDto categoryRequestDto) {
    return this.categoryCache.queryCategory(categoryRequestDto);
  }

  public Long queryCategoryCount(CategoryRequestDto categoryRequestDto) {
    return this.categoryCache.queryCategoryCount(categoryRequestDto);
  }

  @Override
  public GridPagerResponse getDatas(Map<String, Object> condition, Page pager) {
    CategoryRequestDto categoryRequestDto = starJson.str2obj(starJson.obj2string(condition), CategoryRequestDto.class);
    categoryRequestDto.setPager(pager);
    Long count = categoryCache.queryCategoryCount(categoryRequestDto);
    List<CategoryResponseDto> list = new ArrayList<>();
    if (count > 0) {
      list = categoryCache.queryCategory(categoryRequestDto);
    }
    long total = count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1;
    return new GridPagerResponse(pager.getPageNum(), total, count, list);
  }

  @Override
  public List<GridColumn> getGridColumns() {
    List<GridColumn> columns = new ArrayList<>();
    columns.add(GridColumn.builder().caption("cateId").javaName("cateId").dsName("cate_id").hidden(true).build());
    columns.add(GridColumn.builder().caption("大分类名称").javaName("cateName").dsName("cateName").query(true).type("text").placeholder("大分类名称").build());
    return columns;
  }

  @Override
  public String getPrimaryKey() {
    return "cateId";
  }

}