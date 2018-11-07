/**create by framework at 2018年11月07日 09:43:30**/
package com.star.truffle.module.product.dao.write;

import java.util.List;
import com.star.truffle.module.product.domain.Category;
import com.star.truffle.module.product.dto.req.CategoryRequestDto;
import com.star.truffle.module.product.dto.res.CategoryResponseDto;

public interface CategoryWriteDao {

  public int saveCategory(Category category);

  public int batchSaveCategory(List<Category> categorys);

  public int updateCategory(CategoryRequestDto categoryDto);

  public int deleteCategory(Long cateId);

  public CategoryResponseDto getCategory(Long cateId);

}