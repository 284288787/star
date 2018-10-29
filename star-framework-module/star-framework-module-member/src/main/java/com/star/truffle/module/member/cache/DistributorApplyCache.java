/**create by framework at 2018年10月26日 09:40:50**/
package com.star.truffle.module.member.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.member.dao.read.DistributorApplyReadDao;
import com.star.truffle.module.member.dao.write.DistributorApplyWriteDao;
import com.star.truffle.module.member.domain.DistributorApply;
import com.star.truffle.module.member.dto.req.DistributorApplyRequestDto;
import com.star.truffle.module.member.dto.res.DistributorApplyResponseDto;

@Service
public class DistributorApplyCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private DistributorApplyWriteDao distributorApplyWriteDao;
  @Autowired
  private DistributorApplyReadDao distributorApplyReadDao;

//  @CachePut(value = "module-member-distributorApply", key = "'distributorApply_id_'+#result.id", condition = "#result != null and #result.id != null")
  public DistributorApplyResponseDto saveDistributorApply(DistributorApply distributorApply){
    distributorApply.setCreateTime(new Date());
    this.distributorApplyWriteDao.saveDistributorApply(distributorApply);
    DistributorApplyResponseDto distributorApplyResponseDto = this.distributorApplyWriteDao.getDistributorApply(distributorApply.getId());
    return distributorApplyResponseDto;
  }

//  @CachePut(value = "module-member-distributorApply", key = "'distributorApply_id_'+#result.id", condition = "#result != null and #result.id != null")
  public DistributorApplyResponseDto updateDistributorApply(DistributorApplyRequestDto distributorApplyRequestDto){
    this.distributorApplyWriteDao.updateDistributorApply(distributorApplyRequestDto);
    DistributorApplyResponseDto distributorApplyResponseDto = this.distributorApplyWriteDao.getDistributorApply(distributorApplyRequestDto.getId());
    return distributorApplyResponseDto;
  }

//  @CacheEvict(value = "module-member-distributorApply", key = "'distributorApply_id_'+#id", condition = "#id != null")
  public int deleteDistributorApply(Long id){
    return this.distributorApplyWriteDao.deleteDistributorApply(id);
  }

//  @Cacheable(value = "module-member-distributorApply", key = "'distributorApply_id_'+#id", condition = "#id != null")
  public DistributorApplyResponseDto getDistributorApply(Long id){
    DistributorApplyResponseDto distributorApplyResponseDto = this.distributorApplyReadDao.getDistributorApply(id);
    return distributorApplyResponseDto;
  }

  public List<DistributorApplyResponseDto> queryDistributorApply(DistributorApplyRequestDto distributorApplyRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(distributorApplyRequestDto);
    return this.distributorApplyReadDao.queryDistributorApply(conditions);
  }

  public Long queryDistributorApplyCount(DistributorApplyRequestDto distributorApplyRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(distributorApplyRequestDto);
    return this.distributorApplyReadDao.queryDistributorApplyCount(conditions);
  }

}