/**create by liuhua at 2018年7月14日 上午11:08:13**/
package com.star.truffle.module.user.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.common.constants.EnabledEnum;
import com.star.truffle.module.user.constant.UserConstants;
import com.star.truffle.module.user.dao.read.ResourceReadDao;
import com.star.truffle.module.user.dao.write.ResourceWriteDao;
import com.star.truffle.module.user.domain.Resource;
import com.star.truffle.module.user.dto.ResourceDto;
import com.star.truffle.module.user.dto.RoleResourceRelationDto;

@Service
public class ResourceCache {

  @Autowired
  private ResourceWriteDao resourceWriteDao;
  @Autowired
  private ResourceReadDao resourceReadDao;
  
  public Resource saveResource(Resource resource){
    resource.setSourceId(UserConstants.idWorker.nextId());
    resource.setEnabled(EnabledEnum.enabled.val());
    this.resourceWriteDao.saveResource(resource);
    return null;
  }
  
  public Resource updateResource(Resource resource){
    this.resourceWriteDao.updateResource(resource);
    return null;
  }
  
  public ResourceDto getResource(Long sourceId){
    ResourceDto resource = this.resourceReadDao.getResource(sourceId);
    return resource;
  }
  
  public List<ResourceDto> queryResource(Map<String, Object> conditions){
    List<ResourceDto> resources = this.resourceReadDao.queryResource(conditions);
    return resources;
  }
  
  public Integer queryResourceCount(Map<String, Object> conditions){
    Integer count = this.resourceReadDao.queryResourceCount(conditions);
    return count;
  }

  public List<RoleResourceRelationDto> queryAllRoleResource() {
    return this.resourceReadDao.queryAllRoleResource();
  }
  
  public void deleteResource(Long sourceId) {
    resourceWriteDao.deleteResource(sourceId);
  }

  public String getResourceMainUri(String roleIds, Long sourceId) {
    return resourceReadDao.getResourceMainUri(roleIds, sourceId);
  }
}
