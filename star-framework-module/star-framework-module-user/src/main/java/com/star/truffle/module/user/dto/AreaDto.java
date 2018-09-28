/**create by liuhua at 2018年9月4日 上午10:39:50**/
package com.star.truffle.module.user.dto;

import com.star.truffle.core.jdbc.Page;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AreaDto extends Page {

  private Long areaId;
  private Long parentId;
  private Integer code;
  private Integer type;     //1直辖市 2港澳台 3省 4市
  private Integer status;   //状态 1可用 0禁用
  private String shortName;
  private String areaName;
  private String pinyin;
  private String py;
  private String marker;
  private String longitude;  //经度
  private String latitude;   //纬度
  
}
