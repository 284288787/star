/**create by liuhua at 2018年7月14日 上午11:03:25**/
package com.star.truffle.module.user.dao.write;

import com.star.truffle.module.user.domain.Resource;

public interface ResourceWriteDao {

  public int saveResource(Resource resource);
  
  public int updateResource(Resource resource);

  public int deleteResource(Long sourceId);
}
