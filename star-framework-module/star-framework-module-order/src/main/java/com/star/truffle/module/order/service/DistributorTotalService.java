/**create by framework at 2018年11月27日 10:12:39**/
package com.star.truffle.module.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.DistributorTotalCache;
import com.star.truffle.module.order.cache.OrderCache;
import com.star.truffle.module.order.domain.DistributorTotal;
import com.star.truffle.module.order.dto.req.DistributorTotalRequestDto;
import com.star.truffle.module.order.dto.res.DistributorTotalResponseDto;

@Service
public class DistributorTotalService {

  @Autowired
  private DistributorTotalCache distributorTotalCache;
  @Autowired
  private OrderCache orderCache;

  public List<DistributorTotalResponseDto> queryDistributorTotal(DistributorTotalRequestDto distributorTotalRequestDto) {
    return this.distributorTotalCache.queryDistributorTotal(distributorTotalRequestDto);
  }

  public Long queryDistributorTotalCount(DistributorTotalRequestDto distributorTotalRequestDto) {
    return this.distributorTotalCache.queryDistributorTotalCount(distributorTotalRequestDto);
  }

  public void updateDistributorTotal(Integer day) {
    if (null == day || day <= 0) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "day必须大于0");
    }
    distributorTotalCache.deleteDistributorTotalBy(day);
    List<DistributorTotalResponseDto> list = orderCache.totalOrderByDistributor(null, day);
    if (null != list && list.size() > 0) {
      List<DistributorTotal> distributorTotals = new ArrayList<>();
      for (DistributorTotalResponseDto distributorTotalResponseDto : list) {
        distributorTotals.add(distributorTotalResponseDto);
      }
      distributorTotalCache.batchSaveDistributorTotal(distributorTotals);
    }
  }

}