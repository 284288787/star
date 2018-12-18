/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.res;

import java.util.List;

import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.domain.OrderDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto extends Order {

  private String regionName;
  private String distributorName;
  
  private Long regionProvinceId;
  private String regionProvinceName;
  private Long regionCityId;
  private String regionCityName;
  private Long regionAreaId;
  private String regionAreaName;
  private Long regionTownId;
  private String regionTownName;
  
  private List<OrderDetail> details;
}