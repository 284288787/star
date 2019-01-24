/**create by liuhua at 2019年1月24日 上午10:58:26**/
package com.star.truffle.module.weixin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TextImage {

  private String text;
  private String imageUrl;
}
