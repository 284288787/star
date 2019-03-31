/**create by liuhua at 2019年1月24日 上午10:58:26**/
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
public class TextImage {

  private String text;
  private String imageUrl;
}
