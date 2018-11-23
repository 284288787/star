/**create by liuhua at 2018年7月14日 上午11:13:49**/
package com.star.truffle.module.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.star.truffle.common.constants.EnabledEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.user.cache.ResourceCache;
import com.star.truffle.module.user.dao.read.UriReadDao;
import com.star.truffle.module.user.dao.write.UriWriteDao;
import com.star.truffle.module.user.domain.Resource;
import com.star.truffle.module.user.domain.Uri;
import com.star.truffle.module.user.dto.ResourceDto;
import com.star.truffle.module.user.dto.RoleResourceRelationDto;
import com.star.truffle.module.user.dto.UriDto;

@Service
public class ResourceService {

  @Autowired
  private StarJson starJson;
  @Autowired
  private ResourceCache resourceCache;
  @Autowired
  private UriWriteDao uriWriteDao;
  @Autowired
  private UriReadDao uriReadDao;
  
  public List<ResourceDto> queryResourceByRoleIds(String roleIds){
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("roleIds", roleIds);
    conditions.put("enabled", EnabledEnum.enabled.val());
    List<ResourceDto> resources = this.resourceCache.queryResource(conditions);
    return resources;
  }

  public List<RoleResourceRelationDto> queryAllRoleResource() {
    List<RoleResourceRelationDto> list = resourceCache.queryAllRoleResource();
    return list;
  }

  public List<ResourceDto> queryResource(ResourceDto resource) {
    Map<String, Object> conditions = starJson.bean2Map(resource);
    List<ResourceDto> resources = this.resourceCache.queryResource(conditions);
    return resources;
  }

  public Integer queryResourceCount(ResourceDto resource) {
    Map<String, Object> conditions = starJson.bean2Map(resource);
    Integer count = this.resourceCache.queryResourceCount(conditions);
    return count;
  }

  public void saveResourceUri(Uri uri, boolean status) {
    if (null == uri || null == uri.getSourceId() || StringUtils.isBlank(uri.getUri())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    if (! status) {
      uri.setSourceId(null);
    }
    this.uriWriteDao.updateUri(uri, "'"+uri.getUri()+"'");
  }

  public List<UriDto> queryUri(UriDto uri) {
    Map<String, Object> conditions = starJson.bean2Map(uri);
    List<UriDto> uris = uriReadDao.queryUri(conditions);
    return uris;
  }
  
  public Integer queryUriCount(UriDto uri) {
    Map<String, Object> conditions = starJson.bean2Map(uri);
    Integer count = this.uriReadDao.queryUriCount(conditions);
    return count;
  }

  public Long saveResource(ResourceDto resourceDto) {
    if (null == resourceDto || !resourceDto.checkSaveData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String idxstr = resourceDto.getIdxstr();
    int idx = 0;
    if (idxstr.indexOf("_") != -1) {
      String[] t = idxstr.split("_");
      int bidx = Integer.valueOf(t[0]);
      if ("1".equals(t[1])) { //在之前
        idx = bidx;
      } else {//在之后
        idx = bidx + 1;
      }
      Map<String, Object> conditions = new HashMap<>();
      conditions.put("parentId", resourceDto.getParentId());
      conditions.put("idx", ">= " + idx);
      conditions.put("s_orderBy", "idx");
      List<ResourceDto> list = this.resourceCache.queryResource(conditions);
      int i = idx;
      for (ResourceDto res : list) {
        res.setIdx(++ i);
        Resource resource = starJson.str2obj(starJson.obj2string(res), new TypeReference<Resource>() {});
        this.resourceCache.updateResource(resource);
      }
    }else{
      idx = Integer.valueOf(idxstr);
    }
    Resource resource = starJson.str2obj(starJson.obj2string(resourceDto), new TypeReference<Resource>() {});
    resource.setIdx(idx);
    resourceCache.saveResource(resource);
    return resource.getSourceId();
  }

  public ResourceDto getResource(Long sourceId) {
    ResourceDto resource = resourceCache.getResource(sourceId);
    return resource;
  }

  public Long editResource(ResourceDto resourceDto) {
    if (null == resourceDto || null == resourceDto.getSourceId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    ResourceDto temp = this.resourceCache.getResource(resourceDto.getSourceId());
    if (null == temp) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "修改失败，资源不存在");
    }
    String idxstr = resourceDto.getIdxstr();
    int idx = 0;
    if (idxstr.indexOf("_") != -1) {
      String[] t = idxstr.split("_");
      int bidx = Integer.valueOf(t[0]);
      if ("1".equals(t[1]) && bidx > temp.getIdx()) { //在之前
        idx = temp.getIdx();
      } else {
        idx = bidx;
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("parentId", resourceDto.getParentId());
        conditions.put("idx", ">= " + idx);
        conditions.put("s_orderBy", "idx");
        List<ResourceDto> list = this.resourceCache.queryResource(conditions);
        int i = idx;
        for (ResourceDto res : list) {
          res.setIdx(++ i);
          Resource resource = starJson.str2obj(starJson.obj2string(res), new TypeReference<Resource>() {});
          this.resourceCache.updateResource(resource);
        }
      }
    }else{
      idx = Integer.valueOf(idxstr);
    }
    Resource resource = starJson.str2obj(starJson.obj2string(resourceDto), new TypeReference<Resource>() {});
    resource.setIdx(idx);
    resourceCache.updateResource(resource);
    return resource.getSourceId();
  }

  public void deleteResource(Long sourceId) {
    if (null == sourceId) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    ResourceDto resource = resourceCache.getResource(sourceId);
    if (null == resource) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "删除失败，资源不存在");
    }
    if (resource.getChildNum() > 0) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "删除失败，存在子资源");
    }
    resourceCache.deleteResource(sourceId);
  }
  
  public String getResourceMainUri(String roleIds, Long sourceId) {
    return resourceCache.getResourceMainUri(roleIds, sourceId);
  }

  public void editUri(Uri uri) {
    this.uriWriteDao.updateUri(uri, "'"+uri.getUri()+"'");
  }
}
