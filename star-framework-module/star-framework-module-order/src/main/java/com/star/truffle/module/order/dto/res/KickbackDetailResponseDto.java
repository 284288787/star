/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.dto.res;

import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.KickbackDetail;

@Getter
@Setter
public class KickbackDetailResponseDto extends KickbackDetail {

  private String distributorName;
  private String distributorMobile;
  private String provinceName;
  private String cityName;
  private String areaName;
  private String townName;
  private String regionName;
}