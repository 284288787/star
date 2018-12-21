/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.util.DateUtils;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.OrderAfterSaleCache;
import com.star.truffle.module.order.cache.OrderCache;
import com.star.truffle.module.order.cache.OrderDetailCache;
import com.star.truffle.module.order.constant.AfterSaleEnum;
import com.star.truffle.module.order.constant.AfterSaleTypeEnum;
import com.star.truffle.module.order.constant.OrderStateEnum;
import com.star.truffle.module.order.domain.OrderAfterSale;
import com.star.truffle.module.order.dto.req.OrderAfterSaleRequestDto;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.OrderAfterSaleResponseDto;
import com.star.truffle.module.order.dto.res.OrderDetailResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;

@Service
public class OrderAfterSaleService {

  @Autowired
  private OrderAfterSaleCache orderAfterSaleCache;
  @Autowired
  private OrderCache orderCache;
  @Autowired
  private OrderDetailCache orderDetailCache;

  public void saveOrderAfterSale(OrderAfterSaleRequestDto orderAfterSale) {
    if (null == orderAfterSale || null == orderAfterSale.getOrderId() || null == orderAfterSale.getDetailIds() 
        || orderAfterSale.getDetailIds().length == 0 || null == orderAfterSale.getDistributorId() || null == orderAfterSale.getCount() 
        || StringUtils.isBlank(orderAfterSale.getRemark()) || null == orderAfterSale.getType()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    if (orderAfterSale.getType() != AfterSaleTypeEnum.back.getType() && orderAfterSale.getType() != AfterSaleTypeEnum.exchange.getType()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "只能是换货或退货");
    }
    OrderResponseDto order = orderCache.getOrder(orderAfterSale.getOrderId());
    if (null == order || order.getState() == OrderStateEnum.nopay.state() || order.getState() == OrderStateEnum.back.state()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "订单不存在或未付款或已退货");
    }
    if (order.getDistributorId().longValue() != orderAfterSale.getDistributorId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "不是自己下面的订单不能操作申请售后");
    }
    List<OrderAfterSale> list = new ArrayList<>();
    Long[] detailIds = orderAfterSale.getDetailIds();
    for (Long detailId : detailIds) {
      OrderDetailResponseDto orderDetail = orderDetailCache.getOrderDetail(detailId);
      if (null == orderDetail || orderDetail.getOrderId() != orderAfterSale.getOrderId().longValue()) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "商品不存在，或不属于该订单");
      }
      OrderAfterSale item = new OrderAfterSale();
      item.setDetailId(detailId);
      item.setCount(orderAfterSale.getCount());
      item.setOrderId(orderAfterSale.getOrderId());
      item.setType(orderAfterSale.getType());
      item.setRemark(orderAfterSale.getRemark());
      item.setAfterCode(String.valueOf(900000 + order.getOrderId()) + detailId);
      item.setState(AfterSaleEnum.pending.state());
      item.setCreateTime(new Date());
      list.add(item);
    }
    this.orderAfterSaleCache.batchSaveOrderAfterSale(list);
  }
  
  public void setExpressage(OrderAfterSaleRequestDto orderAfterSaleRequestDto) {
    if (null == orderAfterSaleRequestDto || StringUtils.isBlank(orderAfterSaleRequestDto.getExpressageCompany()) 
        || StringUtils.isBlank(orderAfterSaleRequestDto.getExpressageNumber()) || null == orderAfterSaleRequestDto.getId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    OrderAfterSaleResponseDto orderAfterSaleResponseDto = this.orderAfterSaleCache.getOrderAfterSale(orderAfterSaleRequestDto.getId());
    if (null == orderAfterSaleResponseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "信息不存在");
    }
    if (orderAfterSaleResponseDto.getState() == AfterSaleEnum.pass.state() || orderAfterSaleResponseDto.getState() == AfterSaleEnum.send.state()) {
      orderAfterSaleRequestDto.setExpressageTime(new Date());
      orderAfterSaleRequestDto.setState(AfterSaleEnum.send.state());
      this.orderAfterSaleCache.updateOrderAfterSale(orderAfterSaleRequestDto);
    }else {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "审核通过才可以填写快递单号");
    }
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
    List<OrderAfterSaleResponseDto> list = this.orderAfterSaleCache.queryOrderAfterSale(orderAfterSaleRequestDto);
    return list;
  }

  public Long queryOrderAfterSaleCount(OrderAfterSaleRequestDto orderAfterSaleRequestDto) {
    return this.orderAfterSaleCache.queryOrderAfterSaleCount(orderAfterSaleRequestDto);
  }

  public void cancelOrderAfterSale(Long id, Long distributorId) {
    if (null == id || null == distributorId) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    OrderAfterSaleResponseDto afterSale = orderAfterSaleCache.getOrderAfterSale(id);
    if (null == afterSale || afterSale.getState() != AfterSaleEnum.pending.state()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "只有待处理的售后申请才可以取消");
    }
    OrderResponseDto order = orderCache.getOrder(afterSale.getOrderId());
    if (order.getDistributorId().longValue() != distributorId) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "不是自己下面的订单不能操作申请售后");
    }
    OrderAfterSaleRequestDto orderAfterSaleRequestDto = new OrderAfterSaleRequestDto();
    orderAfterSaleRequestDto.setId(id);
    orderAfterSaleRequestDto.setState(AfterSaleEnum.cancel.state());
    orderAfterSaleRequestDto.setUpdateTime(new Date());
    this.orderAfterSaleCache.updateOrderAfterSale(orderAfterSaleRequestDto);
  }

  public void changeState(Long id, int state, String reject) {
    if (null == id || (state == AfterSaleEnum.nopass.state() && StringUtils.isBlank(reject))) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    OrderAfterSaleResponseDto responseDto = orderAfterSaleCache.getOrderAfterSale(id);
    if (null == responseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "记录不存在");
    }
    OrderAfterSaleRequestDto orderAfterSaleRequestDto = new OrderAfterSaleRequestDto();
    orderAfterSaleRequestDto.setId(id);
    orderAfterSaleRequestDto.setState(state);
    orderAfterSaleRequestDto.setReason(reject);
    this.orderAfterSaleCache.updateOrderAfterSale(orderAfterSaleRequestDto);
    if (responseDto.getType() == AfterSaleTypeEnum.back.getType() && responseDto.getState() == AfterSaleEnum.pass.state() && state == AfterSaleEnum.nopass.state()) {
      OrderResponseDto order = this.orderCache.getOrder(responseDto.getOrderId());
      OrderRequestDto orderRequestDto = new OrderRequestDto();
      orderRequestDto.setOrderId(responseDto.getOrderId());
      orderRequestDto.setBackBrokerage(order.getBackBrokerage() > 0 ? order.getBackBrokerage() - (responseDto.getCount() * responseDto.getBrokerage()) : 0);
      orderRequestDto.setBackBrokerageFirst(order.getBackBrokerageFirst() > 0 ? order.getBackBrokerageFirst() - (responseDto.getCount() * responseDto.getBrokerageFirst()) : 0);
      this.orderCache.updateOrder(orderRequestDto);
    }
  }

  public void pass(OrderAfterSaleRequestDto orderAfterSaleRequestDto) {
    if (null == orderAfterSaleRequestDto || null == orderAfterSaleRequestDto.getId() || StringUtils.isBlank(orderAfterSaleRequestDto.getAddressee())
        || StringUtils.isBlank(orderAfterSaleRequestDto.getAddresseeAddress()) || StringUtils.isBlank(orderAfterSaleRequestDto.getAddresseeMobile())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "参数错误");
    }
    OrderAfterSaleResponseDto oasrd = orderAfterSaleCache.getOrderAfterSale(orderAfterSaleRequestDto.getId());
    if (null == oasrd) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "记录不存在");
    }
    orderAfterSaleRequestDto.setEffectiveTime(DateUtils.plusNow(7, ChronoUnit.DAYS));
    orderAfterSaleRequestDto.setState(AfterSaleEnum.pass.state());
    this.orderAfterSaleCache.updateOrderAfterSale(orderAfterSaleRequestDto);
    if (oasrd.getType() == AfterSaleTypeEnum.back.getType() && oasrd.getState() == AfterSaleEnum.pending.state() || oasrd.getState() == AfterSaleEnum.nopass.state()) {
      OrderResponseDto order = this.orderCache.getOrder(oasrd.getOrderId());
      OrderRequestDto orderRequestDto = new OrderRequestDto();
      orderRequestDto.setOrderId(oasrd.getOrderId());
      orderRequestDto.setBackBrokerage(order.getTotalBrokerage() > 0 ? order.getBackBrokerage() + (oasrd.getCount() * oasrd.getBrokerage()) : 0);
      orderRequestDto.setBackBrokerageFirst(order.getTotalBrokerageFirst() > 0 ? order.getBackBrokerageFirst() + (oasrd.getCount() * oasrd.getBrokerageFirst()) : 0);
      this.orderCache.updateOrder(orderRequestDto);
    }
  }

}