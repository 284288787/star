/**create by framework at 2018年09月04日 10:28:05**/
package com.star.truffle.module.product.dao.write;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.product.domain.ProductInventory;

public interface ProductInventoryWriteDao {

  public int saveProductInventory(ProductInventory productInventory);

  public int deleteProductInventoryByProductId(@Param("productId") Long productId, @Param("type") int type);

  public ProductInventory getProductInventoryByProductId(@Param("productId") Long productId, @Param("type") int type);

}