/**create by liuhua at 2018年7月14日 上午10:10:22**/
package com.star.truffle.module.user.domain;

import lombok.Data;

@Data
public class Resource {

  private Long sourceId;             //资源id
  private String sourceName;         //资源名称
  private String sourceIcoCls;       //菜单图标class
  private Long parentId;             //父
  private Integer enabled;           //是否可用 1可用 0禁用
  private Integer idx;
}
