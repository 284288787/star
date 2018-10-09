/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.order.domain.OrderDetail;

public interface OrderDetailReadDao {

  public List<OrderDetail> getOrderDetails(Long orderId);

  public Long getProductNoPayNumber(@Param("productId") Long productId, @Param("state") int state);

}