/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class DistributionRegion {

  // 区域ID
  private Long regionId;
  // 区域名称
  private String name;
  // 区域二级域名
  private String py;
  // 省份
  private Long provinceId;
  // 城市
  private Long cityId;
  // 区县
  private Long areaId;
  // 乡镇/街道
  private Long townId;
  // 区域状态 1有效 2禁用 3删除
  private Integer state;
  // 创建日期
  private Date createTime;
  // 更新日期
  private Date updateTime;
  // 粉丝数 下面买过东西的会员人数
  private Integer fansNum;
  // 已售件数
  private Integer soldNum;
}