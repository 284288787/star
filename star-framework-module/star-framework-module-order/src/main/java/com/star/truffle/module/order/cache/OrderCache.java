/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.module.order.dao.read.OrderReadDao;
import com.star.truffle.module.order.dao.write.OrderWriteDao;
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.dto.req.OrderDetailRequestDto;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.DistributorTotalResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.dto.res.OrderTotal;

@Service
public class OrderCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private OrderWriteDao orderWriteDao;
  @Autowired
  private OrderReadDao orderReadDao;

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

  public List<OrderTotal> orderIndexToday(Long distributorId, Integer startIndex, Integer pageSize, String keyword) {
    List<OrderTotal> list = orderReadDao.orderIndexToday(distributorId, startIndex, pageSize, keyword);
    return list;
  }

  public Map<String, Object> totalMoney(Long distributorId, Date beginTime) {
    return orderReadDao.totalMoney(distributorId, beginTime);
  }

  public Map<String, Object> totalMoneyYun(Long distributorId, Date beginTime) {
    return orderReadDao.totalMoneyYun(distributorId, beginTime);
  }
  
  public Long totalOrderNumOfToday() {
    return orderReadDao.totalOrderNumOfToday();
  }
  
  public Long sumBrokerage(OrderRequestDto orderRequestDto) {
    return orderReadDao.sumBrokerage(orderRequestDto);
  }

  public Long sumBrokerageYun(OrderRequestDto orderRequestDto) {
    return orderReadDao.sumBrokerageYun(orderRequestDto);
  }

  public List<Map<String, Object>> seeUser(Long distributorId, String keyword, Page pager) {
    return orderReadDao.seeUser(distributorId, keyword, pager);
  }

  public Map<String, Object> orderNum(Long memberId) {
    return orderReadDao.orderNum(memberId);
  }

  public List<Map<String, Object>> getDistributorIds(OrderDetailRequestDto orderDetailRequestDto) {
    return orderReadDao.getDistributorIds(orderDetailRequestDto);
  }

  public List<DistributorTotalResponseDto> totalOrderByDistributor(Long distributorId, int day) {
    return orderReadDao.totalOrderByDistributor(distributorId, day);
  }

  /**
   * 统计今天的订单情况
   * @param day
   * @return
   */
  public DistributorTotalResponseDto totalOrderBy(int day) {
    return orderReadDao.totalOrderBy(day);
  }
}