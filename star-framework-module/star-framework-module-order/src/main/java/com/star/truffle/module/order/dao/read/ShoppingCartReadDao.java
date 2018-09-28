/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.order.dto.res.ShoppingCartResponseDto;

public interface ShoppingCartReadDao {

  public ShoppingCartResponseDto getShoppingCart(Long cartId);

  public List<ShoppingCartResponseDto> queryShoppingCart(Map<String, Object> conditions);

  public Long queryShoppingCartCount(Map<String, Object> conditions);

}