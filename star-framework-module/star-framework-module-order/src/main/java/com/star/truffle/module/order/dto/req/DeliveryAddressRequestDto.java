/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.req;

import org.apache.commons.lang3.StringUtils;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.DeliveryAddress;

@Getter
@Setter
public class DeliveryAddressRequestDto extends DeliveryAddress {

  private Page pager;
  
  public boolean checkeSaveData() {
    if (null != getId() || null == getMemberId() || null == getProvinceId() || null == getCityId()
        || null == getAreaId() || null == getDef() || StringUtils.isBlank(getName()) || StringUtils.isBlank(getMobile())) {
      return false;
    }
    return true;
  }

  public boolean checkeUpdateData() {
    if (null == getId() || null == getMemberId() || null == getProvinceId() || null == getCityId()
        || null == getAreaId() || null == getDef() || StringUtils.isBlank(getName()) || StringUtils.isBlank(getMobile())) {
      return false;
    }
    return true;
  }
}