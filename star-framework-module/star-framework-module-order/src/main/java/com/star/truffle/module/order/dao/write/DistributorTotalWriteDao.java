/**create by framework at 2018年11月27日 10:12:39**/
package com.star.truffle.module.order.dao.write;

import java.util.List;

import com.star.truffle.module.order.domain.DistributorTotal;

public interface DistributorTotalWriteDao {

  public int batchSaveDistributorTotal(List<DistributorTotal> distributorTotals);

  public int deleteDistributorTotalBy(Integer day);

}