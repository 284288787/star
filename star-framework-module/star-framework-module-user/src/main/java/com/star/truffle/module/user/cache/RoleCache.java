/**create by liuhua at 2018年7月14日 上午11:08:13**/
package com.star.truffle.module.user.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.module.user.constant.UserConstants;
import com.star.truffle.module.user.dao.read.RoleReadDao;
import com.star.truffle.module.user.dao.write.RoleWriteDao;
import com.star.truffle.module.user.domain.Role;
import com.star.truffle.module.user.domain.RoleResourceRelation;
import com.star.truffle.module.user.domain.UserRoleRelation;
import com.star.truffle.module.user.dto.UserRoleRelationDto;

@Service
public class RoleCache {

  @Autowired
  private RoleWriteDao roleWriteDao;
  @Autowired
  private RoleReadDao roleReadDao;
  
  public Role saveRole(Role role){
    role.setRoleId(UserConstants.idWorker.nextId());
    this.roleWriteDao.saveRole(role);
    return null;
  }
  
  public Role updateRole(Role role){
    this.roleWriteDao.updateRole(role);
    return null;
  }
  
  public Role getRole(Long roleId){
    Role role = this.roleReadDao.getRole(roleId);
    return role;
  }
  
  public List<Role> queryRole(Map<String, Object> conditions){
    List<Role> roles = this.roleReadDao.queryRole(conditions);
    return roles;
  }
  
  public Integer queryRoleCount(Map<String, Object> conditions){
    Integer count = this.roleReadDao.queryRoleCount(conditions);
    return count;
  }

  public List<UserRoleRelationDto> queryUserRoleRelationByUserId(Map<String, Object> conditions) {
    List<UserRoleRelationDto> relations = this.roleReadDao.queryUserRoleRelationByUserId(conditions);
    return relations;
  }

  public Integer queryUserRoleRelationCountByUserId(Map<String, Object> conditions) {
    Integer count = this.roleReadDao.queryUserRoleRelationCountByUserId(conditions);
    return count;
  }
  
  public void deleteUserRoleRelationByUserId(Long userId){
    roleWriteDao.deleteUserRoleRelationByUserId(userId);
  }

  public void batchSaveUserRoleRelation(List<UserRoleRelation> relations) {
    roleWriteDao.batchSaveUserRole(relations);
  }
  
  public void batchSaveRoleResource(List<RoleResourceRelation> roleResourceRelations) {
    roleWriteDao.batchSaveRoleResource(roleResourceRelations);
  }

  public void deleteRoleResourceRelationBy(Long roleId, Long sourceId, String uri) {
    roleWriteDao.deleteRoleResourceRelationBy(roleId, sourceId, uri);
  }

  public void updateMainUri(RoleResourceRelation relation) {
    roleWriteDao.updateMainUri(relation);
  }

  public Integer queryUserRoleRelationCount(Map<String, Object> conditions) {
    return roleReadDao.queryUserRoleRelationCount(conditions);
  }

  public void deleteRole(Long roleId) {
    roleWriteDao.deleteRole(roleId);
  }

  public void deleteRoleResourceRelationByRoleId(Long roleId) {
    roleWriteDao.deleteRoleResourceRelationByRoleId(roleId);
  }
}
