/**create by framework at 2018年09月18日 10:59:57**/
package com.star.truffle.module.product.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.product.dto.res.ProductSubscriptionResponseDto;

public interface ProductSubscriptionReadDao {

  public ProductSubscriptionResponseDto getProductSubscription(Long id);

  public List<ProductSubscriptionResponseDto> queryProductSubscription(Map<String, Object> conditions);

  public Long queryProductSubscriptionCount(Map<String, Object> conditions);

}