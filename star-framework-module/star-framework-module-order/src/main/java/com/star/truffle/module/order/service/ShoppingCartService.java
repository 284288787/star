/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.member.constant.EnabledEnum;
import com.star.truffle.module.order.cache.DeliveryAddressCache;
import com.star.truffle.module.order.cache.ShoppingCartCache;
import com.star.truffle.module.order.dto.req.DeliveryAddressRequestDto;
import com.star.truffle.module.order.dto.req.ShoppingCartRequestDto;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;
import com.star.truffle.module.order.dto.res.EnterOrder;
import com.star.truffle.module.order.dto.res.ShoppingCartResponseDto;
import com.star.truffle.module.order.properties.OrderProperties;
import com.star.truffle.module.product.constant.ProductEnum;
import com.star.truffle.module.product.dto.res.ProductResponseDto;
import com.star.truffle.module.product.service.ProductService;

@Service
public class ShoppingCartService {

  @Autowired
  private ShoppingCartCache shoppingCartCache;
  @Autowired
  private ProductService productService;
  @Autowired
  private OrderProperties orderProperties;
  @Autowired
  private DeliveryAddressCache deliveryAddressCache;
  @Autowired
  private StarJson starJson;

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
    ShoppingCartRequestDto queryParam = new ShoppingCartRequestDto();
    queryParam.setMemberId(shoppingCart.getMemberId());
    queryParam.setProductId(shoppingCart.getProductId());
    List<ShoppingCartResponseDto> list = this.shoppingCartCache.queryShoppingCart(queryParam);
    Long cartId = null;
    if (null != list && ! list.isEmpty()) {
      ShoppingCartResponseDto cart = list.get(0);
      ShoppingCartRequestDto shoppingCartRequestDto = new ShoppingCartRequestDto();
      shoppingCartRequestDto.setCartId(cart.getCartId());
      shoppingCartRequestDto.setNum(cart.getNum() + shoppingCart.getNum());
      if (cart.getChecked() != EnabledEnum.enabled.val()) {
        shoppingCartRequestDto.setChecked(EnabledEnum.enabled.val());
      }
      this.shoppingCartCache.updateShoppingCart(shoppingCartRequestDto);
      cartId = cart.getCartId();
    }else {
      shoppingCart.setChecked(EnabledEnum.enabled.val());
      shoppingCart.setCreateTime(new Date());
      this.shoppingCartCache.saveShoppingCart(shoppingCart);
      cartId = shoppingCart.getCartId();
    }
    return cartId;
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
  
  public void updateShoppingCartChecked(Long memberId, String cartIds, int checked) {
    if (null == memberId || StringUtils.isBlank(cartIds)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = cartIds.split(",");
    for (String id : ids) {
      ShoppingCartResponseDto cart = this.shoppingCartCache.getShoppingCart(Long.parseLong(id));
      if (null == cart) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "信息不存在");
      }
      if (cart.getMemberId() != memberId.longValue()) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "不能修改别人的购物车");
      }
      ShoppingCartRequestDto shoppingCartRequestDto = new ShoppingCartRequestDto();
      shoppingCartRequestDto.setCartId(cart.getCartId());
      shoppingCartRequestDto.setChecked(checked);
      this.shoppingCartCache.updateShoppingCart(shoppingCartRequestDto);
    }
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

  public EnterOrder enterOrder(Long memberId) {
    ShoppingCartRequestDto shoppingCartRequestDto = new ShoppingCartRequestDto();
    shoppingCartRequestDto.setMemberId(memberId);
    shoppingCartRequestDto.setChecked(EnabledEnum.enabled.val());
    List<ShoppingCartResponseDto> products = this.shoppingCartCache.queryShoppingCart(shoppingCartRequestDto);
    for (ShoppingCartResponseDto shoppingCartResponseDto : products) {
      ProductResponseDto productResponseDto = this.productService.getProduct(shoppingCartResponseDto.getProductId());
      if (null == productResponseDto) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "商品不存在");
      }
      if (productResponseDto.getState() != ProductEnum.onshelf.state()) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "商品现在已不能购买");
      }
    }
    Integer despatchMoney = this.orderProperties.getDespatchMoney();
    DeliveryAddressRequestDto deliveryAddressRequestDto = new DeliveryAddressRequestDto();
    deliveryAddressRequestDto.setMemberId(memberId);
    deliveryAddressRequestDto.setDef(EnabledEnum.enabled.val());
    List<DeliveryAddressResponseDto> list = this.deliveryAddressCache.queryDeliveryAddress(deliveryAddressRequestDto);
    DeliveryAddressResponseDto deliveryAddress = null;
    if (null != list && ! list.isEmpty()) {
      deliveryAddress = list.get(0);
    }
    EnterOrder enterOrder = new EnterOrder(despatchMoney, products, deliveryAddress);
    return enterOrder;
  }

  public EnterOrder buyNow(Long memberId, Long productId, int num) {
    ProductResponseDto productResponseDto = this.productService.getProduct(productId);
    if (null == productResponseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "商品不存在");
    }
    if (productResponseDto.getState() != ProductEnum.onshelf.state()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "商品现在已不能购买");
    }
    ShoppingCartResponseDto shoppingCartResponseDto = starJson.str2obj(starJson.obj2string(productResponseDto), ShoppingCartResponseDto.class);
    shoppingCartResponseDto.setNum(num);
    Integer despatchMoney = this.orderProperties.getDespatchMoney();
    DeliveryAddressRequestDto deliveryAddressRequestDto = new DeliveryAddressRequestDto();
    deliveryAddressRequestDto.setMemberId(memberId);
    deliveryAddressRequestDto.setDef(EnabledEnum.enabled.val());
    List<DeliveryAddressResponseDto> list = this.deliveryAddressCache.queryDeliveryAddress(deliveryAddressRequestDto);
    DeliveryAddressResponseDto deliveryAddress = null;
    if (null != list && ! list.isEmpty()) {
      deliveryAddress = list.get(0);
    }
    EnterOrder enterOrder = new EnterOrder(despatchMoney, Arrays.asList(shoppingCartResponseDto), deliveryAddress);
    return enterOrder;
  }

}