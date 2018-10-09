/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.order.dao.read.OrderReadDao;
import com.star.truffle.module.order.dao.write.OrderWriteDao;
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;

@Service
public class OrderCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private OrderWriteDao orderWriteDao;
  @Autowired
  private OrderReadDao orderReadDao;

  @CachePut(value = "module-order-order", key = "'order_orderId_'+#result.orderId", condition = "#result != null and #result.orderId != null")
  public OrderResponseDto saveOrder(Order order){
    this.orderWriteDao.saveOrder(order);
    OrderResponseDto orderResponseDto = this.orderWriteDao.getOrder(order.getOrderId());
    return orderResponseDto;
  }

  @CachePut(value = "module-order-order", key = "'order_orderId_'+#result.orderId", condition = "#result != null and #result.orderId != null")
  public OrderResponseDto updateOrder(OrderRequestDto orderRequestDto){
    this.orderWriteDao.updateOrder(orderRequestDto);
    OrderResponseDto orderResponseDto = this.orderWriteDao.getOrder(orderRequestDto.getOrderId());
    return orderResponseDto;
  }

  @CacheEvict(value = "module-order-order", key = "'order_orderId_'+#orderId", condition = "#orderId != null")
  public int deleteOrder(Long orderId){
    return this.orderWriteDao.deleteOrder(orderId);
  }

  @Cacheable(value = "module-order-order", key = "'order_orderId_'+#orderId", condition = "#orderId != null")
  public OrderResponseDto getOrder(Long orderId){
    OrderResponseDto orderResponseDto = this.orderReadDao.getOrder(orderId);
    return orderResponseDto;
  }

  public List<OrderResponseDto> queryOrder(OrderRequestDto orderRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(orderRequestDto);
    return this.orderReadDao.queryOrder(conditions);
  }

  public Long queryOrderCount(OrderRequestDto orderRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(orderRequestDto);
    return this.orderReadDao.queryOrderCount(conditions);
  }

}