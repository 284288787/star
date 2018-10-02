/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.DeliveryAddressCache;
import com.star.truffle.module.order.dto.req.DeliveryAddressRequestDto;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;

@Service
public class DeliveryAddressService {

  @Autowired
  private DeliveryAddressCache deliveryAddressCache;

  public Long saveDeliveryAddress(DeliveryAddressRequestDto deliveryAddress) {
    if (null == deliveryAddress || ! deliveryAddress.checkeSaveData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    
    DeliveryAddressRequestDto param = new DeliveryAddressRequestDto();
    param.setMemberId(deliveryAddress.getMemberId());
    param.setDef(1);
    List<DeliveryAddressResponseDto> list = this.deliveryAddressCache.queryDeliveryAddress(param);
    if (null == list || list.isEmpty()) {
      deliveryAddress.setDef(1);
    }
    DeliveryAddressResponseDto newAddress = this.deliveryAddressCache.saveDeliveryAddress(deliveryAddress);
    if (newAddress.getDef() == 1) {
      if (null != list && ! list.isEmpty()) {
        for (DeliveryAddressResponseDto deliveryAddressResponseDto : list) {
          DeliveryAddressRequestDto deliveryAddressRequestDto = new DeliveryAddressRequestDto();
          deliveryAddressRequestDto.setId(deliveryAddressResponseDto.getId());
          deliveryAddressRequestDto.setDef(0);
          this.deliveryAddressCache.updateDeliveryAddress(deliveryAddressRequestDto);
        }
      }
    }
    return newAddress.getId();
  }

  public void updateDeliveryAddress(DeliveryAddressRequestDto deliveryAddressRequestDto) {
    if (null == deliveryAddressRequestDto || ! deliveryAddressRequestDto.checkeUpdateData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    DeliveryAddressResponseDto da = this.deliveryAddressCache.getDeliveryAddress(deliveryAddressRequestDto.getId());
    if (null == da) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "收货地址不存在");
    }
    if (da.getMemberId() != deliveryAddressRequestDto.getMemberId().longValue()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "不能修改别人的收货地址");
    }
    
    if (deliveryAddressRequestDto.getDef() == 1) {
      DeliveryAddressRequestDto param = new DeliveryAddressRequestDto();
      param.setMemberId(deliveryAddressRequestDto.getMemberId());
      param.setDef(1);
      List<DeliveryAddressResponseDto> list = this.deliveryAddressCache.queryDeliveryAddress(param);
      if (null != list && ! list.isEmpty()) {
        for (DeliveryAddressResponseDto deliveryAddressResponseDto : list) {
          if (deliveryAddressResponseDto.getId() != deliveryAddressRequestDto.getId()) {
            DeliveryAddressRequestDto deliveryAddress = new DeliveryAddressRequestDto();
            deliveryAddressRequestDto.setId(deliveryAddressResponseDto.getId());
            deliveryAddressRequestDto.setDef(0);
            this.deliveryAddressCache.updateDeliveryAddress(deliveryAddress);
          }
        }
      }
    }
    
    this.deliveryAddressCache.updateDeliveryAddress(deliveryAddressRequestDto);
  }
  
  public void deleteDeliveryAddress(Long id, Long memberId) {
    if (null == id || null == memberId) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    DeliveryAddressResponseDto da = this.deliveryAddressCache.getDeliveryAddress(id);
    if (null == da) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "收货地址不存在");
    }
    if (da.getMemberId() != memberId.longValue()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "不能删除别人的收货地址");
    }
    this.deliveryAddressCache.deleteDeliveryAddress(id);
  }

  public void setDef(Long id, Long memberId) {
    if (null == id || null == memberId) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    DeliveryAddressResponseDto da = this.deliveryAddressCache.getDeliveryAddress(id);
    if (null == da) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "收货地址不存在");
    }
    if (da.getMemberId() != memberId.longValue()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "不能编辑别人的收货地址");
    }
    DeliveryAddressRequestDto param = new DeliveryAddressRequestDto();
    param.setMemberId(memberId);
    param.setDef(1);
    List<DeliveryAddressResponseDto> list = this.deliveryAddressCache.queryDeliveryAddress(param);
    boolean update = true;
    if (null != list && ! list.isEmpty()) {
      for (DeliveryAddressResponseDto deliveryAddressResponseDto : list) {
        if (deliveryAddressResponseDto.getId() != id.longValue()) {
          DeliveryAddressRequestDto deliveryAddressRequestDto = new DeliveryAddressRequestDto();
          deliveryAddressRequestDto.setId(deliveryAddressResponseDto.getId());
          deliveryAddressRequestDto.setDef(0);
          this.deliveryAddressCache.updateDeliveryAddress(deliveryAddressRequestDto);
        }else {
          update = false;
        }
      }
    }
    if (update) {
      DeliveryAddressRequestDto deliveryAddressRequestDto = new DeliveryAddressRequestDto();
      deliveryAddressRequestDto.setId(id);
      deliveryAddressRequestDto.setDef(1);
      this.deliveryAddressCache.updateDeliveryAddress(deliveryAddressRequestDto);
    }
  }

  public void deleteDeliveryAddress(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      this.deliveryAddressCache.deleteDeliveryAddress(id);
    }
  }

  public DeliveryAddressResponseDto getDeliveryAddress(Long id) {
    DeliveryAddressResponseDto deliveryAddressResponseDto = this.deliveryAddressCache.getDeliveryAddress(id);
    return deliveryAddressResponseDto;
  }

  public List<DeliveryAddressResponseDto> queryDeliveryAddress(DeliveryAddressRequestDto deliveryAddressRequestDto) {
    if (null == deliveryAddressRequestDto || null == deliveryAddressRequestDto.getMemberId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    return this.deliveryAddressCache.queryDeliveryAddress(deliveryAddressRequestDto);
  }

  public Long queryDeliveryAddressCount(DeliveryAddressRequestDto deliveryAddressRequestDto) {
    return this.deliveryAddressCache.queryDeliveryAddressCount(deliveryAddressRequestDto);
  }

}