/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.product.cache.CategoryProdcutRelationCache;
import com.star.truffle.module.product.cache.ProductCache;
import com.star.truffle.module.product.cache.ProductInventoryCache;
import com.star.truffle.module.product.cache.ProductPictureCache;
import com.star.truffle.module.product.constant.ProductEnum;
import com.star.truffle.module.product.constant.ProductInventoryTypeEnum;
import com.star.truffle.module.product.constant.ProductPictureTypeEnum;
import com.star.truffle.module.product.domain.CategoryProdcutRelation;
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
  @Autowired
  private CategoryProdcutRelationCache categoryProdcutRelationCache;

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
    if (null == product.getPresellTime() || now.after(product.getPresellTime())) {
      product.setState(ProductEnum.onshelf.state());
    } else if (now.before(product.getPresellTime())) {    //当前时间在预售时间的前面，表示预售
      product.setState(ProductEnum.presell.state());
    }
    if (null != product.getOffShelfTime() && product.getOffShelfTime().before(now)) {
      product.setState(ProductEnum.offshelf.state());
    }else if(product.getState() != ProductEnum.presell.state()) {
      product.setState(ProductEnum.onshelf.state());
    }
//    if (2 == product.getProductInventory().getNumberType() && product.getProductInventory().getNumber() == 0) { //库存为0 表示售罄
//      product.setState(ProductEnum.sellout.state());
//    }
    Integer idx = this.productCache.getMinIdx();
    product.setIdx(idx - 1);
    this.productCache.saveProduct(product);
    Long productId = product.getProductId();
    this.productPictureCache.batchSavePicture(productId, pictures);
    saveCategoryProductRelation(productId, product.getCateIds());
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
    Date now = new Date();
    if (null == product.getPresellTime() || now.after(product.getPresellTime())) {
      product.setState(ProductEnum.onshelf.state());
    } else if (now.before(product.getPresellTime())) {    //当前时间在预售时间的前面，表示预售
      product.setState(ProductEnum.presell.state());
    }
    if (null != product.getOffShelfTime() && product.getOffShelfTime().before(now)) {
      product.setState(ProductEnum.offshelf.state());
    }else if(product.getState() != ProductEnum.presell.state()) {
      product.setState(ProductEnum.onshelf.state());
    }
    if (product.getState() == ProductEnum.onshelf.state() && 2 == product.getProductInventory().getNumberType() && null != product.getProductInventory().getSoldNumber() && product.getProductInventory().getNumber() <= product.getProductInventory().getSoldNumber()) { //库存为0 表示售罄
      product.setState(ProductEnum.sellout.state());
    }
    String updateUser = "无登录测试";
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (null != auth) {
      updateUser = auth.getName();
    }
    product.setUpdateUser(updateUser);
    ProductInventory productInventory = this.productInventoryCache.getProductInventory(product.getProductId(), ProductInventoryTypeEnum.product.type());
    if (null != productInventory) {
      productInventory.setNumberType(product.getProductInventory().getNumberType());
      productInventory.setNumber(product.getProductInventory().getNumber());
      productInventory.setSoldNumber(productResponseDto.getSoldNumber());
      productInventory.setTimes(product.getProductInventory().getTimes());
      this.productInventoryCache.updateProductInventory(productInventory);
    }
    product.setPresellTimeUpdate(1);
    product.setOffShelfTimeUpdate(1);
    product.setPickupTimeUpdate(1);
    this.productCache.updateProduct(product);
    this.productPictureCache.deleteProductPictureByProductId(product.getProductId());
    this.productPictureCache.batchSavePicture(product.getProductId(), pictures);
    saveCategoryProductRelation(product.getProductId(), product.getCateIds());
  }

  private void saveCategoryProductRelation(Long productId, String cateIds) {
    if(null == productId || StringUtils.isBlank(cateIds)) {
      return ;
    }
    List<CategoryProdcutRelation> categoryProdcutRelations = 
    Stream.of(cateIds.split(",")).map(cateId -> {
      CategoryProdcutRelation relation = new CategoryProdcutRelation();
      relation.setCateId(Long.parseLong(cateId));
      relation.setProductId(productId);
      return relation;
    }).collect(Collectors.toList());
    categoryProdcutRelationCache.deleteCategoryProdcutRelationByProductId(productId);
    categoryProdcutRelationCache.batchSaveCategoryProdcutRelation(categoryProdcutRelations);
  }

  public ProductResponseDto getProduct(Long productId) {
    ProductResponseDto productResponseDto = this.productCache.getProduct(productId);
    if (null != productResponseDto) {
      ProductInventory inventory = productInventoryCache.getProductInventory(productId, ProductInventoryTypeEnum.product.type());
      productResponseDto.setNumberType(inventory.getNumberType());
      productResponseDto.setNumber(inventory.getNumber());
      productResponseDto.setSoldNumber(inventory.getSoldNumber());
      productResponseDto.setType(inventory.getType());
      productResponseDto.setTimes(inventory.getTimes());
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

  /**
   * 更新供应已卖出的数量
   * @param productId
   * @param count   本次卖出的数量
   */
  public void updateProductSoldNumber(Long productId, Integer count) {
    if (null == productId || null == count || count < 1) {
      return;
    }
    ProductInventory productInventory = this.productInventoryCache.getProductInventory(productId, ProductInventoryTypeEnum.product.type());
    if (null == productInventory) {
      return;
    }
    productInventory.setSoldNumber(productInventory.getSoldNumber() + count);
    this.productInventoryCache.updateProductInventory(productInventory);
    if (productInventory.getNumberType() == 2 && null != productInventory.getSoldNumber() && productInventory.getNumber().intValue() <= productInventory.getSoldNumber()) {
      ProductRequestDto productRequestDto = new ProductRequestDto();
      productRequestDto.setProductId(productId);
      productRequestDto.setState(ProductEnum.sellout.state());
      this.productCache.updateProduct(productRequestDto);
    }
  }
  
  public List<ProductResponseDto> queryProduct(ProductRequestDto productRequestDto) {
    if (null == productRequestDto) {
      productRequestDto = new ProductRequestDto();
    }
    if (null == productRequestDto.getPager()) {
      productRequestDto.setPager(new Page(1, 10, "update_time", OrderType.desc));
    }
//    if (null != productRequestDto.getCateId() && productRequestDto.getCateId() == 0) {
//      productRequestDto.setCateId(null);
//      productRequestDto.setState(ProductEnum.presell.state());
//    }
    return this.productCache.queryProduct(productRequestDto);
  }

  public Long queryProductCount(ProductRequestDto productRequestDto) {
    if (null == productRequestDto) {
      productRequestDto = new ProductRequestDto();
    }
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
        if (null == product.getPresellTime() || now.after(product.getPresellTime())) {
          dto.setState(ProductEnum.onshelf.state());
        } else if (now.before(product.getPresellTime())) {    //当前时间在预售时间的前面，表示预售
          dto.setState(ProductEnum.presell.state());
        }
        if (null != product.getOffShelfTime() && product.getOffShelfTime().before(now)) {
          dto.setState(ProductEnum.offshelf.state());
        }else if(product.getState() != ProductEnum.presell.state()) {
          dto.setState(ProductEnum.onshelf.state());
        }
        if (dto.getState() == ProductEnum.onshelf.state() && 2 == product.getNumberType() && product.getNumber() <= product.getSoldNumber()) { //库存为0 表示售罄
          dto.setState(ProductEnum.sellout.state());
        }
        this.productCache.updateProduct(dto);
      }
    }
  }

  public void syncProductState() {
    int pageNum = 1;
    int pageSize = 50;
    ProductRequestDto productRequestDto = new ProductRequestDto();
    productRequestDto.setStates("1,2,3,4");
    while(true) {
      productRequestDto.setPager(new Page(pageNum, pageSize, "update_time", OrderType.desc));
      List<ProductResponseDto> list = this.productCache.queryProduct(productRequestDto);
      pageNum ++;
      if (null == list || list.isEmpty()) {
        break;
      }
      for (ProductResponseDto product : list) {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setProductId(product.getProductId());
        Date now = new Date();
        if (null == product.getPresellTime() || now.after(product.getPresellTime())) {
          dto.setState(ProductEnum.onshelf.state());
        } else if (now.before(product.getPresellTime())) {    //当前时间在预售时间的前面，表示预售
          dto.setState(ProductEnum.presell.state());
        }
        if (null != product.getOffShelfTime() && product.getOffShelfTime().before(now)) {
          dto.setState(ProductEnum.offshelf.state());
        }else if(product.getState() != ProductEnum.presell.state()) {
          dto.setState(ProductEnum.onshelf.state());
        }
        if (dto.getState() == ProductEnum.onshelf.state() && 2 == product.getNumberType() && product.getNumber() <= product.getSoldNumber()) { // 
          dto.setState(ProductEnum.sellout.state());
        }
        this.productCache.updateProduct(dto);
      }
    }
  }

  public void top(Long productId) {
    Integer idx = this.productCache.getMinIdx();
    ProductRequestDto dto = new ProductRequestDto();
    dto.setProductId(productId);
    dto.setIdx(idx - 1);
    this.productCache.updateProduct(dto);
  }

  public void up(Long productId) {
    ProductResponseDto product = this.productCache.getProduct(productId);
    if (null != productId) {
      ProductResponseDto upProduct = this.productCache.getProductOfIdx(product.getIdx(), 1);
      if (null != upProduct) {
        ProductRequestDto up = new ProductRequestDto();
        up.setProductId(upProduct.getProductId());
        up.setIdx(product.getIdx());
        this.productCache.updateProduct(up);
        ProductRequestDto update = new ProductRequestDto();
        update.setProductId(product.getProductId());
        update.setIdx(upProduct.getIdx());
        this.productCache.updateProduct(update);
      }
    }
  }

  public void down(Long productId) {
    ProductResponseDto product = this.productCache.getProduct(productId);
    if (null != productId) {
      ProductResponseDto downProduct = this.productCache.getProductOfIdx(product.getIdx(), 2);
      if (null != downProduct) {
        ProductRequestDto down = new ProductRequestDto();
        down.setProductId(downProduct.getProductId());
        down.setIdx(product.getIdx());
        this.productCache.updateProduct(down);
        ProductRequestDto update = new ProductRequestDto();
        update.setProductId(product.getProductId());
        update.setIdx(downProduct.getIdx());
        this.productCache.updateProduct(update);
      }
    }
  }

  public void sortBySoldNumber() {
    productCache.sortBySoldNumber();
  }

  public void syncProductSoldNumber() {
    productCache.syncProductSoldNumber();
  }

}