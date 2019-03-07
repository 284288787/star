/**create by framework at 2019年03月07日 10:03:37**/
package com.star.truffle.module.product.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.product.dao.read.ProductCategoryReadDao;
import com.star.truffle.module.product.dao.write.ProductCategoryWriteDao;
import com.star.truffle.module.product.domain.ProductCategory;
import com.star.truffle.module.product.dto.req.ProductCategoryRequestDto;
import com.star.truffle.module.product.dto.res.ProductCategoryResponseDto;

@Service
public class ProductCategoryCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private ProductCategoryWriteDao productCategoryWriteDao;
  @Autowired
  private ProductCategoryReadDao productCategoryReadDao;

  @CachePut(value = "module-product-productCategory", key = "'productCategory_productCateId_'+#result.productCateId", condition = "#result != null and #result.productCateId != null")
  public ProductCategoryResponseDto saveProductCategory(ProductCategory productCategory){
    this.productCategoryWriteDao.saveProductCategory(productCategory);
    ProductCategoryResponseDto productCategoryResponseDto = this.productCategoryWriteDao.getProductCategory(productCategory.getProductCateId());
    return productCategoryResponseDto;
  }

  @CachePut(value = "module-product-productCategory", key = "'productCategory_productCateId_'+#result.productCateId", condition = "#result != null and #result.productCateId != null")
  public ProductCategoryResponseDto updateProductCategory(ProductCategoryRequestDto productCategoryRequestDto){
    this.productCategoryWriteDao.updateProductCategory(productCategoryRequestDto);
    ProductCategoryResponseDto productCategoryResponseDto = this.productCategoryWriteDao.getProductCategory(productCategoryRequestDto.getProductCateId());
    return productCategoryResponseDto;
  }

  @CacheEvict(value = "module-product-productCategory", key = "'productCategory_productCateId_'+#productCateId", condition = "#productCateId != null")
  public int deleteProductCategory(Long productCateId){
    return this.productCategoryWriteDao.deleteProductCategory(productCateId);
  }

  @Cacheable(value = "module-product-productCategory", key = "'productCategory_productCateId_'+#productCateId", condition = "#productCateId != null")
  public ProductCategoryResponseDto getProductCategory(Long productCateId){
    ProductCategoryResponseDto productCategoryResponseDto = this.productCategoryReadDao.getProductCategory(productCateId);
    return productCategoryResponseDto;
  }

  public List<ProductCategoryResponseDto> queryProductCategory(ProductCategoryRequestDto productCategoryRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(productCategoryRequestDto);
    return this.productCategoryReadDao.queryProductCategory(conditions);
  }

  public Long queryProductCategoryCount(ProductCategoryRequestDto productCategoryRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(productCategoryRequestDto);
    return this.productCategoryReadDao.queryProductCategoryCount(conditions);
  }

}