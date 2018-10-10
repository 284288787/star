/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.dao.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.product.domain.DistributionRegion;
import com.star.truffle.module.product.dto.req.DistributionRegionRequestDto;
import com.star.truffle.module.product.dto.res.DistributionRegionResponseDto;

public interface DistributionRegionWriteDao {

  public int saveDistributionRegion(DistributionRegion distributionRegion);

  public int batchSaveDistributionRegion(List<DistributionRegion> distributionRegions);

  public int updateDistributionRegion(DistributionRegionRequestDto distributionRegionDto);

  public int deleteDistributionRegion(Long regionId);

  public DistributionRegionResponseDto getDistributionRegion(Long regionId);

  public int addDistributionRegionNum(@Param("regionId") Long regionId, @Param("type") String type, @Param("num") int num);

}