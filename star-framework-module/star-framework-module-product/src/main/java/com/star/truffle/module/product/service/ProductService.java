/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.product.cache.ProductCache;
import com.star.truffle.module.product.cache.ProductInventoryCache;
import com.star.truffle.module.product.cache.ProductPictureCache;
import com.star.truffle.module.product.constant.ProductEnum;
import com.star.truffle.module.product.constant.ProductInventoryTypeEnum;
import com.star.truffle.module.product.constant.ProductPictureTypeEnum;
import com.star.truffle.module.product.domain.ProductInventory;
import com.star.truffle.module.product.domain.ProductPicture;
import com.star.truffle.module.product.dto.req.ProductRequestDto;
import com.star.truffle.module.product.dto.res.ProductResponseDto;

@Service
public class ProductService {

  @Autowired
  private ProductCache productCache;
  @Autowired
  private ProductInventoryCache productInventoryCache;
  @Autowired
  private ProductPictureCache productPictureCache;

  public Long saveProduct(ProductRequestDto product) {
    if (null == product || ! product.checkSaveData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    List<ProductPicture> pictures = product.getPictures();
    for (ProductPicture productPicture : pictures) {
      if (StringUtils.isBlank(productPicture.getUrl()) || null == productPicture.getType()) {
        throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "图片路径、类型不能为空");
      }
    }
    Date now = new Date();
    product.setCreateTime(now);
    product.setUpdateTime(product.getCreateTime());
    String createUser = "无登录测试";
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (null != auth) {
      createUser = auth.getName();
    }
    product.setCreateUser(createUser);
    product.setUpdateUser(createUser);
    product.setSubscribers(0);
    if (null == product.getPresellTime()) {
      product.setState(ProductEnum.onshelf.state());
    } else if (now.before(product.getPresellTime())) {    //当前时间在预售时间的前面，表示预售
      product.setState(ProductEnum.presell.state());
    } else if (null != product.getOffShelfTime() && product.getOffShelfTime().before(now)) {  //当前时间在预售时间的后面，并 当前时间在下架时间的前面， 表示在售
      product.setState(ProductEnum.onshelf.state());
      if (null != product.getProductInventory().getNumber() && product.getProductInventory().getNumber() == 0) { //库存为0 表示售罄
        product.setState(ProductEnum.sellout.state());
      }
    } else {  //当前时间在预售时间的后面，并 当前时间在下架时间的后面， 表示下架
      product.setState(ProductEnum.offshelf.state());
    }
    this.productCache.saveProduct(product);
    Long productId = product.getProductId();
    this.productPictureCache.batchSavePicture(productId, pictures);
    ProductInventory productInventory = product.getProductInventory();
    productInventory.setProductId(productId);
    productInventory.setSoldNumber(0);
    productInventory.setType(ProductInventoryTypeEnum.product.type());
    this.productInventoryCache.saveProductInventory(productInventory);
    return productId;
  }

