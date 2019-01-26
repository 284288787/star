/**create by liuhua at 2019年1月24日 下午1:52:16**/
package com.star.truffle.module.weixin.dto;

import lombok.Builder;
import lombok.Getter;
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
public class Gift {

  private BaseInfo baseInfo;
  private AdvancedInfo advancedInfo;
  private String gift;  //兑换券专用，填写兑换内容的信息。
  
}
