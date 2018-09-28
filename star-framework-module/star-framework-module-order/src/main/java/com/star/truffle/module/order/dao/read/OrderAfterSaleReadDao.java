/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.order.dto.res.OrderAfterSaleResponseDto;

public interface OrderAfterSaleReadDao {

  public OrderAfterSaleResponseDto getOrderAfterSale(Long id);

  public List<OrderAfterSaleResponseDto> queryOrderAfterSale(Map<String, Object> conditions);

  public Long queryOrderAfterSaleCount(Map<String, Object> conditions);

}