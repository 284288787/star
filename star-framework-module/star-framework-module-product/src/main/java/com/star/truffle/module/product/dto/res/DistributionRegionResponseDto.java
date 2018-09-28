/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.dto.res;

import lombok.Getter;
import lombok.Setter;

import com.star.truffle.module.member.dto.res.DistributorResponseDto;
import com.star.truffle.module.product.domain.DistributionRegion;

@Getter
@Setter
public class DistributionRegionResponseDto extends DistributionRegion {

  private String provinceName;
  private String cityName;
  private String areaName;
  private String townName;
  
  private String viewAreaName;
  
  private DistributorResponseDto distributor;
}