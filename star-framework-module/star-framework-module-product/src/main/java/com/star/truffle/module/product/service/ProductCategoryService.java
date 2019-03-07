/**create by framework at 2019年03月07日 10:03:37**/
package com.star.truffle.module.product.service;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.product.cache.ProductCategoryCache;
import com.star.truffle.module.product.domain.ProductCategory;
import com.star.truffle.module.product.dto.req.ProductCategoryRequestDto;
import com.star.truffle.module.product.dto.res.ProductCategoryResponseDto;

@Service
public class ProductCategoryService {

  @Autowired
  private ProductCategoryCache productCategoryCache;

  public Long saveProductCategory(ProductCategory productCategory) {
    productCategory.setCreateTime(new Date());
    this.productCategoryCache.saveProductCategory(productCategory);
    return productCategory.getProductCateId();
  }

  public void updateProductCategory(ProductCategoryRequestDto productCategoryRequestDto) {
    this.productCategoryCache.updateProductCategory(productCategoryRequestDto);
  }

  public void deleteProductCategory(Long productCateId) {
    this.productCategoryCache.deleteProductCategory(productCateId);
  }

  public void deleteProductCategory(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] productCateIds = idstr.split(",");
    for (String str : productCateIds) {
      Long productCateId = Long.parseLong(str);
      this.productCategoryCache.deleteProductCategory(productCateId);
    }
  }

  public ProductCategoryResponseDto getProductCategory(Long productCateId) {
    ProductCategoryResponseDto productCategoryResponseDto = this.productCategoryCache.getProductCategory(productCateId);
    return productCategoryResponseDto;
  }

  public List<ProductCategoryResponseDto> queryProductCategory(ProductCategoryRequestDto productCategoryRequestDto) {
    return this.productCategoryCache.queryProductCategory(productCategoryRequestDto);
  }

  public Long queryProductCategoryCount(ProductCategoryRequestDto productCategoryRequestDto) {
    return this.productCategoryCache.queryProductCategoryCount(productCategoryRequestDto);
  }

}