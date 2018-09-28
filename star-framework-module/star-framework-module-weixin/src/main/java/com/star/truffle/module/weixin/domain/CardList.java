/**create by liuhua at 2018年5月7日 上午10:26:18**/
package com.star.truffle.module.weixin.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardList {

	private int errcode;
	private String errmsg;
	private List<Card> card_list;
	private boolean has_share_card;
	
	private Map<String, Integer> cardMap = null;
	private Map<String, String> cardCodeMap = null;
	
	public Integer getNum(String cardId){
		if (null == cardMap) {
			cardMap = new HashMap<>();
			if (null != card_list && ! card_list.isEmpty()) {
				for (Card card : card_list) {
					Integer num = cardMap.get(card.getCard_id());
					if (null == num) {
						num = 0;
					}
					num ++;
					cardMap.put(card.getCard_id(), num);
				}
			}
		}
		return cardMap.get(cardId);
	}
	
	public String getCode(String cardId){
		if (null == cardCodeMap) {
			cardCodeMap = new HashMap<>();
			if (null != card_list && ! card_list.isEmpty()) {
				for (Card card : card_list) {
					String code = cardCodeMap.get(card.getCard_id());
					if (null == code) {
						code = card.getCode();
					}
					cardCodeMap.put(card.getCard_id(), code);
				}
			}
		}
		return cardCodeMap.get(cardId);
	}
	
	public int getErrcode() {
		return errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public List<Card> getCard_list() {
		return card_list;
	}
	public boolean isHas_share_card() {
		return has_share_card;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public void setCard_list(List<Card> card_list) {
		this.card_list = card_list;
	}
	public void setHas_share_card(boolean has_share_card) {
		this.has_share_card = has_share_card;
	}
}
