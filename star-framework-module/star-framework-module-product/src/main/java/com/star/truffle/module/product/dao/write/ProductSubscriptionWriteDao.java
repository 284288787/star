/**create by framework at 2018年09月18日 10:59:57**/
package com.star.truffle.module.product.dao.write;

import java.util.List;
import com.star.truffle.module.product.domain.ProductSubscription;
import com.star.truffle.module.product.dto.req.ProductSubscriptionRequestDto;
import com.star.truffle.module.product.dto.res.ProductSubscriptionResponseDto;

public interface ProductSubscriptionWriteDao {

  public int saveProductSubscription(ProductSubscription productSubscription);

  public int batchSaveProductSubscription(List<ProductSubscription> productSubscriptions);

  public int updateProductSubscription(ProductSubscriptionRequestDto productSubscriptionDto);

  public int deleteProductSubscription(Long id);

  public ProductSubscriptionResponseDto getProductSubscription(Long id);

}