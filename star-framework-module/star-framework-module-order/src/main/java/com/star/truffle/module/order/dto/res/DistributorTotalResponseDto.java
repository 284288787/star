/**create by framework at 2018年11月27日 10:12:39**/
package com.star.truffle.module.order.dto.res;

import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.DistributorTotal;

@Getter
@Setter
public class DistributorTotalResponseDto extends DistributorTotal {

  private String distributorName;
}