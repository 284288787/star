/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.req;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.star.truffle.core.jdbc.Page;
import com.star.truffle.module.order.constant.DeliveryTypeEnum;
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.domain.OrderDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto extends Order {

  private Page pager;
  
  private Long deliveryId;
  
  private List<OrderDetail> details;
  
  private String states;
  
  public boolean checkMemberSave() {
    if (null != getOrderId() || null == getMemberId() || null == getDeliveryType()
        || null == getDistributorId() || null == details || details.isEmpty()
        || (getDeliveryType() == DeliveryTypeEnum.self.type()) && (StringUtils.isBlank(getName()) || StringUtils.isBlank(getMobile()))
        || (getDeliveryType() == DeliveryTypeEnum.express.type()) && null == deliveryId) {
      return false;
    }
    return true;
  }
  
  public boolean checkDistributorSave() {
    if (null != getOrderId() || null == getDeliveryType() || StringUtils.isBlank(getName()) || StringUtils.isBlank(getMobile())
        || null == getDistributorId()  || null == details || details.isEmpty()
        || (getDeliveryType() == DeliveryTypeEnum.express.type()) && (
            null == getProvinceId() || null == getCityId() || null == getAreaId() 
            || StringUtils.isBlank(getDeliveryAddress())
            )
        ) {
      return false;
    }
    return true;
  }
}