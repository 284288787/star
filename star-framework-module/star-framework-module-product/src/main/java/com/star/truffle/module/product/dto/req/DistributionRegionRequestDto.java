/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.product.domain.DistributionRegion;

@Getter
@Setter
public class DistributionRegionRequestDto extends DistributionRegion {

  private Page pager;
  
  private String states;
  
  private String provinceName;
  private String cityName;
  private String areaName;
  private String townName;
}