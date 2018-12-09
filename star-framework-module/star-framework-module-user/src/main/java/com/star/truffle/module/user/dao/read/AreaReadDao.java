/**create by liuhua at 2018年8月17日 上午9:49:39**/
package com.star.truffle.module.user.dao.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.user.domain.Area;

public interface AreaReadDao {

  public Area getById(Long areaId);
  
  public List<Area> queryArea(Map<String, Object> conditions);
  
  public Long queryAreaCount(Map<String, Object> conditions);

  public Long getAreaByAreaNames(@Param("provinceName") String provinceName, @Param("cityName") String cityName, @Param("areaName") String areaName, @Param("townName") String townName);
}
