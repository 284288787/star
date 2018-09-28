/**create by liuhua at 2018年7月14日 上午10:54:06**/
package com.star.truffle.module.user.domain;

import java.util.Date;
import lombok.Data;

@Data
public class UserRoleRelation {

  private Long userId;          
  private Long roleId;
  private Date createTime;
  private String createUser;
}
