/**create by liuhua at 2019年3月25日 上午10:34:14**/
package com.star.truffle.module.weixin.dto.card.res;

import com.star.truffle.module.weixin.dto.card.CardInfo;

import lombok.Data;

@Data
public class CardDetail {

  private int errcode;
  private String errmsg;
  private CardInfo card;
}
