/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.write;

import java.util.List;
import com.star.truffle.module.order.domain.ShoppingCart;
import com.star.truffle.module.order.dto.req.ShoppingCartRequestDto;
import com.star.truffle.module.order.dto.res.ShoppingCartResponseDto;

public interface ShoppingCartWriteDao {

  public int saveShoppingCart(ShoppingCart shoppingCart);

  public int batchSaveShoppingCart(List<ShoppingCart> shoppingCarts);

  public int updateShoppingCart(ShoppingCartRequestDto shoppingCartDto);

  public int deleteShoppingCart(Long cartId);

  public ShoppingCartResponseDto getShoppingCart(Long cartId);

}