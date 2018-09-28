/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.OrderAfterSaleCache;
import com.star.truffle.module.order.domain.OrderAfterSale;
import com.star.truffle.module.order.dto.req.OrderAfterSaleRequestDto;
import com.star.truffle.module.order.dto.res.OrderAfterSaleResponseDto;

@Service
public class OrderAfterSaleService {

  @Autowired
  private OrderAfterSaleCache orderAfterSaleCache;

  public Long saveOrderAfterSale(OrderAfterSale orderAfterSale) {
    this.orderAfterSaleCache.saveOrderAfterSale(orderAfterSale);
    return orderAfterSale.getId();
  }

  public void updateOrderAfterSale(OrderAfterSaleRequestDto orderAfterSaleRequestDto) {
    this.orderAfterSaleCache.updateOrderAfterSale(orderAfterSaleRequestDto);
  }

  public void deleteOrderAfterSale(Long id) {
    this.orderAfterSaleCache.deleteOrderAfterSale(id);
  }

  public void deleteOrderAfterSale(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      this.orderAfterSaleCache.deleteOrderAfterSale(id);
    }
  }

  public OrderAfterSaleResponseDto getOrderAfterSale(Long id) {
    OrderAfterSaleResponseDto orderAfterSaleResponseDto = this.orderAfterSaleCache.getOrderAfterSale(id);
    return orderAfterSaleResponseDto;
  }

  public List<OrderAfterSaleResponseDto> queryOrderAfterSale(OrderAfterSaleRequestDto orderAfterSaleRequestDto) {
    return this.orderAfterSaleCache.queryOrderAfterSale(orderAfterSaleRequestDto);
  }

  public Long queryOrderAfterSaleCount(OrderAfterSaleRequestDto orderAfterSaleRequestDto) {
    return this.orderAfterSaleCache.queryOrderAfterSaleCount(orderAfterSaleRequestDto);
  }

}