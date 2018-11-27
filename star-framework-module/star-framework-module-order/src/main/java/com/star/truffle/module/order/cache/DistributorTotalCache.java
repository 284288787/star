/**create by framework at 2018年11月27日 10:12:39**/
package com.star.truffle.module.order.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.order.dao.read.DistributorTotalReadDao;
import com.star.truffle.module.order.dao.write.DistributorTotalWriteDao;
import com.star.truffle.module.order.domain.DistributorTotal;
import com.star.truffle.module.order.dto.req.DistributorTotalRequestDto;
import com.star.truffle.module.order.dto.res.DistributorTotalResponseDto;

@Service
public class DistributorTotalCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private DistributorTotalWriteDao distributorTotalWriteDao;
  @Autowired
  private DistributorTotalReadDao distributorTotalReadDao;

  public void batchSaveDistributorTotal(List<DistributorTotal> distributorTotals){
    this.distributorTotalWriteDao.batchSaveDistributorTotal(distributorTotals);
  }

  public List<DistributorTotalResponseDto> queryDistributorTotal(DistributorTotalRequestDto distributorTotalRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(distributorTotalRequestDto);
    return this.distributorTotalReadDao.queryDistributorTotal(conditions);
  }

  public Long queryDistributorTotalCount(DistributorTotalRequestDto distributorTotalRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(distributorTotalRequestDto);
    return this.distributorTotalReadDao.queryDistributorTotalCount(conditions);
  }

  public void deleteDistributorTotalBy(Integer day) {
    this.distributorTotalWriteDao.deleteDistributorTotalBy(day);
  }

  public List<DistributorTotalResponseDto> totalDistributorTotal(Integer day) {
    return distributorTotalReadDao.totalDistributorTotal(day);
  }

}