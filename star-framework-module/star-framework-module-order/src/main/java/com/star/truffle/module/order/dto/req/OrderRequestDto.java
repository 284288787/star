/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.req;

import java.util.Date;
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
  private String transportStates;
  private Long regionProvinceId;
  private Long regionCityId;
  private Long regionAreaId;
  private Long regionTownId;
  
  private Integer minutes30;  //是否查询30分以内的订单 1是 0否
  private Integer brokerage0; //是否查询提成为0的记录 1是 0否
  
  private Date beginCreateTime;
  private Date endCreateTime;
  private Integer time;       //0今天 1本周 2本月 3全部
  
  private Long productId;
  private String productName;
  
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
            StringUtils.isBlank(getProvinceName()) || StringUtils.isBlank(getCityName()) || StringUtils.isBlank(getAreaName())
            || StringUtils.isBlank(getDeliveryAddress())
            )
        ) {
      return false;
    }
    return true;
  }
}