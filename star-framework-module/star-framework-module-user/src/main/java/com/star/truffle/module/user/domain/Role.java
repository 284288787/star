/**create by liuhua at 2018年7月14日 上午10:51:36**/
package com.star.truffle.module.user.domain;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import lombok.Data;

@Data
public class Role {

  private Long roleId;           //角色id
  private String roleName;       //角色编码
  private String roleRemark;     //角色名称
  private String roleIntro;      //角色描述
  private Date createTime;       //创建时间
  private String createUser;     //创建用户
  
  public boolean checkSaveData() {
    if (null != roleId || StringUtils.isBlank(roleName) || StringUtils.isBlank(roleRemark)) {
      return false;
    }
    return true;
  }
}
