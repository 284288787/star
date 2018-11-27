/**create by framework at 2018年11月27日 10:12:39**/
package com.star.truffle.module.order.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.DistributorTotal;

@Getter
@Setter
public class DistributorTotalRequestDto extends DistributorTotal {

  private Page pager;
  
  private Integer day;
  
  private String beginTime;
  private String endTime;
}