/**create by framework at 2019年03月07日 10:03:37**/
package com.star.truffle.module.product.dao.write;

import java.util.List;
import com.star.truffle.module.product.domain.ProductCategory;
import com.star.truffle.module.product.dto.req.ProductCategoryRequestDto;
import com.star.truffle.module.product.dto.res.ProductCategoryResponseDto;

public interface ProductCategoryWriteDao {

  public int saveProductCategory(ProductCategory productCategory);

  public int batchSaveProductCategory(List<ProductCategory> productCategorys);

  public int updateProductCategory(ProductCategoryRequestDto productCategoryDto);

  public int deleteProductCategory(Long productCateId);

  public ProductCategoryResponseDto getProductCategory(Long productCateId);

}