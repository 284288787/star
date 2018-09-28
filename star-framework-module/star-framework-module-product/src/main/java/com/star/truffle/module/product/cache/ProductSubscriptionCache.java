/**create by framework at 2018年09月18日 10:59:57**/
package com.star.truffle.module.product.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.product.dao.read.ProductSubscriptionReadDao;
import com.star.truffle.module.product.dao.write.ProductSubscriptionWriteDao;
import com.star.truffle.module.product.domain.ProductSubscription;
import com.star.truffle.module.product.dto.req.ProductSubscriptionRequestDto;
import com.star.truffle.module.product.dto.res.ProductSubscriptionResponseDto;

@Service
public class ProductSubscriptionCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private ProductSubscriptionWriteDao productSubscriptionWriteDao;
  @Autowired
  private ProductSubscriptionReadDao productSubscriptionReadDao;

  @CachePut(value = "module-product-productSubscription", key = "'productSubscription_id_'+#result.id", condition = "#result != null and #result.id != null")
  public ProductSubscriptionResponseDto saveProductSubscription(ProductSubscription productSubscription){
    this.productSubscriptionWriteDao.saveProductSubscription(productSubscription);
    ProductSubscriptionResponseDto productSubscriptionResponseDto = this.productSubscriptionWriteDao.getProductSubscription(productSubscription.getId());
    return productSubscriptionResponseDto;
  }

  @CachePut(value = "module-product-productSubscription", key = "'productSubscription_id_'+#result.id", condition = "#result != null and #result.id != null")
  public ProductSubscriptionResponseDto updateProductSubscription(ProductSubscriptionRequestDto productSubscriptionRequestDto){
    this.productSubscriptionWriteDao.updateProductSubscription(productSubscriptionRequestDto);
    ProductSubscriptionResponseDto productSubscriptionResponseDto = this.productSubscriptionWriteDao.getProductSubscription(productSubscriptionRequestDto.getId());
    return productSubscriptionResponseDto;
  }

  @CacheEvict(value = "module-product-productSubscription", key = "'productSubscription_id_'+#id", condition = "#id != null")
  public int deleteProductSubscription(Long id){
    return this.productSubscriptionWriteDao.deleteProductSubscription(id);
  }

  @Cacheable(value = "module-product-productSubscription", key = "'productSubscription_id_'+#id", condition = "#id != null")
  public ProductSubscriptionResponseDto getProductSubscription(Long id){
    ProductSubscriptionResponseDto productSubscriptionResponseDto = this.productSubscriptionReadDao.getProductSubscription(id);
    return productSubscriptionResponseDto;
  }

  public List<ProductSubscriptionResponseDto> queryProductSubscription(ProductSubscriptionRequestDto productSubscriptionRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(productSubscriptionRequestDto);
    return this.productSubscriptionReadDao.queryProductSubscription(conditions);
  }

  public Long queryProductSubscriptionCount(ProductSubscriptionRequestDto productSubscriptionRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(productSubscriptionRequestDto);
    return this.productSubscriptionReadDao.queryProductSubscriptionCount(conditions);
  }

}