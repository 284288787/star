/**create by liuhua at 2019年1月24日 上午10:35:16**/
package com.star.truffle.module.weixin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 代金券
 * 
 * @author liuhua
 *
 */
@Getter
@Setter
@Builder
public class Cash {
  
  private BaseInfo baseInfo;
  private AdvancedInfo advancedInfo;
  private Integer leastCost;  //代金券专用，表示起用金额（单位为分）,如果无起用门槛则填0。
  private Integer reduceCost; //代金券专用，表示减免金额。（单位为分）
}
