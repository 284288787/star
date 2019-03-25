/**create by liuhua at 2019年1月24日 上午10:36:18**/
package com.star.truffle.module.weixin.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025056
public class BaseInfo {

  /**必填  begin**/
  private String logoUrl;
  private String codeType;
  private String brandName;
  private String title;
  private String color;
  private String notice;
  private String description;
  private Sku sku;
  private DateInfo dateInfo;
  /**必填  end**/
  /**选填  begin**/
  //每人可领券的数量限制,不填写默认为50。
  private Integer getLimit;
  //每人可核销的数量限制,不填写默认为50。
  private Integer useLimit;
  //卡券领取页面是否可分享。
  private Boolean canShare;
  //卡券是否可转赠。
  private Boolean canGiveFriend;
  private String servicePhone;
  private Boolean useAllLocations;
  //中间按钮设置
  private String centerTitle;
  private String centerSubTitle;
  private String centerUrl;
  //下面按钮设置
  private String customUrlName;
  private String customUrlSubTitle;
  private String customUrl;
  /**选填  end**/
}
