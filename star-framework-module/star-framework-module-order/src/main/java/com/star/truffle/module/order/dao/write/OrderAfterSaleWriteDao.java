/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dao.write;

import java.util.List;
import com.star.truffle.module.order.domain.OrderAfterSale;
import com.star.truffle.module.order.dto.req.OrderAfterSaleRequestDto;
import com.star.truffle.module.order.dto.res.OrderAfterSaleResponseDto;

public interface OrderAfterSaleWriteDao {

  public int saveOrderAfterSale(OrderAfterSale orderAfterSale);

  public int batchSaveOrderAfterSale(List<OrderAfterSale> orderAfterSales);

  public int updateOrderAfterSale(OrderAfterSaleRequestDto orderAfterSaleDto);

  public int deleteOrderAfterSale(Long id);

  public OrderAfterSaleResponseDto getOrderAfterSale(Long id);

}