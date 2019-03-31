/**create by liuhua at 2019年1月24日 上午10:39:31**/
package com.star.truffle.module.weixin.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateInfo {

  private String type;
  
  //type=DATE_TYPE_FIX_TERM时
  private Integer fixedBeginTerm; //表示自领取后多少天开始生效，领取后当天生效填写0。（单位为天）
  private Integer FixedTerm;     //表示自领取后多少天内有效，不支持填写0。
  //type=DATE_TYPE_FIX_TIME_RANGE时
  private Long beginTimestamp;//表示起用时间
  private Long endTimestamp;  //当时间达到end_timestamp时卡券统一过期
}
