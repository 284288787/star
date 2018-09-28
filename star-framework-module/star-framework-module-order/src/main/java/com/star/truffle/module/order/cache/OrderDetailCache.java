/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.star.truffle.module.order.dao.read.OrderDetailReadDao;
import com.star.truffle.module.order.dao.write.OrderDetailWriteDao;
import com.star.truffle.module.order.domain.OrderDetail;

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
}