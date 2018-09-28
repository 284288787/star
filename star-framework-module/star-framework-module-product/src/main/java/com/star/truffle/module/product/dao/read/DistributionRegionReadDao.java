/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.product.dto.res.DistributionRegionResponseDto;

public interface DistributionRegionReadDao {

  public DistributionRegionResponseDto getDistributionRegion(Long regionId);

  public List<DistributionRegionResponseDto> queryDistributionRegion(Map<String, Object> conditions);

  public Long queryDistributionRegionCount(Map<String, Object> conditions);
  
  public DistributionRegionResponseDto getDistributionRegionByPy(String py);

}