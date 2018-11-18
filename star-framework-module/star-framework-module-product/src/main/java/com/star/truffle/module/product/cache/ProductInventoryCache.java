/**create by framework at 2018年09月04日 10:28:05**/
package com.star.truffle.module.product.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.star.truffle.module.product.dao.read.ProductInventoryReadDao;
import com.star.truffle.module.product.dao.write.ProductInventoryWriteDao;
import com.star.truffle.module.product.domain.ProductInventory;

@Service
public class ProductInventoryCache {

  @Autowired
  private ProductInventoryWriteDao productInventoryWriteDao;
  @Autowired
  private ProductInventoryReadDao productInventoryReadDao;

  @CachePut(value = "module-product-productInventory", key = "'productInventory_productId_'+#productInventory.productId+'_type_'+#productInventory.type", condition = "#productInventory != null")
  public ProductInventory saveProductInventory(ProductInventory productInventory){
    this.productInventoryWriteDao.saveProductInventory(productInventory);
    productInventory = this.productInventoryWriteDao.getProductInventoryByProductId(productInventory.getProductId(), productInventory.getType());
    return productInventory;
  }

  @CacheEvict(value = "module-product-productInventory", key = "'productInventory_productId_'+#productId+'_type_'+#type", condition = "#productId != null")
  public int deleteProductInventoryByProductId(Long productId, int type){
    return this.productInventoryWriteDao.deleteProductInventoryByProductId(productId, type);
  }

  @Cacheable(value = "module-product-productInventory", key = "'productInventory_productId_'+#productId+'_type_'+#type", condition = "#productId != null")
  public ProductInventory getProductInventory(Long productId, int type){
    ProductInventory productInventory = this.productInventoryReadDao.getProductInventoryByProductId(productId, type);
    return productInventory;
  }

  @CachePut(value = "module-product-productInventory", key = "'productInventory_productId_'+#productInventory.productId+'_type_'+#productInventory.type", condition = "#productInventory != null")
  public ProductInventory updateProductInventory(ProductInventory productInventory) {
    this.productInventoryWriteDao.updateProductInventory(productInventory);
    ProductInventory pi = this.productInventoryReadDao.getProductInventoryByProductId(productInventory.getProductId(), productInventory.getType());
    return pi;
  }
}