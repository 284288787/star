/**create by liuhua at 2018年7月14日 上午10:58:15**/
package com.star.truffle.module.user.dao.read;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.star.truffle.module.user.dto.ResourceDto;
import com.star.truffle.module.user.dto.RoleResourceRelationDto;

public interface ResourceReadDao {

  public ResourceDto getResource(Long sourceId);
  
  public List<ResourceDto> queryResource(Map<String, Object> conditions);
  
  public Integer queryResourceCount(Map<String, Object> conditions);

  public List<RoleResourceRelationDto> queryAllRoleResource();
  
  public String getResourceMainUri(@Param("roleIds") String roleIds, @Param("sourceId") Long sourceId);
}
