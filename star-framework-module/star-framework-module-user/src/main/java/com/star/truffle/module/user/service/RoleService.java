/** create by liuhua at 2018年7月14日 上午11:17:02 **/
package com.star.truffle.module.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.user.cache.RoleCache;
import com.star.truffle.module.user.domain.Role;
import com.star.truffle.module.user.domain.RoleResourceRelation;
import com.star.truffle.module.user.domain.UserRoleRelation;
import com.star.truffle.module.user.dto.RoleDto;
import com.star.truffle.module.user.dto.UserRoleRelationDto;

@Service
public class RoleService {

  @Autowired
  private RoleCache roleCache;
  @Autowired
  private StarJson starJson;
  @Autowired
  private SourceMap sourceMap;

  public List<Role> queryRoleByUserIds(String userIds) {
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("userIds", userIds);
    List<Role> roles = roleCache.queryRole(conditions);
    return roles;
  }

  public List<UserRoleRelationDto> queryUserRoleRelationByUserId(UserRoleRelationDto userRoleRelationDto) {
    Map<String, Object> conditions = starJson.bean2Map(userRoleRelationDto);
    List<UserRoleRelationDto> relations = this.roleCache.queryUserRoleRelationByUserId(conditions);
    return relations;
  }

  public Integer queryUserRoleRelationCountByUserId(UserRoleRelationDto userRoleRelationDto) {
    Map<String, Object> conditions = starJson.bean2Map(userRoleRelationDto);
    Integer count = this.roleCache.queryUserRoleRelationCountByUserId(conditions);
    return count;
  }

  public void setUserRoles(Long userId, String roleIds) {
    if (null == userId) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    this.roleCache.deleteUserRoleRelationByUserId(userId);
    if (StringUtils.isNotBlank(roleIds)) {
      String createUser = "无登录测试";
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (null != auth) {
        createUser = auth.getName();
      }
      String roleId[] = roleIds.split(",");
      List<UserRoleRelation> relations = new ArrayList<>();
      for (String rId : roleId) {
        Long roId = Long.parseLong(rId);
        Role role = roleCache.getRole(roId);
        if (null == role) {
          throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "角色不存在");
        }
        UserRoleRelation relation = new UserRoleRelation();
        relation.setCreateTime(new Date());
        relation.setCreateUser(createUser);
        relation.setRoleId(roId);
        relation.setUserId(userId);
        relations.add(relation);
      }
      this.roleCache.batchSaveUserRoleRelation(relations);
    }
  }

  public List<Role> queryRole(RoleDto roleDto) {
    Map<String, Object> conditions = starJson.bean2Map(roleDto);
    List<Role> roles = this.roleCache.queryRole(conditions);
    return roles;
  }

  public Integer queryRoleCount(RoleDto roleDto) {
    Map<String, Object> conditions = starJson.bean2Map(roleDto);
    Integer count = this.roleCache.queryRoleCount(conditions);
    return count;
  }

  public Role getRole(Long roleId) {
    Role role = this.roleCache.getRole(roleId);
    return role;
  }

  public Long saveRole(Role role) {
    if (null == role || !role.checkSaveData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("roleRemark", role.getRoleRemark());
    List<Role> roles = this.roleCache.queryRole(conditions);
    if (null != roles && ! roles.isEmpty()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "角色已经存在");
    }
    String createUser = "无登录测试";
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (null != auth) {
      createUser = auth.getName();
    }
    role.setCreateUser(createUser);
    role.setCreateTime(new Date());
    roleCache.saveRole(role);
    return role.getRoleId();
  }

  public void updateRole(Role role) {
    if (null == role || null == role.getRoleId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    roleCache.updateRole(role);
  }

  public void saveRoleResourceRelation(RoleResourceRelation relation, boolean status) {
    if (null == relation || null == relation.getRoleId() || null == relation.getSourceId() || StringUtils.isBlank(relation.getUri())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    if (status) {
      String createUser = "无登录测试";
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (null != auth) {
        createUser = auth.getName();
      }
      relation.setCreateTime(new Date());
      relation.setCreateUser(createUser);
      relation.setMainUri(0);
      this.roleCache.batchSaveRoleResource(Arrays.asList(relation));
    }else{
      this.roleCache.deleteRoleResourceRelationBy(relation.getRoleId(), relation.getSourceId(), relation.getUri());
    }
    sourceMap.initResourceMap();
  }

  public void setMainUri(RoleResourceRelation relation) {
    if (null == relation || null == relation.getRoleId() || null == relation.getSourceId() || StringUtils.isBlank(relation.getUri())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    this.roleCache.updateMainUri(relation);
  }

  public void deleteRole(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      Long roleId = Long.parseLong(id);
      Role role = roleCache.getRole(roleId);
      if (null == role) {
        throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "删除失败，角色不存在");
      }
      Map<String, Object> conditions = new HashMap<>();
      conditions.put("roleId", roleId);
      Integer count = roleCache.queryUserRoleRelationCount(conditions);
      if (count > 0) {
        throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "删除失败，角色[" + role.getRoleRemark() + "]已经指定给用户");
      }
      roleCache.deleteRole(roleId);
      roleCache.deleteRoleResourceRelationByRoleId(roleId);
    }
  
  }
}
