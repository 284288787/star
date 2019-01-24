/**create by liuhua at 2019年1月24日 下午1:53:51**/
package com.star.truffle.module.weixin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 优惠券
 * 
 * @author liuhua
 *
 */
@Getter
@Setter
@Builder
public class GeneralCoupon {

  private BaseInfo baseInfo;
  private AdvancedInfo advancedInfo;
  private String defaultDetail;  //优惠券专用，填写优惠详情。
  
}
