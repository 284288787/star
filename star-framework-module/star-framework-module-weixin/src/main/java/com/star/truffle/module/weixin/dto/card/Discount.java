/**create by liuhua at 2019年1月24日 下午1:50:48**/
package com.star.truffle.module.weixin.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 折扣券
 * 
 * @author liuhua
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
  
  private BaseInfo baseInfo;
  private AdvancedInfo advancedInfo;
  private Integer discount;  //折扣券专用，表示打折额度（百分比）。填30就是七折。
}
