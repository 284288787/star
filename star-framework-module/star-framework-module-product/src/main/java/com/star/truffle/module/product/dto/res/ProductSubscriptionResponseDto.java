/**create by framework at 2018年09月18日 10:59:57**/
package com.star.truffle.module.product.dto.res;

import com.star.truffle.module.product.domain.ProductSubscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSubscriptionResponseDto extends ProductSubscription {

  private String name;
  private String mobile;
  private String head;
  private String title;
}