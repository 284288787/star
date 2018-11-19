/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.common.choosedata.ChooseDataIntf;
import com.star.truffle.common.choosedata.GridColumn;
import com.star.truffle.common.choosedata.GridPagerResponse;
import com.star.truffle.common.constants.EnabledEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.member.cache.DistributorCache;
import com.star.truffle.module.member.dto.req.DistributorRequestDto;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;
import com.star.truffle.module.product.cache.DistributionRegionCache;
import com.star.truffle.module.product.constant.DistributionRegionStateEnum;
import com.star.truffle.module.product.domain.DistributionRegion;
import com.star.truffle.module.product.dto.req.DistributionRegionRequestDto;
import com.star.truffle.module.product.dto.res.DistributionRegionResponseDto;

@Service
public class DistributionRegionService implements ChooseDataIntf {

  @Autowired
  private StarJson starJson;
  @Autowired
  private DistributionRegionCache distributionRegionCache;
  @Autowired
  private DistributorCache distributorCache;

  @Override
  public String getPrimaryKey() {
    return "regionId";
  }
  
  public List<GridColumn> getGridColumns(){
    List<GridColumn> columns = new ArrayList<>();
    columns.add(GridColumn.builder().caption("regionId").javaName("regionId").dsName("region_id").hidden(true).build());
    columns.add(GridColumn.builder().caption("区域名称").javaName("name").dsName("name").query(true).type("text").placeholder("区域名称").build());
    columns.add(GridColumn.builder().caption("所属地区").notInList(true).query(true).type("area").build());
//    columns.add(GridColumn.builder().caption("起止日期").notInList(true).query(true).type("date").build());
    columns.add(GridColumn.builder().caption("省").javaName("provinceName").dsName("province_name").build());
    columns.add(GridColumn.builder().caption("市").javaName("cityName").dsName("city_name").build());
    columns.add(GridColumn.builder().caption("区县").javaName("areaName").dsName("area_name").build());
    columns.add(GridColumn.builder().caption("乡镇/街道").javaName("townName").dsName("town_name").build());
    return columns;
  }
  
  @Override
  public GridPagerResponse getDatas(Map<String, Object> condition, Page pager) {
    DistributionRegionRequestDto distributionRegionRequestDto = starJson.str2obj(starJson.obj2string(condition), DistributionRegionRequestDto.class);
    List<DistributionRegionResponseDto> list = distributionRegionCache.queryDistributionRegion(distributionRegionRequestDto);
    Long count = distributionRegionCache.queryDistributionRegionCount(distributionRegionRequestDto);
    long total = count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1;
    return new GridPagerResponse(pager.getPageNum(), total, count, list);
  }
  
