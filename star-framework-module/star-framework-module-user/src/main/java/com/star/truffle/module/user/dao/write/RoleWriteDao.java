/**create by liuhua at 2018年7月14日 上午11:07:04**/
package com.star.truffle.module.user.dao.write;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.star.truffle.module.user.domain.Role;
import com.star.truffle.module.user.domain.RoleResourceRelation;
import com.star.truffle.module.user.domain.UserRoleRelation;

public interface RoleWriteDao {

  public int saveRole(Role role);
  
  public int updateRole(Role role);
  
  public int deleteRoleResourceRelationByRoleId(Long roleId);
  
  public int deleteRoleResourceRelationByResourceId(Long sourceId);
  
  public int deleteRoleResourceRelationBy(@Param("roleId") Long roleId, @Param("sourceId") Long sourceId, @Param("uri") String uri);
  
  public int batchSaveRoleResource(List<RoleResourceRelation> roleResourceRelations);
  
  public int deleteUserRoleRelationByUserId(Long userId);
  
  public int deleteUserRoleRelationByRoleId(Long roleId);
  
  public int batchSaveUserRole(List<UserRoleRelation> userRoleRelations);

  public int updateMainUri(RoleResourceRelation relation);

  public int deleteRole(Long roleId);
}
