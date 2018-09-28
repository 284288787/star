/**create by liuhua at 2018年8月17日 上午9:43:59**/
package com.star.truffle.module.user.domain;

import java.util.List;

import lombok.Data;

@Data
public class Area {

  private Long areaId;
  private Long parentId;
  private Integer code;
  private Integer type;     //1直辖市 2港澳台 3省 4市 5区县 6乡镇/街道
  private Integer status;   //状态 1可用 0禁用
  private String shortName;
  private String areaName;
  private String pinyin;
  private String py;
  private String marker;
  private String longitude;  //经度
  private String latitude;   //纬度
  
  private List<Area> children;
}
