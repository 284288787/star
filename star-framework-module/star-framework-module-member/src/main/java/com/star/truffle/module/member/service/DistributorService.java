/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.star.truffle.module.alibaba.service.SmsIdentityService;
import com.star.truffle.module.member.cache.DistributorCache;
import com.star.truffle.module.member.constant.LoginStateEnum;
import com.star.truffle.module.member.dto.req.DistributorRequestDto;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;

@Service
public class DistributorService implements ChooseDataIntf {

  @Autowired
  private StarJson starJson;
  @Autowired
  private DistributorCache distributorCache;
  @Autowired
  private SmsIdentityService smsIdentityService;

  @Override
  public String getPrimaryKey() {
    return "distributorId";
  }
  
  @Override
  public GridPagerResponse getDatas(Map<String, Object> condition, Page pager) {
    DistributorRequestDto distributorRequestDto = starJson.str2obj(starJson.obj2string(condition), DistributorRequestDto.class);
    List<DistributorResponseDto> list = this.distributorCache.queryDistributor(distributorRequestDto);
    Long count = distributorCache.queryDistributorCount(distributorRequestDto);
    long total = count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1;
    return new GridPagerResponse(pager.getPageNum(), total, count, list);
  }

  @Override
  public List<GridColumn> getGridColumns() {
    List<GridColumn> columns = new ArrayList<>();
    columns.add(GridColumn.builder().caption("所属分销区域").notInList(true).query(true).type("choose").service("distributionRegionService").javaName("regionId").viewName("regionName").jsName("regionId").jsViewName("name").build());
    columns.add(GridColumn.builder().caption("分销商姓名").javaName("name").dsName("name").query(true).type("text").placeholder("分销商姓名").build());
    columns.add(GridColumn.builder().caption("distributorId").javaName("distributorId").dsName("distributor_id").hidden(true).build());
    columns.add(GridColumn.builder().caption("店铺名称").javaName("shopName").dsName("shop_name").build());
    columns.add(GridColumn.builder().caption("店铺编码").javaName("shopCode").dsName("shop_code").build());
    columns.add(GridColumn.builder().caption("分销区域").javaName("regionName").dsName("region_name").build());
    columns.add(GridColumn.builder().caption("街道地址").javaName("address").dsName("address").build());
    return columns;
  }
  
