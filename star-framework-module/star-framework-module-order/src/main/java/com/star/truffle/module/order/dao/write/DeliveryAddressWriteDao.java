/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.write;

import java.util.List;
import com.star.truffle.module.order.domain.DeliveryAddress;
import com.star.truffle.module.order.dto.req.DeliveryAddressRequestDto;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;

public interface DeliveryAddressWriteDao {

  public int saveDeliveryAddress(DeliveryAddress deliveryAddress);

  public int batchSaveDeliveryAddress(List<DeliveryAddress> deliveryAddresss);

  public int updateDeliveryAddress(DeliveryAddressRequestDto deliveryAddressDto);

  public int deleteDeliveryAddress(Long id);

  public DeliveryAddressResponseDto getDeliveryAddress(Long id);

}