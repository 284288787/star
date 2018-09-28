/**create by framework at 2018年09月18日 11:52:25**/
package com.star.truffle.module.member.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Member {

  // memberId
  private Long memberId;
  // 头像
  private String head;
  // 姓名
  private String name;
  // 手机号
  private String mobile;
  // openid
  private String openId;
  // 创建日期
  private Date createTime;
  // 状态 1未登录 2已登录
  private Integer state;
}