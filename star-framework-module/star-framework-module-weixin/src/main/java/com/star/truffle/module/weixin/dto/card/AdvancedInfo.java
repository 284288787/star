/**create by liuhua at 2019年1月24日 上午10:51:32**/
package com.star.truffle.module.weixin.dto.card;

import java.util.List;

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
public class AdvancedInfo {

  private UseCondition useCondition;
  @JsonProperty("abstract")
  private Abstract abstracd;
  private List<TextImage> textImageList;
}
