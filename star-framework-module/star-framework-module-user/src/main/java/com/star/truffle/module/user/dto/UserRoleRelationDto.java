/**create by liuhua at 2018年7月14日 上午10:54:06**/
package com.star.truffle.module.user.dto;

import java.util.Date;
import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleRelationDto extends Page {

  private Long userId;          
  private Long roleId;
  private Date createTime;
  private String createUser;
  
  private String roleRemark;
}
