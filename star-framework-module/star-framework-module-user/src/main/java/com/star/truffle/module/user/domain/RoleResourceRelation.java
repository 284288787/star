/**create by liuhua at 2018年7月14日 上午10:52:50**/
package com.star.truffle.module.user.domain;

import java.util.Date;
import lombok.Data;

@Data
public class RoleResourceRelation {

  private Long roleId;
  private Long sourceId;
  private String uri;          //菜单则为menu
  private Integer mainUri;     //是否为主uri 1是 0否 一个菜单只需要设置一个就够，如果有多个则取第一个
  private Date createTime;
  private String createUser;
}
