/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.product.constant.ProductInventoryTypeEnum;
import com.star.truffle.module.product.dao.read.ProductReadDao;
import com.star.truffle.module.product.dao.write.ProductInventoryWriteDao;
import com.star.truffle.module.product.dao.write.ProductWriteDao;
import com.star.truffle.module.product.domain.ProductInventory;
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
  @Autowired
  private ProductInventoryWriteDao productInventoryWriteDao;
  
  @CachePut(value = "module-product-product", key = "'product_productId_'+#result.productId", condition = "#result != null and #result.productId != null")
  public ProductResponseDto saveProduct(ProductRequestDto product){
    this.productWriteDao.saveProduct(product);
    Long productId = product.getProductId();
    ProductInventory productInventory = product.getProductInventory();
    productInventory.setProductId(productId);
    productInventory.setSoldNumber(0);
    productInventory.setType(ProductInventoryTypeEnum.product.type());
    this.productInventoryWriteDao.saveProductInventory(productInventory);
    ProductResponseDto productResponseDto = this.productWriteDao.getProduct(productId);
    return productResponseDto;
  }

  @CachePut(value = "module-product-product", key = "'product_productId_'+#result.productId", condition = "#result != null and #result.productId != null")
  public ProductResponseDto updateProduct(ProductRequestDto productRequestDto){
    productRequestDto.setUpdateTime(new Date());
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

  public Integer getMinIdx() {
    return this.productWriteDao.getMinIdx();
  }

}