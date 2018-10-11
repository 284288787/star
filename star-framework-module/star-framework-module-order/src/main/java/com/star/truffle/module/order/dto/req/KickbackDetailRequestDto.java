/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.KickbackDetail;

@Getter
@Setter
public class KickbackDetailRequestDto extends KickbackDetail {

  private Page pager;
  
  private String distributorMobile;
  private Long provinceId;
  private Long cityId;
  private Long areaId;
  private Long townId;
  private Long regionId;
}