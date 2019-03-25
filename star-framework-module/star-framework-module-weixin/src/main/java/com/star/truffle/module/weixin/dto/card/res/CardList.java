/**create by liuhua at 2018年5月7日 上午10:26:18**/
package com.star.truffle.module.weixin.dto.card.res;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CardList {

  private int errcode;
  private String errmsg;
  private List<Card> cardList;
  private boolean hasShareCard;

  @JsonIgnore
  private Map<String, Integer> cardMap = null;
  @JsonIgnore
  private Map<String, String> cardCodeMap = null;

  public Integer getNum(String cardId) {
    if (null == cardMap) {
      cardMap = new HashMap<>();
      if (null != cardList && !cardList.isEmpty()) {
        for (Card card : cardList) {
          Integer num = cardMap.get(card.getCardId());
          if (null == num) {
            num = 0;
          }
          num++;
          cardMap.put(card.getCardId(), num);
        }
      }
    }
    return cardMap.get(cardId);
  }

  public String getCode(String cardId) {
    if (null == cardCodeMap) {
      cardCodeMap = new HashMap<>();
      if (null != cardList && !cardList.isEmpty()) {
        for (Card card : cardList) {
          String code = cardCodeMap.get(card.getCardId());
          if (null == code) {
            code = card.getCode();
          }
          cardCodeMap.put(card.getCardId(), code);
        }
      }
    }
    return cardCodeMap.get(cardId);
  }
}
