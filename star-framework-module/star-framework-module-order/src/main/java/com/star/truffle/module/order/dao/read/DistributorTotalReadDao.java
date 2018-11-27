/**create by framework at 2018年11月27日 10:12:39**/
package com.star.truffle.module.order.dao.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.order.dto.res.DistributorTotalResponseDto;

public interface DistributorTotalReadDao {

  public List<DistributorTotalResponseDto> queryDistributorTotal(Map<String, Object> conditions);

  public Long queryDistributorTotalCount(Map<String, Object> conditions);

  public List<DistributorTotalResponseDto> totalDistributorTotal(@Param("day") Integer day);

}