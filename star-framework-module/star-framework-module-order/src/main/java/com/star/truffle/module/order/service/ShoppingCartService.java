/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.ShoppingCartCache;
import com.star.truffle.module.order.dto.req.ShoppingCartRequestDto;
import com.star.truffle.module.order.dto.res.ShoppingCartResponseDto;
import com.star.truffle.module.product.constant.ProductEnum;
import com.star.truffle.module.product.dto.res.ProductResponseDto;
import com.star.truffle.module.product.service.ProductService;

@Service
public class ShoppingCartService {

  @Autowired
  private ShoppingCartCache shoppingCartCache;
  @Autowired
  private ProductService productService;

  public Long saveShoppingCart(ShoppingCartRequestDto shoppingCart) {
    if (null == shoppingCart || ! shoppingCart.checkSaveData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    ProductResponseDto product = productService.getProduct(shoppingCart.getProductId());
    if (null == product) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "供应不存在");
    }
    if (product.getState() == ProductEnum.sellout.state()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "供应已售罄");
    }
    if (product.getState() >= ProductEnum.offshelf.state()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "供应已下架或已不存在");
    }
    this.shoppingCartCache.saveShoppingCart(shoppingCart);
    return shoppingCart.getCartId();
  }

  public void updateShoppingCart(ShoppingCartRequestDto shoppingCartRequestDto) {
    if (null == shoppingCartRequestDto || null == shoppingCartRequestDto.getCartId() 
        || null == shoppingCartRequestDto.getMemberId() || null == shoppingCartRequestDto.getNum()
        || shoppingCartRequestDto.getNum() < 1) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    ShoppingCartResponseDto cart = this.shoppingCartCache.getShoppingCart(shoppingCartRequestDto.getCartId());
    if (null == cart) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "信息不存在");
    }
    if (cart.getMemberId() != shoppingCartRequestDto.getMemberId().longValue()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "不能修改别人的购物车");
    }
    this.shoppingCartCache.updateShoppingCart(shoppingCartRequestDto);
  }
  
  public void updateShoppingCartChecked(ShoppingCartRequestDto shoppingCartRequestDto) {
    if (null == shoppingCartRequestDto || null == shoppingCartRequestDto.getCartId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    ShoppingCartResponseDto cart = this.shoppingCartCache.getShoppingCart(shoppingCartRequestDto.getCartId());
    if (null == cart) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "信息不存在");
    }
    if (cart.getMemberId() != shoppingCartRequestDto.getMemberId().longValue()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "不能修改别人的购物车");
    }
    this.shoppingCartCache.updateShoppingCart(shoppingCartRequestDto);
  }

  public void deleteShoppingCart(Long cartId) {
    this.shoppingCartCache.deleteShoppingCart(cartId);
  }

  public void deleteShoppingCart(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] cartIds = idstr.split(",");
    for (String str : cartIds) {
      Long cartId = Long.parseLong(str);
      this.shoppingCartCache.deleteShoppingCart(cartId);
    }
  }

  public ShoppingCartResponseDto getShoppingCart(Long cartId) {
    ShoppingCartResponseDto shoppingCartResponseDto = this.shoppingCartCache.getShoppingCart(cartId);
    return shoppingCartResponseDto;
  }

  public List<ShoppingCartResponseDto> queryShoppingCart(ShoppingCartRequestDto shoppingCartRequestDto) {
    if (null == shoppingCartRequestDto || null == shoppingCartRequestDto.getMemberId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    return this.shoppingCartCache.queryShoppingCart(shoppingCartRequestDto);
  }

  public Long queryShoppingCartCount(ShoppingCartRequestDto shoppingCartRequestDto) {
    return this.shoppingCartCache.queryShoppingCartCount(shoppingCartRequestDto);
  }

}