/**create by framework at 2018年09月04日 10:28:05**/
package com.star.truffle.module.product.dao.read;

import java.util.List;

import com.star.truffle.module.product.domain.ProductPicture;

public interface ProductPictureReadDao {

  public List<ProductPicture> getProductPictureByProductId(Long productId);
}