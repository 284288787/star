/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.product.dao.read.ProductReadDao;
import com.star.truffle.module.product.dao.write.ProductWriteDao;
import com.star.truffle.module.product.domain.Product;
import com.star.truffle.module.product.dto.req.ProductRequestDto;
import com.star.truffle.module.product.dto.res.ProductResponseDto;

@Service
public class ProductCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private ProductWriteDao productWriteDao;
  @Autowired
  private ProductReadDao productReadDao;

  @CachePut(value = "module-product-product", key = "'product_productId_'+#result.productId", condition = "#result != null and #result.productId != null")
  public ProductResponseDto saveProduct(Product product){
    this.productWriteDao.saveProduct(product);
    ProductResponseDto productResponseDto = this.productWriteDao.getProduct(product.getProductId());
    return productResponseDto;
  }

  @CachePut(value = "module-product-product", key = "'product_productId_'+#result.productId", condition = "#result != null and #result.productId != null")
  public ProductResponseDto updateProduct(ProductRequestDto productRequestDto){
    this.productWriteDao.updateProduct(productRequestDto);
    ProductResponseDto productResponseDto = this.productWriteDao.getProduct(productRequestDto.getProductId());
    return productResponseDto;
  }

  @CacheEvict(value = "module-product-product", key = "'product_productId_'+#productId", condition = "#productId != null")
  public int deleteProduct(Long productId){
    return this.productWriteDao.deleteProduct(productId);
  }

  @Cacheable(value = "module-product-product", key = "'product_productId_'+#productId", condition = "#productId != null")
  public ProductResponseDto getProduct(Long productId){
    ProductResponseDto productResponseDto = this.productReadDao.getProduct(productId);
    return productResponseDto;
  }

  public List<ProductResponseDto> queryProduct(ProductRequestDto productRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(productRequestDto);
    return this.productReadDao.queryProduct(conditions);
  }

  public Long queryProductCount(ProductRequestDto productRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(productRequestDto);
    return this.productReadDao.queryProductCount(conditions);
  }

}