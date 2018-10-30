/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.read;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.core.jdbc.Page;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.dto.res.OrderTotal;

public interface OrderReadDao {

  public OrderResponseDto getOrder(Long orderId);

  public List<OrderResponseDto> queryOrder(Map<String, Object> conditions);

  public Long queryOrderCount(Map<String, Object> conditions);

  public List<OrderTotal> orderIndexToday(@Param("distributorId") Long distributorId, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize, @Param("keyword") String keyword);

  /**
   * 累计收益
   * @param distributorId
   * @return
   */
  public Map<String, Object> totalMoney(@Param("distributorId") Long distributorId, @Param("beginTime") Date beginTime);

  public Long totalOrderNumOfToday();

  public Long sumBrokerage(OrderRequestDto orderRequestDto);

  public List<Map<String, Object>> seeUser(@Param("distributorId") Long distributorId, @Param("keyword") String keyword, @Param("pager") Page pager);

  public Map<String, Object> orderNum(Long memberId);
}