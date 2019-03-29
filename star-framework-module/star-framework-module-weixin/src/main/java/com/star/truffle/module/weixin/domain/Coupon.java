/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.weixin.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 卡券：
 * 1.卡券可以设置适用大分类，分类
 * 2.卡券可以设置适用具体某些供应
 * 3.卡券可以设置适用某些用户
 * @author liuhua
 *
 */
@Getter
@Setter
public class Coupon {

  // 卡券ID
  private Long couponId;
  // 微信卡券Id
  private String cardId;
  // 卡券标题
  private String title;
  // 卡券描述
  private String description;
  // 卡券类型 CardTypeEnum 
  private String cardType;
  // 是否禁用 1是 0否
  private Integer enabled;
  // 是否删除 1是 0否
  private Integer deleted;
  // 创建日期
  private Date createTime;
}