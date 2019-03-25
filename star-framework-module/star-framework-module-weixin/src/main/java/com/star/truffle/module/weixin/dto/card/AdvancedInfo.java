/**create by liuhua at 2019年1月24日 上午10:51:32**/
package com.star.truffle.module.weixin.dto.card;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AdvancedInfo {

  private UseCondition useCondition;
  @JsonProperty("abstract")
  private Abstract abstracd;
  private List<TextImage> textImageList;
}
