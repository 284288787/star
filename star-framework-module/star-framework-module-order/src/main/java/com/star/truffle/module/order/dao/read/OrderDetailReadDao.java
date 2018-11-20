/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.core.jdbc.Page;
import com.star.truffle.module.order.domain.OrderDetail;
import com.star.truffle.module.order.dto.req.OrderDetailRequestDto;
import com.star.truffle.module.order.dto.res.OrderDetailResponseDto;

public interface OrderDetailReadDao {

  public List<OrderDetail> getOrderDetails(Long orderId);

  public Long getProductNoPayNumber(@Param("productId") Long productId, @Param("state") int state);

  public List<OrderDetailResponseDto> buyRecord(@Param("productId") Long productId, @Param("pager") Page pager);
  
  public Map<String, Integer> buyRecordTotal(Long productId);

  public List<OrderDetailResponseDto> queryOrderDetail(OrderDetailRequestDto orderDetailRequestDto);

  public Integer getBuyTimes(@Param("memberId") Long memberId, @Param("productId") Long productId);

}