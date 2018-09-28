/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Distributor {

  // 主键
  private Long distributorId;
  // 头像
  private String head;
  // 姓名
  private String name;
  // 店铺名称
  private String shopName;
  // 店铺编码
  private String shopCode;
  // 手机号
  private String mobile;
  // 分销区域
  private Long regionId;
  // 街道地址
  private String address;
  // 是否可用 1可用 0禁用
  private Integer enabled;
  // 创建日期
  private Date createTime;
  // 更新日期
  private Date updateTime;
  // openid
  private String openId;
  // 粉丝数
  private Integer fansNum;
  // 已售件数
  private Integer soldNum;
  // 状态 1未登录 2已登录
  private Integer state;
}