  public void updateProduct(ProductRequestDto product) {
    if (null == product || ! product.checkUpdateData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    List<ProductPicture> pictures = product.getPictures();
    for (ProductPicture productPicture : pictures) {
      if (StringUtils.isBlank(productPicture.getUrl()) || null == productPicture.getType()) {
        throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "图片路径、类型不能为空");
      }
    }
    ProductResponseDto productResponseDto = this.productCache.getProduct(product.getProductId());
    if (null == productResponseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "供应不存在");
    }
    this.productCache.updateProduct(product);
    this.productPictureCache.deleteProductPictureByProductId(product.getProductId());
    this.productPictureCache.batchSavePicture(product.getProductId(), pictures);
    this.productInventoryCache.deleteProductInventoryByProductId(product.getProductId(), ProductInventoryTypeEnum.product.type());
    ProductInventory productInventory = product.getProductInventory();
    productInventory.setProductId(product.getProductId());
    productInventory.setSoldNumber(productResponseDto.getSoldNumber());
    productInventory.setType(ProductInventoryTypeEnum.product.type());
    this.productInventoryCache.saveProductInventory(productInventory);
  }

  public ProductResponseDto getProduct(Long productId) {
    ProductResponseDto productResponseDto = this.productCache.getProduct(productId);
    if (null != productResponseDto) {
//      ProductInventory inventory = productInventoryCache.getProductInventory(productId, ProductInventoryTypeEnum.product.type());
//      productResponseDto.setProductInventory(inventory);
      List<ProductPicture> pictures = productPictureCache.getProductPictureByProductId(productId);
      List<ProductPicture> pics = new ArrayList<>();
      if (null != pictures && ! pictures.isEmpty()) {
        for (ProductPicture productPicture : pictures) {
          if (StringUtils.isNotBlank(productPicture.getUrl())) {
            pics.add(productPicture);
            if (productPicture.getType() == ProductPictureTypeEnum.main.type()) {
              productResponseDto.setMainPictureUrl(productPicture.getUrl());
            }
          }
        }
        productResponseDto.setPictures(pics);
      }
    }
    return productResponseDto;
  }

  public List<ProductResponseDto> queryProduct(ProductRequestDto productRequestDto) {
    if (null == productRequestDto) {
      productRequestDto = new ProductRequestDto();
    }
    productRequestDto.setStates("1,2,3");
    if (null == productRequestDto.getPager()) {
      productRequestDto.setPager(new Page(1, 10, "update_time", OrderType.desc));
    }
    return this.productCache.queryProduct(productRequestDto);
  }

  public Long queryProductCount(ProductRequestDto productRequestDto) {
    if (null == productRequestDto) {
      productRequestDto = new ProductRequestDto();
    }
    productRequestDto.setStates("1,2,3");
    if (null == productRequestDto.getPager()) {
      productRequestDto.setPager(new Page(1, 10, "update_time", OrderType.desc));
    }
    return this.productCache.queryProductCount(productRequestDto);
  }

  public void deleteProduct(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] productIds = idstr.split(",");
    for (String str : productIds) {
      Long productId = Long.parseLong(str);
//      this.productCache.deleteProduct(productId);
      ProductRequestDto dto = new ProductRequestDto();
      dto.setProductId(productId);
      dto.setState(ProductEnum.deleted.state());
      this.productCache.updateProduct(dto);
    }
  }
  

  public void disabledProduct(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] productIds = idstr.split(",");
    for (String str : productIds) {
      Long productId = Long.parseLong(str);
      ProductRequestDto dto = new ProductRequestDto();
      dto.setProductId(productId);
      dto.setState(ProductEnum.disabled.state());
      this.productCache.updateProduct(dto);
    }
  }

  public void setPresell(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] productIds = idstr.split(",");
    for (String str : productIds) {
      Long productId = Long.parseLong(str);
      ProductResponseDto product = this.productCache.getProduct(productId);
      if (null != product) {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setProductId(productId);
        Date now = new Date();
        if (null == product.getPresellTime()) {
          dto.setState(ProductEnum.onshelf.state());
        } else if (now.before(product.getPresellTime())) {    //当前时间在预售时间的前面，表示预售
          dto.setState(ProductEnum.presell.state());
        } else if (null != product.getOffShelfTime() && product.getOffShelfTime().before(now)) {  //当前时间在预售时间的后面，并 当前时间在下架时间的前面， 表示在售
          dto.setState(ProductEnum.onshelf.state());
          if (null != product.getNumber() && product.getNumber() == 0) { //库存为0 表示售罄
            dto.setState(ProductEnum.sellout.state());
          }
        } else {  //当前时间在预售时间的后面，并 当前时间在下架时间的后面， 表示下架
          dto.setState(ProductEnum.offshelf.state());
        }
        this.productCache.updateProduct(dto);
      }
    }
  }

}