  public Long saveDistributor(DistributorRequestDto distributor) {
    if (null == distributor || ! distributor.checkSaveData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    DistributorResponseDto dto = distributorCache.getDistributorByMobile(distributor.getMobile());
    if (null != dto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "该手机号用户已经是分销商");
    }
    DistributorRequestDto distributorRequestDto = new DistributorRequestDto();
    distributorRequestDto.setRegionId(distributor.getRegionId());
    distributorRequestDto.setEnabled(EnabledEnum.enabled.val());
    Long count = distributorCache.queryDistributorCount(distributorRequestDto);
    if (count > 0) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "该区域下已经存在分销商，若还是需要添加，请先禁用该区域的老分销商");
    }
    distributor.setShopCode("");
    distributor.setEnabled(EnabledEnum.enabled.val());
    distributor.setCreateTime(new Date());
    distributor.setUpdateTime(distributor.getCreateTime());
    distributor.setFansNum(0);
    distributor.setSoldNum(0);
    distributor.setState(1);
    this.distributorCache.saveDistributor(distributor);
    distributor.setShopCode(String.valueOf(distributor.getDistributorId() + 500000));
    this.distributorCache.updateDistributor(distributor);
    return distributor.getDistributorId();
  }
  
  public void updateDistributor(DistributorRequestDto distributorRequestDto) {
    if (null == distributorRequestDto || ! distributorRequestDto.checkUpdateData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    distributorRequestDto.setUpdateTime(new Date());
    this.distributorCache.updateDistributor(distributorRequestDto);
  }

  public void updateDistributorHead(DistributorRequestDto distributorRequestDto) {
    if (null == distributorRequestDto || null == distributorRequestDto.getDistributorId() || StringUtils.isBlank(distributorRequestDto.getHead())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    distributorRequestDto.setUpdateTime(new Date());
    this.distributorCache.updateDistributor(distributorRequestDto);
  }

  public void deleteDistributor(Long distributorId) {
    DistributorResponseDto distributorResponseDto = this.distributorCache.getDistributor(distributorId);
    if (null == distributorResponseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "用户不存在");
    }
    this.distributorCache.deleteDistributor(distributorId, distributorResponseDto.getMobile(), distributorResponseDto.getOpenId());
  }

  public void deleteDistributor(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] distributorIds = idstr.split(",");
    for (String str : distributorIds) {
      Long distributorId = Long.parseLong(str);
      DistributorResponseDto distributorResponseDto = this.distributorCache.getDistributor(distributorId);
      if (null != distributorResponseDto) {
        this.distributorCache.deleteDistributor(distributorId, distributorResponseDto.getMobile(), distributorResponseDto.getOpenId());
      }
    }
  }

  public DistributorResponseDto getDistributor(Long distributorId) {
    DistributorResponseDto distributorResponseDto = this.distributorCache.getDistributor(distributorId);
    if (null != distributorResponseDto) {
      String fullAreaName = "";
      Map<String, Long> ids = new HashMap<>();
      if (StringUtils.isNotBlank(distributorResponseDto.getProvinceName())) {
        fullAreaName = distributorResponseDto.getProvinceName();
        ids.put("provinceId", distributorResponseDto.getProvinceId());
      }
      if (StringUtils.isNotBlank(distributorResponseDto.getCityName())) {
        fullAreaName += "-" + distributorResponseDto.getCityName();
        ids.put("cityId", distributorResponseDto.getCityId());
      }
      if (StringUtils.isNotBlank(distributorResponseDto.getAreaName())) {
        fullAreaName += "-" + distributorResponseDto.getAreaName();
        ids.put("areaId", distributorResponseDto.getAreaId());
      }
      if (StringUtils.isNotBlank(distributorResponseDto.getTownName())) {
        fullAreaName += "-" + distributorResponseDto.getTownName();
        ids.put("townId", distributorResponseDto.getTownId());
      }
      distributorResponseDto.setFullAreaName(fullAreaName);
      distributorResponseDto.setFullAreaJson(starJson.obj2string(ids));
    }
    return distributorResponseDto;
  }

  public List<DistributorResponseDto> queryDistributor(DistributorRequestDto distributorRequestDto) {
    return this.distributorCache.queryDistributor(distributorRequestDto);
  }

  public Long queryDistributorCount(DistributorRequestDto distributorRequestDto) {
    return this.distributorCache.queryDistributorCount(distributorRequestDto);
  }

  public void disabledDistributor(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] distributorIds = idstr.split(",");
    for (String str : distributorIds) {
      Long distributorId = Long.parseLong(str);
      DistributorRequestDto dto = new DistributorRequestDto();
      dto.setDistributorId(distributorId);
      dto.setEnabled(EnabledEnum.disabled.val());
      this.distributorCache.updateDistributor(dto);
    }
  }
  
  public void enabledDistributor(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] distributorIds = idstr.split(",");
    for (String str : distributorIds) {
      Long distributorId = Long.parseLong(str);
      DistributorResponseDto distributor = this.distributorCache.getDistributor(distributorId);
      if (null == distributor) {
        continue;
      }
      DistributorRequestDto distributorRequestDto = new DistributorRequestDto();
      distributorRequestDto.setRegionId(distributor.getRegionId());
      distributorRequestDto.setEnabled(EnabledEnum.enabled.val());
      Long count = distributorCache.queryDistributorCount(distributorRequestDto);
      if (count > 0) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "该区域下已经存在分销商，若还是需要启用，请先禁用老分销商");
      }
      DistributorRequestDto dto = new DistributorRequestDto();
      dto.setDistributorId(distributorId);
      dto.setEnabled(EnabledEnum.enabled.val());
      this.distributorCache.updateDistributor(dto);
    }
  }

  public DistributorResponseDto login(DistributorRequestDto distributorRequestDto) {
    if (null == distributorRequestDto || StringUtils.isBlank(distributorRequestDto.getMobile()) || StringUtils.isBlank(distributorRequestDto.getOpenId()) 
        || StringUtils.isBlank(distributorRequestDto.getCode()) || null == distributorRequestDto.getTag() || StringUtils.isBlank(distributorRequestDto.getName()) ) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    smsIdentityService.verify(distributorRequestDto.getMobile(), distributorRequestDto.getTag(), distributorRequestDto.getCode());
    DistributorResponseDto dto = distributorCache.getDistributorByMobile(distributorRequestDto.getMobile());
    if (null == dto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "用户不存在");
    }
    if (dto.getEnabled() == EnabledEnum.disabled.val()) {
      throw new StarServiceException(ApiCode.NO_LOGIN, "已被取消分销商身份");
    }
    boolean update = false;
    DistributorRequestDto distributor = new DistributorRequestDto();
    distributor.setDistributorId(dto.getDistributorId());
    if (StringUtils.isBlank(dto.getHead())) {
      dto.setHead(distributorRequestDto.getHead());
      distributor.setHead(distributorRequestDto.getHead());
      update = true;
    }
    if (StringUtils.isBlank(dto.getOpenId())) {
      dto.setOpenId(distributorRequestDto.getOpenId());
      distributor.setOpenId(distributorRequestDto.getOpenId());
      update = true;
    }
    if (dto.getState() == LoginStateEnum.logout.getState()) {
      dto.setState(LoginStateEnum.login.getState());
      distributor.setState(LoginStateEnum.login.getState());
      update = true;
    }
    if (update) {
      this.distributorCache.updateDistributor(distributor);
    }
    return dto;
  }

  public void logout(String openId) {
    DistributorResponseDto dto = distributorCache.getDistributorByOpenId(openId);
    if (null != dto) {
      DistributorRequestDto distributor = new DistributorRequestDto();
      distributor.setDistributorId(dto.getDistributorId());
      distributor.setState(LoginStateEnum.logout.getState());
      this.distributorCache.updateDistributor(distributor);
    }
  }

  public DistributorResponseDto getDistributorByOpenId(String openId) {
    DistributorResponseDto distributorResponseDto = distributorCache.getDistributorByOpenId(openId);
    if (null == distributorResponseDto) {
      throw new StarServiceException(ApiCode.NO_EXISTS);
    }
    if (distributorResponseDto.getEnabled() == EnabledEnum.disabled.val()) {
      throw new StarServiceException(ApiCode.NO_LOGIN, "已被取消分销商身份");
    }
    if (distributorResponseDto.getState() == LoginStateEnum.logout.getState()) {
      throw new StarServiceException(ApiCode.NO_LOGIN);
    }
    return distributorResponseDto;
  }

  public DistributorResponseDto getDistributorByMobile(String mobile) {
    DistributorResponseDto distributorResponseDto = distributorCache.getDistributorByMobile(mobile);
    if (null == distributorResponseDto) {
      throw new StarServiceException(ApiCode.NO_EXISTS);
    }
    if (distributorResponseDto.getEnabled() == EnabledEnum.disabled.val()) {
      throw new StarServiceException(ApiCode.NO_LOGIN, "已被取消分销商身份");
    }
    if (distributorResponseDto.getState() == LoginStateEnum.logout.getState()) {
      throw new StarServiceException(ApiCode.NO_LOGIN);
    }
    return distributorResponseDto;
  }
}