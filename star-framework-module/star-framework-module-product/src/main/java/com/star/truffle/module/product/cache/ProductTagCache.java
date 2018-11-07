/**create by framework at 2018年11月07日 14:22:22**/
package com.star.truffle.module.product.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.product.dao.read.ProductTagReadDao;
import com.star.truffle.module.product.dao.write.ProductTagWriteDao;
import com.star.truffle.module.product.domain.ProductTag;
import com.star.truffle.module.product.dto.req.ProductTagRequestDto;
import com.star.truffle.module.product.dto.res.ProductTagResponseDto;

@Service
public class ProductTagCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private ProductTagWriteDao productTagWriteDao;
  @Autowired
  private ProductTagReadDao productTagReadDao;

  @CachePut(value = "module-product-productTag", key = "'productTag_tagId_'+#result.tagId", condition = "#result != null and #result.tagId != null")
  public ProductTagResponseDto saveProductTag(ProductTag productTag){
    this.productTagWriteDao.saveProductTag(productTag);
    ProductTagResponseDto productTagResponseDto = this.productTagWriteDao.getProductTag(productTag.getTagId());
    return productTagResponseDto;
  }

  @CachePut(value = "module-product-productTag", key = "'productTag_tagId_'+#result.tagId", condition = "#result != null and #result.tagId != null")
  public ProductTagResponseDto updateProductTag(ProductTagRequestDto productTagRequestDto){
    this.productTagWriteDao.updateProductTag(productTagRequestDto);
    ProductTagResponseDto productTagResponseDto = this.productTagWriteDao.getProductTag(productTagRequestDto.getTagId());
    return productTagResponseDto;
  }

  @CacheEvict(value = "module-product-productTag", key = "'productTag_tagId_'+#tagId", condition = "#tagId != null")
  public int deleteProductTag(Long tagId){
    return this.productTagWriteDao.deleteProductTag(tagId);
  }

  @Cacheable(value = "module-product-productTag", key = "'productTag_tagId_'+#tagId", condition = "#tagId != null")
  public ProductTagResponseDto getProductTag(Long tagId){
    ProductTagResponseDto productTagResponseDto = this.productTagReadDao.getProductTag(tagId);
    return productTagResponseDto;
  }

  public List<ProductTagResponseDto> queryProductTag(ProductTagRequestDto productTagRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(productTagRequestDto);
    return this.productTagReadDao.queryProductTag(conditions);
  }

  public Long queryProductTagCount(ProductTagRequestDto productTagRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(productTagRequestDto);
    return this.productTagReadDao.queryProductTagCount(conditions);
  }

}