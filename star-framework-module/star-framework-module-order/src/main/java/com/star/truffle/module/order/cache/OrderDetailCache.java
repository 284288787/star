/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jdbc.Page;
import com.star.truffle.module.order.dao.read.OrderDetailReadDao;
import com.star.truffle.module.order.dao.write.OrderDetailWriteDao;
import com.star.truffle.module.order.domain.OrderDetail;
import com.star.truffle.module.order.dto.req.OrderDetailRequestDto;
import com.star.truffle.module.order.dto.res.OrderDetailResponseDto;

@Service
public class OrderDetailCache {

  @Autowired
  private OrderDetailWriteDao orderDetailWriteDao;
  @Autowired
  private OrderDetailReadDao orderDetailReadDao;

  @CachePut(value = "module-order-orderDetail", key = "'orderDetails_orderId_'+#orderId", condition = "#orderId != null")
  public List<OrderDetail> saveOrderDetail(Long orderId, List<OrderDetail> orderDetails){
    this.orderDetailWriteDao.batchSaveOrderDetail(orderDetails);
    return orderDetails;
  }

  @Cacheable(value = "module-order-orderDetail", key = "'orderDetails_orderId_'+#orderId", condition = "#orderId != null")
  public List<OrderDetail> getOrderDetails(Long orderId){
    List<OrderDetail> details = this.orderDetailReadDao.getOrderDetails(orderId);
    return details;
  }

  public Long getProductNoPayNumber(Long productId, int state) {
    return this.orderDetailReadDao.getProductNoPayNumber(productId, state);
  }

  public List<OrderDetailResponseDto> buyRecord(Long productId, Page page) {
    return orderDetailReadDao.buyRecord(productId, page);
  }

  public Map<String, Integer> buyRecordTotal(Long productId) {
    return orderDetailReadDao.buyRecordTotal(productId);
  }

  public List<OrderDetailResponseDto> queryOrderDetail(OrderDetailRequestDto orderDetailRequestDto) {
    return orderDetailReadDao.queryOrderDetail(orderDetailRequestDto);
  }
}