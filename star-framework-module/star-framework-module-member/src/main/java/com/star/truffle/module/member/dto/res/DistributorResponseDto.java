/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.dto.res;

import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.member.domain.Distributor;

@Getter
@Setter
public class DistributorResponseDto extends Distributor {

  private String regionName;
  private String parentDistributorName;
  private Long provinceId;
  private String provinceName;
  private Long cityId;
  private String cityName;
  private Long areaId;
  private String areaName;
  private Long townId;
  private String townName;
  private String py;
  
  private String fullAreaName;
  private String fullAreaJson;
  
  private Integer childNum;
}