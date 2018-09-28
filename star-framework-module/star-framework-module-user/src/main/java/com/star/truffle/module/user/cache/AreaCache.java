/**create by liuhua at 2018年8月20日 下午2:12:43**/
package com.star.truffle.module.user.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.star.truffle.module.user.dao.read.AreaReadDao;
import com.star.truffle.module.user.dao.write.AreaWriteDao;
import com.star.truffle.module.user.domain.Area;

@Service
public class AreaCache {

  @Autowired
  private AreaReadDao areaReadDao;
  @Autowired
  private AreaWriteDao areaWriteDao;
  
  @Cacheable(value = "module-area", key = "'area_areaId_'+#areaId", condition = "#areaId != null")
  public Area getById(Long areaId) {
    return areaReadDao.getById(areaId);
  }
  
  @Cacheable(value = "module-area-list", key = "'area_list_parentId_'+#parentId", condition = "#parentId != null")
  public List<Area> getByParentId(Long parentId){
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("parentId", parentId);
    conditions.put("status", 1);
    List<Area> areas = areaReadDao.queryArea(conditions);
    return areas;
  }
  
  @Cacheable(value = "module-area-list", key = "'area_list_type_'+#type", condition = "#type != null")
  public List<Area> getByType(Integer type){
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("type", type);
    conditions.put("status", 1);
    List<Area> areas = areaReadDao.queryArea(conditions);
    return areas;
  }

  public List<Area> getByName(Long parentId, String areaName) {
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("parentId", parentId);
    conditions.put("areaName", areaName);
    conditions.put("status", 1);
    List<Area> areas = areaReadDao.queryArea(conditions);
    return areas;
  }
  
  public List<Area> getByPinyin(Long parentId, String pinyin) {
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("parentId", parentId);
    conditions.put("pinyin", pinyin);
    conditions.put("status", 1);
    List<Area> areas = areaReadDao.queryArea(conditions);
    return areas;
  }
  
  public List<Area> queryArea(Map<String, Object> conditions){
    List<Area> areas = areaReadDao.queryArea(conditions);
    return areas;
  }
  
  public Long queryAreaCount(Map<String, Object> conditions){
    Long count = areaReadDao.queryAreaCount(conditions);
    return count;
  }

  @Caching(
    put = @CachePut(value = "module-area", key = "'area_areaId_'+#result.areaId", condition = "#result != null"),
    evict = @CacheEvict(value = "module-area-list", allEntries = true)
  )
  public Area saveArea(Area area) {
    areaWriteDao.saveArea(area);
    return area;
  }

  @Caching(
    put = @CachePut(value = "module-area", key = "'area_areaId_'+#area.areaId", condition = "#area.areaId != null"),
    evict = @CacheEvict(value = "module-area-list", allEntries = true)
  )
  public Area updateArea(Area area) {
    areaWriteDao.updateArea(area);
    return areaReadDao.getById(area.getAreaId());
  }
}
