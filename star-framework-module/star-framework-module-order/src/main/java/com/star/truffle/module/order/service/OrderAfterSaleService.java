/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.OrderAfterSaleCache;
import com.star.truffle.module.order.cache.OrderCache;
import com.star.truffle.module.order.cache.OrderDetailCache;
import com.star.truffle.module.order.constant.AfterSaleEnum;
import com.star.truffle.module.order.constant.AfterSaleTypeEnum;
import com.star.truffle.module.order.constant.OrderStateEnum;
import com.star.truffle.module.order.domain.OrderAfterSale;
import com.star.truffle.module.order.dto.req.OrderAfterSaleRequestDto;
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
    orderAfterSaleRequestDto.setExpressageTime(new Date());
    this.orderAfterSaleCache.updateOrderAfterSale(orderAfterSaleRequestDto);
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
    if (responseDto.getState() == AfterSaleEnum.pending.state() || responseDto.getState() == AfterSaleEnum.pass.state() || responseDto.getState() == AfterSaleEnum.nopass.state()) {
      OrderAfterSaleRequestDto orderAfterSaleRequestDto = new OrderAfterSaleRequestDto();
      orderAfterSaleRequestDto.setId(id);
      orderAfterSaleRequestDto.setState(state);
      orderAfterSaleRequestDto.setReason(reject);
      this.orderAfterSaleCache.updateOrderAfterSale(orderAfterSaleRequestDto);
    }else {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "该记录已被处理，请刷新页面");
    }
  }

}