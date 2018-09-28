/**create by framework at 2018年09月04日 10:28:05**/
package com.star.truffle.module.product.dao.read;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.product.domain.ProductInventory;

public interface ProductInventoryReadDao {

  public ProductInventory getProductInventoryByProductId(@Param("productId") Long productId, @Param("type") int type);

}