/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.order.dao.read.DeliveryAddressReadDao;
import com.star.truffle.module.order.dao.write.DeliveryAddressWriteDao;
import com.star.truffle.module.order.domain.DeliveryAddress;
import com.star.truffle.module.order.dto.req.DeliveryAddressRequestDto;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;

@Service
public class DeliveryAddressCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private DeliveryAddressWriteDao deliveryAddressWriteDao;
  @Autowired
  private DeliveryAddressReadDao deliveryAddressReadDao;

  @CachePut(value = "module-order-deliveryAddress", key = "'deliveryAddress_id_'+#result.id", condition = "#result != null and #result.id != null")
  public DeliveryAddressResponseDto saveDeliveryAddress(DeliveryAddress deliveryAddress){
    this.deliveryAddressWriteDao.saveDeliveryAddress(deliveryAddress);
    DeliveryAddressResponseDto deliveryAddressResponseDto = this.deliveryAddressWriteDao.getDeliveryAddress(deliveryAddress.getId());
    return deliveryAddressResponseDto;
  }

  @CachePut(value = "module-order-deliveryAddress", key = "'deliveryAddress_id_'+#result.id", condition = "#result != null and #result.id != null")
  public DeliveryAddressResponseDto updateDeliveryAddress(DeliveryAddressRequestDto deliveryAddressRequestDto){
    this.deliveryAddressWriteDao.updateDeliveryAddress(deliveryAddressRequestDto);
    DeliveryAddressResponseDto deliveryAddressResponseDto = this.deliveryAddressWriteDao.getDeliveryAddress(deliveryAddressRequestDto.getId());
    return deliveryAddressResponseDto;
  }

  @CacheEvict(value = "module-order-deliveryAddress", key = "'deliveryAddress_id_'+#id", condition = "#id != null")
  public int deleteDeliveryAddress(Long id){
    return this.deliveryAddressWriteDao.deleteDeliveryAddress(id);
  }

  @Cacheable(value = "module-order-deliveryAddress", key = "'deliveryAddress_id_'+#id", condition = "#id != null")
  public DeliveryAddressResponseDto getDeliveryAddress(Long id){
    DeliveryAddressResponseDto deliveryAddressResponseDto = this.deliveryAddressReadDao.getDeliveryAddress(id);
    return deliveryAddressResponseDto;
  }

  public List<DeliveryAddressResponseDto> queryDeliveryAddress(DeliveryAddressRequestDto deliveryAddressRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(deliveryAddressRequestDto);
    return this.deliveryAddressReadDao.queryDeliveryAddress(conditions);
  }

  public Long queryDeliveryAddressCount(DeliveryAddressRequestDto deliveryAddressRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(deliveryAddressRequestDto);
    return this.deliveryAddressReadDao.queryDeliveryAddressCount(conditions);
  }

}