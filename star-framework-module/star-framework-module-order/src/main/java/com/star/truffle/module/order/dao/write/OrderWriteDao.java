/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.write;

import java.util.List;
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;

public interface OrderWriteDao {

  public int saveOrder(Order order);

  public int batchSaveOrder(List<Order> orders);

  public int updateOrder(OrderRequestDto orderDto);

  public int deleteOrder(Long orderId);

  public OrderResponseDto getOrder(Long orderId);

}