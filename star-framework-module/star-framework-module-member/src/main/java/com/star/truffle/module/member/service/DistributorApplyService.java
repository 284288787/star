/**create by framework at 2018年10月26日 09:40:50**/
package com.star.truffle.module.member.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.common.constants.EnabledEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.member.cache.DistributorApplyCache;
import com.star.truffle.module.member.cache.DistributorCache;
import com.star.truffle.module.member.constant.DistributorApplyStateEnum;
import com.star.truffle.module.member.dto.req.DistributorApplyRequestDto;
import com.star.truffle.module.member.dto.req.DistributorRequestDto;
import com.star.truffle.module.member.dto.res.DistributorApplyResponseDto;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;

@Service
public class DistributorApplyService {

  @Autowired
  private DistributorApplyCache distributorApplyCache;
  @Autowired
  private DistributorCache distributorCache;

  public Long saveDistributorApply(DistributorApplyRequestDto distributorApply) {
    distributorApply.setState(DistributorApplyStateEnum.normal.getState());
    this.distributorApplyCache.saveDistributorApply(distributorApply);
    return distributorApply.getId();
  }

  public void updateDistributorApply(DistributorApplyRequestDto distributorApplyRequestDto) {
    this.distributorApplyCache.updateDistributorApply(distributorApplyRequestDto);
  }

  public void deleteDistributorApply(Long id) {
    this.distributorApplyCache.deleteDistributorApply(id);
  }

  public void deleteDistributorApply(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      DistributorApplyRequestDto distributorApplyRequestDto = new DistributorApplyRequestDto();
      distributorApplyRequestDto.setId(id);
      distributorApplyRequestDto.setState(DistributorApplyStateEnum.deleted.getState());
      this.distributorApplyCache.updateDistributorApply(distributorApplyRequestDto);
//      this.distributorApplyCache.deleteDistributorApply(id);
    }
  }

  public DistributorApplyResponseDto getDistributorApply(Long id) {
    DistributorApplyResponseDto distributorApplyResponseDto = this.distributorApplyCache.getDistributorApply(id);
    return distributorApplyResponseDto;
  }

  public List<DistributorApplyResponseDto> queryDistributorApply(DistributorApplyRequestDto distributorApplyRequestDto) {
    return this.distributorApplyCache.queryDistributorApply(distributorApplyRequestDto);
  }

  public Long queryDistributorApplyCount(DistributorApplyRequestDto distributorApplyRequestDto) {
    return this.distributorApplyCache.queryDistributorApplyCount(distributorApplyRequestDto);
  }

  public void changeState(Long id, int state, String reject) {
    if (null == id || (state == DistributorApplyStateEnum.nopass.getState() && StringUtils.isBlank(reject))) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    DistributorApplyResponseDto responseDto = distributorApplyCache.getDistributorApply(id);
    if (null == responseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "记录不存在");
    }
    DistributorApplyRequestDto distributorApplyRequestDto = new DistributorApplyRequestDto();
    distributorApplyRequestDto.setId(id);
    distributorApplyRequestDto.setState(state);
    distributorApplyRequestDto.setReject(reject);
    this.distributorApplyCache.updateDistributorApply(distributorApplyRequestDto);
  }

  public void pass(Long id, Long regionId) {
    if (null == id || null == regionId) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    DistributorApplyResponseDto responseDto = distributorApplyCache.getDistributorApply(id);
    if (null == responseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "记录不存在");
    }
    DistributorResponseDto dto = distributorCache.getDistributorByMobile(responseDto.getMobile());
    if (null != dto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "该手机号用户已经是分销商");
    }
    DistributorRequestDto distributorRequestDto = new DistributorRequestDto();
    distributorRequestDto.setRegionId(regionId);
    distributorRequestDto.setEnabled(EnabledEnum.enabled.val());
    Long count = distributorCache.queryDistributorCount(distributorRequestDto);
    if (count > 0) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "该区域下已经存在分销商，若还是需要开通，请先禁用该区域的老分销商");
    }
    DistributorRequestDto distributor = new DistributorRequestDto();
    distributor.setName(responseDto.getName());
    distributor.setShopName(responseDto.getShopName());
    distributor.setMobile(responseDto.getMobile());
    distributor.setRegionId(regionId);
    distributor.setAddress(responseDto.getAddress());
    distributor.setEnabled(EnabledEnum.enabled.val());
    distributor.setCreateTime(new Date());
    distributor.setUpdateTime(distributor.getCreateTime());
    distributor.setFansNum(0);
    distributor.setSoldNum(0);
    distributor.setState(1);
    distributor.setBusinessLicense(responseDto.getBusinessLicense());
    distributor.setBusinessLicensePic(responseDto.getBusinessLicensePic());
    distributor.setFoodAllowanceLicense(responseDto.getFoodAllowanceLicense());
    distributor.setFoodAllowanceLicensePic(responseDto.getFoodAllowanceLicensePic());
    distributor.setAcreage(responseDto.getAcreage());
    distributor.setBankAddress(responseDto.getBankAddress());
    distributor.setBankCardName(responseDto.getBankCardName());
    distributor.setBankCardNo(responseDto.getBankCardNo());
    distributor.setApplyId(id);
    distributor.setShopCode("");
    this.distributorCache.saveDistributor(distributor);
    distributor.setShopCode(String.valueOf(distributor.getDistributorId() + 500000));
    this.distributorCache.updateDistributor(distributor);
    changeState(id, DistributorApplyStateEnum.pass.getState(), null);
  }

}