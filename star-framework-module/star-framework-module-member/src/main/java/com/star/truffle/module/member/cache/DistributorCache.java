/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.star.truffle.common.constants.EnabledEnum;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.member.dao.read.DistributorReadDao;
import com.star.truffle.module.member.dao.write.DistributorWriteDao;
import com.star.truffle.module.member.domain.Distributor;
import com.star.truffle.module.member.dto.req.DistributorRequestDto;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;

@Service
public class DistributorCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private DistributorWriteDao distributorWriteDao;
  @Autowired
  private DistributorReadDao distributorReadDao;

  @Caching(put = {
      @CachePut(value = "module-member-distributor", key = "'distributor_distributorId_'+#result.distributorId", condition = "#result != null and #result.distributorId != null"),
      @CachePut(value = "module-member-distributor", key = "'distributor_mobile_'+#result.mobile", condition = "#result != null"),
      @CachePut(value = "module-member-distributor", key = "'distributor_openId_'+#result.openId", condition = "#result != null and #result.openId != null"),
  })
  public DistributorResponseDto saveDistributor(Distributor distributor){
    this.distributorWriteDao.saveDistributor(distributor);
    DistributorResponseDto distributorResponseDto = this.distributorWriteDao.getDistributor(distributor.getDistributorId());
    return distributorResponseDto;
  }

  @Caching(put = {
      @CachePut(value = "module-member-distributor", key = "'distributor_distributorId_'+#result.distributorId", condition = "#result != null and #result.distributorId != null"),
      @CachePut(value = "module-member-distributor", key = "'distributor_mobile_'+#result.mobile", condition = "#result != null"),
      @CachePut(value = "module-member-distributor", key = "'distributor_openId_'+#result.openId", condition = "#result != null and #result.openId != null")
  })
  public DistributorResponseDto updateDistributor(DistributorRequestDto distributorRequestDto){
    this.distributorWriteDao.updateDistributor(distributorRequestDto);
    DistributorResponseDto distributorResponseDto = this.distributorWriteDao.getDistributor(distributorRequestDto.getDistributorId());
    return distributorResponseDto;
  }

  @Caching(evict = {
      @CacheEvict(value = "module-member-distributor", key = "'distributor_distributorId_'+#distributorId", condition = "#distributorId != null"),
      @CacheEvict(value = "module-member-distributor", key = "'distributor_mobile_'+#mobile", condition = "#mobile != null"),
      @CacheEvict(value = "module-member-distributor", key = "'distributor_openId_'+#openId", condition = "#openId != null")
  })
  
  public int deleteDistributor(Long distributorId, String mobile, String openId){
    return this.distributorWriteDao.deleteDistributor(distributorId);
  }

  @Cacheable(value = "module-member-distributor", key = "'distributor_distributorId_'+#distributorId", condition = "#distributorId != null")
  public DistributorResponseDto getDistributor(Long distributorId){
    DistributorResponseDto distributorResponseDto = this.distributorReadDao.getDistributor(distributorId);
    return distributorResponseDto;
  }

  @Cacheable(value = "module-member-distributor", key = "'distributor_openId_'+#openId", condition = "#openId != null")
  public DistributorResponseDto getDistributorByOpenId(String openId) {
    DistributorResponseDto distributorResponseDto = this.distributorReadDao.getDistributorByOpenId(openId);
    return distributorResponseDto;
  }
  
  @Cacheable(value = "module-member-distributor", key = "'distributor_mobile_'+#mobile", condition = "#mobile != null")
  public DistributorResponseDto getDistributorByMobile(String mobile) {
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("mobile", mobile);
    conditions.put("enabled", EnabledEnum.enabled.val());
    List<DistributorResponseDto> list = this.distributorReadDao.queryDistributor(conditions);
    if (null != list && ! list.isEmpty()) {
      return list.get(0);
    }
    return null;
  }
  
  public List<DistributorResponseDto> queryDistributor(DistributorRequestDto distributorRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(distributorRequestDto);
    return this.distributorReadDao.queryDistributor(conditions);
  }

  public Long queryDistributorCount(DistributorRequestDto distributorRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(distributorRequestDto);
    return this.distributorReadDao.queryDistributorCount(conditions);
  }
}