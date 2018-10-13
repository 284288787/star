/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.OrderAfterSaleCache;
import com.star.truffle.module.order.constant.AfterSaleEnum;
import com.star.truffle.module.order.constant.OrderStateEnum;
import com.star.truffle.module.order.dto.req.OrderAfterSaleRequestDto;
import com.star.truffle.module.order.dto.res.OrderAfterSaleResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;

@Service
public class OrderAfterSaleService {

  @Autowired
  private OrderAfterSaleCache orderAfterSaleCache;
  @Autowired
  private OrderService orderService;

  public Long saveOrderAfterSale(OrderAfterSaleRequestDto orderAfterSale) {
    if (null == orderAfterSale || null == orderAfterSale.getOrderId() 
        || null == orderAfterSale.getDistributorId() || StringUtils.isBlank(orderAfterSale.getRemark())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    OrderResponseDto order = orderService.getOrder(orderAfterSale.getOrderId());
    if (null == order || order.getState() == OrderStateEnum.nopay.state() || order.getState() == OrderStateEnum.back.state()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "订单不存在或未付款或已退货");
    }
    if (order.getDistributorId().longValue() != orderAfterSale.getDistributorId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "不是自己下面的订单不能操作申请售后");
    }
    orderAfterSale.setAfterCode(900000 + order.getOrderId());
    orderAfterSale.setState(AfterSaleEnum.pending.state());
    orderAfterSale.setCreateTime(new Date());
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