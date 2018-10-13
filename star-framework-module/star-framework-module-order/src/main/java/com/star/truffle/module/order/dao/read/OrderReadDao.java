/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.dto.res.OrderTotal;

public interface OrderReadDao {

  public OrderResponseDto getOrder(Long orderId);

  public List<OrderResponseDto> queryOrder(Map<String, Object> conditions);

  public Long queryOrderCount(Map<String, Object> conditions);

  public List<OrderTotal> orderIndexToday(@Param("distributorId") Long distributorId, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

  /**
   * 累计收益
   * @param distributorId
   * @return
   */
  public Long totalMoney(Long distributorId);

  public Long totalOrderNumOfToday();
}