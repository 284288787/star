/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.product.dao.read.DistributionRegionReadDao;
import com.star.truffle.module.product.dao.write.DistributionRegionWriteDao;
import com.star.truffle.module.product.domain.DistributionRegion;
import com.star.truffle.module.product.dto.req.DistributionRegionRequestDto;
import com.star.truffle.module.product.dto.res.DistributionRegionResponseDto;

@Service
public class DistributionRegionCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private DistributionRegionWriteDao distributionRegionWriteDao;
  @Autowired
  private DistributionRegionReadDao distributionRegionReadDao;

  @Caching(
    put = {
      @CachePut(value = "module-product-distributionRegion", key = "'distributionRegion_regionId_'+#result.regionId", condition = "#result != null and #result.regionId != null"),
      @CachePut(value = "module-product-distributionRegion", key = "'distributionRegion_regionId_'+#result.py", condition = "#result != null and #result.py != null"),
    }
  )
  public DistributionRegionResponseDto saveDistributionRegion(DistributionRegion distributionRegion){
    this.distributionRegionWriteDao.saveDistributionRegion(distributionRegion);
    DistributionRegionResponseDto distributionRegionResponseDto = this.distributionRegionWriteDao.getDistributionRegion(distributionRegion.getRegionId());
    return distributionRegionResponseDto;
  }

  @Caching(
    put = {
      @CachePut(value = "module-product-distributionRegion", key = "'distributionRegion_regionId_'+#result.regionId", condition = "#result != null and #result.regionId != null"),
      @CachePut(value = "module-product-distributionRegion", key = "'distributionRegion_regionId_'+#result.py", condition = "#result != null and #result.py != null"),
    }
  )
  public DistributionRegionResponseDto updateDistributionRegion(DistributionRegionRequestDto distributionRegionRequestDto){
    this.distributionRegionWriteDao.updateDistributionRegion(distributionRegionRequestDto);
    DistributionRegionResponseDto distributionRegionResponseDto = this.distributionRegionWriteDao.getDistributionRegion(distributionRegionRequestDto.getRegionId());
    return distributionRegionResponseDto;
  }

  @Caching(
    evict = {
      @CacheEvict(value = "module-product-distributionRegion", key = "'distributionRegion_regionId_'+#regionId", condition = "#regionId != null"),
      @CacheEvict(value = "module-product-distributionRegion", key = "'distributionRegion_regionId_'+#py", condition = "#py != null")
    }
  )
  public int deleteDistributionRegion(Long regionId, String py){
    return this.distributionRegionWriteDao.deleteDistributionRegion(regionId);
  }

  @Cacheable(value = "module-product-distributionRegion", key = "'distributionRegion_regionId_'+#regionId", condition = "#regionId != null")
  public DistributionRegionResponseDto getDistributionRegion(Long regionId){
    DistributionRegionResponseDto distributionRegionResponseDto = this.distributionRegionReadDao.getDistributionRegion(regionId);
    return distributionRegionResponseDto;
  }

  @Cacheable(value = "module-product-distributionRegion", key = "'distributionRegion_py_'+#py", condition = "#py != null")
  public DistributionRegionResponseDto getDistributionRegionByPy(String py) {
    return distributionRegionReadDao.getDistributionRegionByPy(py);
  }
  
  public List<DistributionRegionResponseDto> queryDistributionRegion(DistributionRegionRequestDto distributionRegionRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(distributionRegionRequestDto);
    return this.distributionRegionReadDao.queryDistributionRegion(conditions);
  }

  public Long queryDistributionRegionCount(DistributionRegionRequestDto distributionRegionRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(distributionRegionRequestDto);
    return this.distributionRegionReadDao.queryDistributionRegionCount(conditions);
  }

}