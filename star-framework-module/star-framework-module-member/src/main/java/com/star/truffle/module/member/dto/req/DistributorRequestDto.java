/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.dto.req;

import org.apache.commons.lang3.StringUtils;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.member.domain.Distributor;

@Getter
@Setter
public class DistributorRequestDto extends Distributor {

  private Page pager;
  private Long provinceId;
  private Long cityId;
  private Long areaId;
  private Long townId;
  
  private String code;
  private Integer tag;
  
  public boolean checkSaveData() {
    if (null != getDistributorId() || null == getRegionId() || StringUtils.isBlank(getName())
        || StringUtils.isBlank(getShopName()) || StringUtils.isBlank(getMobile()) || StringUtils.isBlank(getAddress())) {
      return false;
    }
    return true;
  }

  public boolean checkUpdateData() {
    if (null == getDistributorId() || null == getRegionId() || StringUtils.isBlank(getName())
      || StringUtils.isBlank(getShopName()) || StringUtils.isBlank(getMobile()) || StringUtils.isBlank(getAddress())) {
      return false;
    }
    return true;
  }
}