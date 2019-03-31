/**create by liuhua at 2019年1月24日 上午10:56:51**/
package com.star.truffle.module.weixin.dto.card;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Abstract {

  //封面描述
  @JsonProperty("abstract")
  private String abstracd;
  private String[] iconUrlList;
}