  public Long saveDistributionRegion(DistributionRegion distributionRegion) {
    if (null == distributionRegion || StringUtils.isBlank(distributionRegion.getName()) 
        || null == distributionRegion.getProvinceId() || null == distributionRegion.getCityId()
        || null == distributionRegion.getAreaId() || StringUtils.isBlank(distributionRegion.getPy())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    DistributionRegionRequestDto distributionRegionRequestDto = new DistributionRegionRequestDto();
    distributionRegionRequestDto.setPy(distributionRegion.getPy());
    Long count = this.distributionRegionCache.queryDistributionRegionCount(distributionRegionRequestDto);
    if (count > 0) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "拼音简称已经被使用，请更换");
    }
    distributionRegion.setState(DistributionRegionStateEnum.enabled.state());
    distributionRegion.setCreateTime(new Date());
    distributionRegion.setUpdateTime(distributionRegion.getCreateTime());
    distributionRegion.setFansNum(0);
    distributionRegion.setSoldNum(0);
    this.distributionRegionCache.saveDistributionRegion(distributionRegion);
    return distributionRegion.getRegionId();
  }

  public void updateDistributionRegion(DistributionRegionRequestDto distributionRegionRequestDto) {
    distributionRegionRequestDto.setUpdateTime(new Date());
    this.distributionRegionCache.updateDistributionRegion(distributionRegionRequestDto);
  }

  public void deleteDistributionRegion(Long regionId) {
    DistributionRegionResponseDto distributionRegionResponseDto = getDistributionRegion(regionId);
    if (null != distributionRegionResponseDto) {
      this.distributionRegionCache.deleteDistributionRegion(regionId, distributionRegionResponseDto.getPy());
    }
  }

  public void deleteDistributionRegion(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] regionIds = idstr.split(",");
    for (String str : regionIds) {
//      Long regionId = Long.parseLong(str);
//      this.distributionRegionCache.deleteDistributionRegion(regionId);
      DistributionRegionRequestDto dto = new DistributionRegionRequestDto();
      dto.setState(DistributionRegionStateEnum.deleted.state());
      dto.setRegionId(Long.parseLong(str));
      distributionRegionCache.updateDistributionRegion(dto);
    }
  }

  public DistributionRegionResponseDto getDistributionRegion(Long regionId) {
    DistributionRegionResponseDto distributionRegionResponseDto = this.distributionRegionCache.getDistributionRegion(regionId);
    if (null != distributionRegionResponseDto) {
      String viewAreaName = "";
      if (StringUtils.isNotBlank(distributionRegionResponseDto.getProvinceName())) {
        viewAreaName += distributionRegionResponseDto.getProvinceName();
      }
      if (StringUtils.isNotBlank(distributionRegionResponseDto.getCityName())) {
        viewAreaName += " - " + distributionRegionResponseDto.getCityName();
      }
      if (StringUtils.isNotBlank(distributionRegionResponseDto.getAreaName())) {
        viewAreaName += " - " + distributionRegionResponseDto.getAreaName();
      }
      if (StringUtils.isNotBlank(distributionRegionResponseDto.getTownName())) {
        viewAreaName += " - " + distributionRegionResponseDto.getTownName();
      }
      distributionRegionResponseDto.setViewAreaName(viewAreaName);
    }
    return distributionRegionResponseDto;
  }

  public DistributionRegionResponseDto getDistributionRegion(String py) {
    DistributionRegionResponseDto distributionRegionResponseDto = this.distributionRegionCache.getDistributionRegionByPy(py);
    if (null != distributionRegionResponseDto) {
      String viewAreaName = "";
      if (StringUtils.isNotBlank(distributionRegionResponseDto.getProvinceName())) {
        viewAreaName += distributionRegionResponseDto.getProvinceName();
      }
      if (StringUtils.isNotBlank(distributionRegionResponseDto.getCityName())) {
        viewAreaName += " - " + distributionRegionResponseDto.getCityName();
      }
      if (StringUtils.isNotBlank(distributionRegionResponseDto.getAreaName())) {
        viewAreaName += " - " + distributionRegionResponseDto.getAreaName();
      }
      if (StringUtils.isNotBlank(distributionRegionResponseDto.getTownName())) {
        viewAreaName += " - " + distributionRegionResponseDto.getTownName();
      }
      distributionRegionResponseDto.setViewAreaName(viewAreaName);
      DistributorRequestDto distributorRequestDto = new DistributorRequestDto();
      distributorRequestDto.setRegionId(distributionRegionResponseDto.getRegionId());
      distributorRequestDto.setEnabled(EnabledEnum.enabled.val());
      List<DistributorResponseDto> list = this.distributorCache.queryDistributor(distributorRequestDto);
      if (null != list && ! list.isEmpty()) {
        distributionRegionResponseDto.setDistributor(list.get(0));
      }else {
        DistributorResponseDto main = this.distributorCache.getDistributor(1L);
        distributionRegionResponseDto.setDistributor(main);
      }
    }
    
    return distributionRegionResponseDto;
  }
  
  public List<DistributionRegionResponseDto> queryDistributionRegion(DistributionRegionRequestDto distributionRegionRequestDto) {
    return this.distributionRegionCache.queryDistributionRegion(distributionRegionRequestDto);
  }

  public Long queryDistributionRegionCount(DistributionRegionRequestDto distributionRegionRequestDto) {
    return this.distributionRegionCache.queryDistributionRegionCount(distributionRegionRequestDto);
  }

  public void enabledDistributionRegion(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      DistributionRegionRequestDto dto = new DistributionRegionRequestDto();
      dto.setState(DistributionRegionStateEnum.enabled.state());
      dto.setRegionId(Long.parseLong(id));
      distributionRegionCache.updateDistributionRegion(dto);
    }
  }

  public void disabledDistributionRegion(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      DistributionRegionRequestDto dto = new DistributionRegionRequestDto();
      dto.setState(DistributionRegionStateEnum.disabled.state());
      dto.setRegionId(Long.parseLong(id));
      distributionRegionCache.updateDistributionRegion(dto);
    }
  }
}