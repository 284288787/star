/**create by liuhua at 2018年7月14日 上午11:05:30**/
package com.star.truffle.module.user.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.user.domain.Role;
import com.star.truffle.module.user.dto.UserRoleRelationDto;

public interface RoleReadDao {

  public Role getRole(Long roleId);
  
  public List<Role> queryRole(Map<String, Object> conditions);
  
  public Integer queryRoleCount(Map<String, Object> conditions);
  
  public List<UserRoleRelationDto> queryUserRoleRelationByUserId(Map<String, Object> conditions);
  
  public Integer queryUserRoleRelationCountByUserId(Map<String, Object> conditions);
  
  public List<UserRoleRelationDto> queryUserRoleRelation(Map<String, Object> conditions);
  
  public Integer queryUserRoleRelationCount(Map<String, Object> conditions);
}
