/**create by liuhua at 2018年8月20日 下午2:31:46**/
package com.star.truffle.module.user.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.user.cache.AreaCache;
import com.star.truffle.module.user.domain.Area;
import com.star.truffle.module.user.dto.AreaDto;

@Service
public class AreaService {

  private List<Area> areas;
  
  @Autowired
  private StarJson starJson;
  @Autowired
  private AreaCache areaCache;

  public Area getAreaByAreaId(Long areaId) {
    return areaCache.getById(areaId);
  }

  public List<Area> getAreasByParentId(Long parentId) {
    return areaCache.getByParentId(parentId);
  }

  public List<Area> getAreasByType(Integer type) {
    return areaCache.getByType(type);
  }

  public List<Area> getAreasByName(Long parentId, String areaName) {
    return areaCache.getByName(parentId, areaName);
  }

  public List<Area> getAreasByPinyin(Long parentId, String pinyin) {
    return areaCache.getByPinyin(parentId, pinyin);
  }

  public List<Area> queryArea(AreaDto areaDto) {
    Map<String, Object> conditions = starJson.bean2Map(areaDto);
    return areaCache.queryArea(conditions);
  }

  public Long queryAreaCount(AreaDto areaDto) {
    Map<String, Object> conditions = starJson.bean2Map(areaDto);
    return areaCache.queryAreaCount(conditions);
  }

  public List<Area> allProvinceAreas() {
    if (null != areas) {
      return areas;
    }
    List<Area> provinces = this.areaCache.getByType(3);
    List<Area> zxs = this.areaCache.getByType(1);
    List<Area> gat = this.areaCache.getByType(2);
    for (Area province : provinces) {
      List<Area> citys = this.areaCache.getByParentId(province.getAreaId());
      province.setChildren(citys);
    }
    Area zx = new Area();
    zx.setShortName("直辖市");
    zx.setType(3);
    zx.setChildren(zxs);
    Area ga = new Area();
    ga.setShortName("港澳台");
    ga.setType(3);
    ga.setChildren(gat);
    provinces.add(0, zx);
    provinces.add(ga);
    areas = provinces;
    return provinces;
  }

  public void saveArea(Area area) {
    if (null == area || null != area.getAreaId() || null == area.getParentId() 
        || StringUtils.isBlank(area.getShortName()) || StringUtils.isBlank(area.getAreaName())
        || StringUtils.isBlank(area.getPinyin()) || StringUtils.isBlank(area.getPy())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    Area parent = areaCache.getById(area.getParentId());
    if (null == parent || parent.getStatus() == 0 || parent.getType() < 4) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "父地区不存在或已禁用或只能给市下添加地区");
    }
    List<Area> list = areaCache.getByName(area.getParentId(), area.getAreaName());
    if (null != list && ! list.isEmpty()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "地区已存在");
    }
    area.setType(parent.getType() + 1);
    this.areaCache.saveArea(area);
  }
  
  public void enabledArea(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      Area area = new Area();
      area.setAreaId(Long.parseLong(id));
      area.setStatus(1);
      areaCache.updateArea(area);
    }
  }

  public void disabledArea(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      Area area = new Area();
      area.setAreaId(Long.parseLong(id));
      area.setStatus(0);
      areaCache.updateArea(area);
    }
  }

  public void deleteArea(String ids) {
    throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "删除功能未开放");
  }
}
