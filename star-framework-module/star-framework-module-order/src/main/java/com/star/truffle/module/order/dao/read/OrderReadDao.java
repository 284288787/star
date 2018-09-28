/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.order.dto.res.OrderResponseDto;

public interface OrderReadDao {

  public OrderResponseDto getOrder(Long orderId);

  public List<OrderResponseDto> queryOrder(Map<String, Object> conditions);

  public Long queryOrderCount(Map<String, Object> conditions);

}