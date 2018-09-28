/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.res;

import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.DeliveryAddress;

@Getter
@Setter
public class DeliveryAddressResponseDto extends DeliveryAddress {

  private String provinceName;
  private String cityName;
  private String areaName;
}