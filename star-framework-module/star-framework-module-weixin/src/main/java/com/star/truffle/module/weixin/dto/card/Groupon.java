/**create by liuhua at 2019年1月24日 上午10:35:16**/
package com.star.truffle.module.weixin.dto.card;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 团购券
 * @author liuhua
 *
 */
@Getter
@Setter
@Builder
public class Groupon {
  
  private BaseInfo baseInfo;
  private AdvancedInfo advancedInfo;
  private String dealDetail; //团购券专用，团购详情。例如：双人套餐\n -进口红酒一支。\n孜然牛肉一份。
}
