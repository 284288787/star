/**create by framework at 2018年10月26日 09:40:50**/
package com.star.truffle.module.member.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.member.cache.DistributorApplyCache;
import com.star.truffle.module.member.dto.req.DistributorApplyRequestDto;
import com.star.truffle.module.member.dto.res.DistributorApplyResponseDto;

@Service
public class DistributorApplyService {

  @Autowired
  private DistributorApplyCache distributorApplyCache;

  public Long saveDistributorApply(DistributorApplyRequestDto distributorApply) {
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
      this.distributorApplyCache.deleteDistributorApply(id);
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

}