/**create by liuhua at 2018年9月4日 上午10:56:38**/
package com.star.truffle.module.user.dao.write;

import com.star.truffle.module.user.domain.Area;

public interface AreaWriteDao {

  public int saveArea(Area area);
  
  public int updateArea(Area area);

  public int deleteArea(Long areaId);
}
