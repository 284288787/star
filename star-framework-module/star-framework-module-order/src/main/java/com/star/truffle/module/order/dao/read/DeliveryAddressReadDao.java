/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;

public interface DeliveryAddressReadDao {

  public DeliveryAddressResponseDto getDeliveryAddress(Long id);

  public List<DeliveryAddressResponseDto> queryDeliveryAddress(Map<String, Object> conditions);

  public Long queryDeliveryAddressCount(Map<String, Object> conditions);

}