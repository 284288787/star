/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.order.dao.read.ShoppingCartReadDao;
import com.star.truffle.module.order.dao.write.ShoppingCartWriteDao;
import com.star.truffle.module.order.domain.ShoppingCart;
import com.star.truffle.module.order.dto.req.ShoppingCartRequestDto;
import com.star.truffle.module.order.dto.res.ShoppingCartResponseDto;

@Service
public class ShoppingCartCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private ShoppingCartWriteDao shoppingCartWriteDao;
  @Autowired
  private ShoppingCartReadDao shoppingCartReadDao;

  @CachePut(value = "module-order-shoppingCart", key = "'shoppingCart_cartId_'+#result.cartId", condition = "#result != null and #result.cartId != null")
  public ShoppingCartResponseDto saveShoppingCart(ShoppingCart shoppingCart){
    this.shoppingCartWriteDao.saveShoppingCart(shoppingCart);
    ShoppingCartResponseDto shoppingCartResponseDto = this.shoppingCartWriteDao.getShoppingCart(shoppingCart.getCartId());
    return shoppingCartResponseDto;
  }

  @CachePut(value = "module-order-shoppingCart", key = "'shoppingCart_cartId_'+#result.cartId", condition = "#result != null and #result.cartId != null")
  public ShoppingCartResponseDto updateShoppingCart(ShoppingCartRequestDto shoppingCartRequestDto){
    this.shoppingCartWriteDao.updateShoppingCart(shoppingCartRequestDto);
    ShoppingCartResponseDto shoppingCartResponseDto = this.shoppingCartWriteDao.getShoppingCart(shoppingCartRequestDto.getCartId());
    return shoppingCartResponseDto;
  }

  @CacheEvict(value = "module-order-shoppingCart", key = "'shoppingCart_cartId_'+#cartId", condition = "#cartId != null")
  public int deleteShoppingCart(Long cartId){
    return this.shoppingCartWriteDao.deleteShoppingCart(cartId);
  }

  @Cacheable(value = "module-order-shoppingCart", key = "'shoppingCart_cartId_'+#cartId", condition = "#cartId != null")
  public ShoppingCartResponseDto getShoppingCart(Long cartId){
    ShoppingCartResponseDto shoppingCartResponseDto = this.shoppingCartReadDao.getShoppingCart(cartId);
    return shoppingCartResponseDto;
  }

  public List<ShoppingCartResponseDto> queryShoppingCart(ShoppingCartRequestDto shoppingCartRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(shoppingCartRequestDto);
    return this.shoppingCartReadDao.queryShoppingCart(conditions);
  }

  public Long queryShoppingCartCount(ShoppingCartRequestDto shoppingCartRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(shoppingCartRequestDto);
    return this.shoppingCartReadDao.queryShoppingCartCount(conditions);
  }

}