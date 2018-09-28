/**create by framework at 2018年09月18日 10:59:57**/
package com.star.truffle.module.product.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.product.domain.ProductSubscription;

@Getter
@Setter
public class ProductSubscriptionRequestDto extends ProductSubscription {

  private Page pager;
}