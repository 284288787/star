/**create by liuhua at 2018年7月14日 上午10:51:36**/
package com.star.truffle.module.user.dto;

import java.util.Date;
import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto extends Page {

  private Long roleId;           //角色id
  private String roleName;       //角色编码
  private String roleRemark;     //角色名称
  private String roleIntro;      //角色描述
  private Date createTime;       //创建时间
  private String createUser;     //创建用户
}
