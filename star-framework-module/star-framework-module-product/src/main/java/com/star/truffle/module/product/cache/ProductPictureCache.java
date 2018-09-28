/**create by framework at 2018年09月04日 10:28:05**/
package com.star.truffle.module.product.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.star.truffle.module.product.dao.read.ProductPictureReadDao;
import com.star.truffle.module.product.dao.write.ProductPictureWriteDao;
import com.star.truffle.module.product.domain.ProductPicture;

@Service
public class ProductPictureCache {

  @Autowired
  private ProductPictureWriteDao productPictureWriteDao;
  @Autowired
  private ProductPictureReadDao productPictureReadDao;

  @CacheEvict(value = "module-product-productPicture", key = "'productPicture_list_productId_'+#productId", condition = "#productId != null")
  public int deleteProductPictureByProductId(Long productId){
    return this.productPictureWriteDao.deleteProductPictureByProductId(productId);
  }

  @Cacheable(value = "module-product-productPicture", key = "'productPicture_list_productId_'+#productId", condition = "#productId != null")
  public List<ProductPicture> getProductPictureByProductId(Long productId){
    return productPictureReadDao.getProductPictureByProductId(productId);
  }
  
  @CachePut(value = "module-product-productPicture", key = "'productPicture_list_productId_'+#productId", condition = "#productId != null")
  public List<ProductPicture> batchSavePicture(Long productId, List<ProductPicture> pictures) {
    for (ProductPicture productPicture : pictures) {
      productPicture.setProductId(productId);
    }
    productPictureWriteDao.batchSaveProductPicture(pictures);
    return pictures;
  }

}