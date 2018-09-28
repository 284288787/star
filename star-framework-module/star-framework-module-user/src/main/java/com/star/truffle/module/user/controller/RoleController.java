/** create by liuhua at 2018年7月12日 上午10:47:38 **/
package com.star.truffle.module.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.star.truffle.common.constants.EnabledEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.user.domain.Role;
import com.star.truffle.module.user.domain.RoleResourceRelation;
import com.star.truffle.module.user.dto.ResourceDto;
import com.star.truffle.module.user.dto.RoleDto;
import com.star.truffle.module.user.dto.UriDto;
import com.star.truffle.module.user.dto.UserRoleRelationDto;
import com.star.truffle.module.user.service.ResourceService;
import com.star.truffle.module.user.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

  @Autowired
  private RoleService roleService;
  @Autowired
  private ResourceService resourceService;
  
  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/role/list";
  }
  
  @ResponseBody
  @RequestMapping(value = "/resources/{roleId}", method = {RequestMethod.POST, RequestMethod.GET})
  public List<Map<String, Object>> resources(@PathVariable Long roleId, Long parentId, String type, Boolean isLeaf) {
    List<Map<String, Object>> list = new ArrayList<>();
    if (null != isLeaf && isLeaf) {
      UriDto uri = new UriDto();
      uri.setSourceId(parentId);
      uri.setOrderBy("uri");
      uri.setRoleId(roleId);
      List<UriDto> uriDtos = resourceService.queryUri(uri);
      for (UriDto uriDto : uriDtos) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", uriDto.getUri());
        map.put("pId", uriDto.getSourceId().toString());
        map.put("name", StringUtils.isBlank(uriDto.getIntro()) ? uriDto.getUri() : uriDto.getIntro());
        map.put("uri", uriDto.getUri());
        map.put("type", "uri");
        map.put("isParent", false);
        map.put("checked", uriDto.getRoleNum() > 0);
        list.add(map);
      }
    } else {
      ResourceDto resource = new ResourceDto();
      resource.setParentId(parentId);
      resource.setEnabled(EnabledEnum.enabled.val());
      if (null == resource.getParentId()) {
        resource.setParentId(0L);
      }
      resource.setRoleId(roleId);
      List<ResourceDto> resources = resourceService.queryResource(resource);
      for (ResourceDto resourceDto : resources) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", resourceDto.getSourceId().toString());
        map.put("pId", resourceDto.getParentId().toString());
        map.put("name", resourceDto.getSourceName());
        map.put("type", "resource");
        map.put("isParent", true);
        map.put("isLeaf", resourceDto.getChildNum() == 0);
        map.put("open", true);
        map.put("checked", resourceDto.getRoleNum() > 0);
        list.add(map);
      }
    }
    return list;
  }
  
  @ResponseBody
  @RequestMapping(value = "/viewResource/{roleId}", method = {RequestMethod.POST, RequestMethod.GET})
  public List<Map<String, Object>> viewResource(@PathVariable Long roleId, Long parentId, String type, Boolean isLeaf) {
    List<Map<String, Object>> list = new ArrayList<>();
    if (null != isLeaf && isLeaf) {
      UriDto uri = new UriDto();
      uri.setSourceId(parentId);
      uri.setOrderBy("uri");
      uri.setRoleId(roleId);
      List<UriDto> uriDtos = resourceService.queryUri(uri);
      for (UriDto uriDto : uriDtos) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", uriDto.getUri());
        map.put("pId", uriDto.getSourceId().toString());
        map.put("name", (uriDto.getMainUri() != 0 ? "[<span style='color:red;font-weight:bold'>主</span>]" : "") + (StringUtils.isBlank(uriDto.getIntro()) ? uriDto.getUri() : uriDto.getIntro()));
        map.put("uri", uriDto.getUri());
        map.put("type", "uri");
        map.put("isParent", false);
        boolean checked = uriDto.getRoleNum() > 0;
        if (checked) {
          list.add(map);
        }
      }
    } else {
      ResourceDto resource = new ResourceDto();
      resource.setParentId(parentId);
      resource.setEnabled(EnabledEnum.enabled.val());
      if (null == resource.getParentId()) {
        resource.setParentId(0L);
      }
      resource.setRoleId(roleId);
      List<ResourceDto> resources = resourceService.queryResource(resource);
      for (ResourceDto resourceDto : resources) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", resourceDto.getSourceId().toString());
        map.put("pId", resourceDto.getParentId().toString());
        map.put("name", resourceDto.getSourceName());
        map.put("type", "resource");
        map.put("isParent", true);
        map.put("isLeaf", resourceDto.getChildNum() == 0);
        map.put("open", true);
        boolean checked = resourceDto.getRoleNum() > 0;
        if (checked) {
          list.add(map);
        }
      }
    }
    return list;
  }

  @ResponseBody
  @RequestMapping(value = "/mainUri/{roleId}", method = {RequestMethod.POST, RequestMethod.GET})
  public List<Map<String, Object>> mainUri(@PathVariable Long roleId, Long parentId, String type, Boolean isLeaf) {
    List<Map<String, Object>> list = new ArrayList<>();
    if (null != isLeaf && isLeaf) {
      UriDto uri = new UriDto();
      uri.setSourceId(parentId);
      uri.setOrderBy("uri");
      uri.setRoleId(roleId);
      List<UriDto> uriDtos = resourceService.queryUri(uri);
      for (UriDto uriDto : uriDtos) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", uriDto.getUri());
        map.put("pId", uriDto.getSourceId().toString());
        map.put("name", StringUtils.isBlank(uriDto.getIntro()) ? uriDto.getUri() : uriDto.getIntro());
        map.put("uri", uriDto.getUri());
        map.put("type", "uri");
        map.put("isParent", false);
        boolean checked = uriDto.getRoleNum() > 0;
        map.put("checked", uriDto.getMainUri() != 0);
        if (checked) {
          list.add(map);
        }
      }
    } else {
      ResourceDto resource = new ResourceDto();
      resource.setParentId(parentId);
      resource.setEnabled(EnabledEnum.enabled.val());
      if (null == resource.getParentId()) {
        resource.setParentId(0L);
      }
      resource.setRoleId(roleId);
      List<ResourceDto> resources = resourceService.queryResource(resource);
      for (ResourceDto resourceDto : resources) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", resourceDto.getSourceId().toString());
        map.put("pId", resourceDto.getParentId().toString());
        map.put("name", resourceDto.getSourceName());
        map.put("type", "resource");
        map.put("isParent", true);
        map.put("isLeaf", resourceDto.getChildNum() == 0);
        map.put("open", true);
        map.put("nocheck", true);
        boolean checked = resourceDto.getRoleNum() > 0;
        if (checked) {
          list.add(map);
        }
      }
    }
    return list;
  }
  
  @ResponseBody
  @RequestMapping(value = "/saveRoleResourceRelation", method = RequestMethod.POST)
  public ApiResult<Void> saveRoleResourceRelation(RoleResourceRelation relation, boolean status) {
    ApiResult<Void> apiResult = null;
    try {
      this.roleService.saveRoleResourceRelation(relation, status);
      apiResult = ApiResult.success();
    } catch (StarServiceException e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
    return apiResult;
  }
  
  @ResponseBody
  @RequestMapping(value = "/setMainUri", method = RequestMethod.POST)
  public ApiResult<Void> setMainUri(RoleResourceRelation relation) {
    ApiResult<Void> apiResult = null;
    try {
      this.roleService.setMainUri(relation);
      apiResult = ApiResult.success();
    } catch (StarServiceException e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
    return apiResult;
  }
  
  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(RoleDto roleDto, Integer page, Integer rows, String sord, String sidx) {
    roleDto.setPageNum(page);
    roleDto.setPageSize(rows);
    roleDto.setOrderBy(sidx);
    roleDto.setOrderType(OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    List<Role> userAccounts = roleService.queryRole(roleDto);
    Integer total = roleService.queryRoleCount(roleDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", page);
    map.put("total", total % roleDto.getPageSize() == 0 ? total / roleDto.getPageSize() : total / roleDto.getPageSize() + 1);
    map.put("records", total);
    map.put("rows", userAccounts);
    return map;
  }
  
  @RequestMapping(value = "/editBefore/{roleId}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long roleId, Model model) {
    Role role = this.roleService.getRole(roleId);
    model.addAttribute("role", role);
    return "mgr/role/editRole";
  }
  
  @ResponseBody
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public ApiResult<Void> edit(Role role) {
    ApiResult<Void> apiResult = null;
    try {
      this.roleService.updateRole(role);
      apiResult = ApiResult.success();
    } catch (StarServiceException e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
    return apiResult;
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Void> add(Role role) {
    ApiResult<Void> apiResult = null;
    try {
      this.roleService.saveRole(role);
      apiResult = ApiResult.success();
    } catch (StarServiceException e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
    return apiResult;
  }
  
  @ResponseBody
  @RequestMapping(value = "/setRole/{userId}", method = RequestMethod.POST)
  public Map<String, Object> setRole(@PathVariable Long userId, UserRoleRelationDto userRoleRelationDto, Integer page, Integer rows, String sord, String sidx) {
    userRoleRelationDto.setPageNum(page);
    userRoleRelationDto.setPageSize(rows);
    userRoleRelationDto.setOrderBy(sidx);
    userRoleRelationDto.setOrderType(OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    userRoleRelationDto.setUserId(userId);
    List<UserRoleRelationDto> userAccounts = roleService.queryUserRoleRelationByUserId(userRoleRelationDto);
    Integer total = roleService.queryUserRoleRelationCountByUserId(userRoleRelationDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", page);
    map.put("total", total % userRoleRelationDto.getPageSize() == 0 ? total / userRoleRelationDto.getPageSize() : total / userRoleRelationDto.getPageSize() + 1);
    map.put("records", total);
    map.put("rows", userAccounts);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/viewRole/{userId}", method = RequestMethod.POST)
  public ApiResult<List<UserRoleRelationDto>> viewRole(@PathVariable Long userId) {
    try {
      UserRoleRelationDto userRoleRelationDto = new UserRoleRelationDto();
      userRoleRelationDto.setUserId(userId);
      List<UserRoleRelationDto> userRoles = roleService.queryUserRoleRelationByUserId(userRoleRelationDto);
      List<UserRoleRelationDto> result = new ArrayList<>();
      for (UserRoleRelationDto urr : userRoles) {
        if (null != urr.getUserId() && urr.getUserId() == userId.longValue()) {
          result.add(urr);
        }
      }
      return ApiResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return ApiResult.fail();
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/saveRoleRelation", method = RequestMethod.POST)
  public ApiResult<Void> saveRoleRelation(Long userId, String roleIds) {
    ApiResult<Void> resultMessage = null;
    try {
      this.roleService.setUserRoles(userId, roleIds);
      resultMessage = ApiResult.success();
    } catch (StarServiceException e) {
      e.printStackTrace();
      resultMessage = ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      e.printStackTrace();
      resultMessage = ApiResult.fail();
    }
    return resultMessage;
  }

  @ResponseBody
  @RequestMapping(value = "/deleted", method = RequestMethod.POST)
  public ApiResult<Void> deleted(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.roleService.deleteRole(ids);
      apiResult = ApiResult.success();
    } catch (StarServiceException e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
    return apiResult;
  }
}
