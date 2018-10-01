/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;
import com.star.truffle.module.member.dto.res.MemberResponseDto;
import com.star.truffle.module.member.service.DistributorService;
import com.star.truffle.module.member.service.MemberService;
import com.star.truffle.module.order.cache.OrderCache;
import com.star.truffle.module.order.cache.OrderDetailCache;
import com.star.truffle.module.order.constant.DeliveryTypeEnum;
import com.star.truffle.module.order.constant.OrderStateEnum;
import com.star.truffle.module.order.constant.OrderTypeEnum;
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.domain.OrderDetail;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.properties.OrderProperties;
import com.star.truffle.module.product.constant.BrokerageTypeEnum;
import com.star.truffle.module.product.constant.ProductEnum;
import com.star.truffle.module.product.dto.res.ProductResponseDto;
import com.star.truffle.module.product.service.ProductService;

@Service
public class OrderService {

  @Autowired
  private StarJson starJson;
  @Autowired
  private OrderProperties orderProperties;
  @Autowired
  private OrderCache orderCache;
  @Autowired
  private OrderDetailCache orderDetailCache;
  @Autowired
  private MemberService memberService;
  @Autowired
  private DeliveryAddressService deliveryAddressService;
  @Autowired
  private DistributorService distributorService;
  @Autowired
  private ProductService productService;
  
  public Long saveOrder(Order order) {
    this.orderCache.saveOrder(order);
    return order.getOrderId();
  }

  public Long saveMemberOrder(OrderRequestDto orderRequestDto) {
    if (null == orderRequestDto || ! orderRequestDto.checkMemberSave()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    MemberResponseDto member = memberService.getMember(orderRequestDto.getMemberId());
    if (null == member) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "会员不存在");
    }
    DistributorResponseDto distributor = distributorService.getDistributor(orderRequestDto.getDistributorId());
    if (null == distributor) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "分销商不存在");
    }
    if (orderRequestDto.getDeliveryType() == DeliveryTypeEnum.express.type()) {
      DeliveryAddressResponseDto deliveryAddress = deliveryAddressService.getDeliveryAddress(orderRequestDto.getDeliveryId());
      if (null == deliveryAddress) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "收获地址不存在");
      }
      orderRequestDto.setProvinceId(deliveryAddress.getProvinceId());
      orderRequestDto.setCityId(deliveryAddress.getCityId());
      orderRequestDto.setAreaId(deliveryAddress.getAreaId());
      orderRequestDto.setDeliveryAddress(deliveryAddress.getAddress());
      orderRequestDto.setDeliveryName(deliveryAddress.getName());
      orderRequestDto.setDeliveryMobile(deliveryAddress.getMobile());
      orderRequestDto.setDespatchMoney(orderProperties.getDespatchMoney());
    }
    Integer totalMoney = 0;
    Integer totalBrokerage = 0;
    List<OrderDetail> details = orderRequestDto.getDetails();
    for (OrderDetail detail : details) {
      ProductResponseDto product = this.productService.getProduct(detail.getProductId());
      if (null == product || product.getState() != ProductEnum.onshelf.state()) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "供应已下架");
      }
      detail.setTitle(product.getTitle());
      detail.setMainPictureUrl(product.getMainPictureUrl());
      detail.setOriginalPrice(product.getOriginalPrice());
      detail.setPrice(product.getPrice());
      detail.setBrokerage(product.getBrokerageType() == BrokerageTypeEnum.money.type() ? product.getBrokerageValue() : Double.valueOf((product.getPrice() / 100.0) * (product.getBrokerageValue() / 100.0) * 100).intValue());
      detail.setPickupTime(product.getPickupTime());
      detail.setSpecification(product.getSpecification());
      detail.setProductInfo(starJson.obj2string(product));
      totalMoney += detail.getPrice() * detail.getCount();
      totalBrokerage += detail.getBrokerage() * detail.getCount();
    }
    orderRequestDto.setTotalMoney(totalMoney);
    orderRequestDto.setTotalBrokerage(totalBrokerage);
    orderRequestDto.setRegionId(distributor.getRegionId());
    orderRequestDto.setShopAddress(distributor.getAddress());
    orderRequestDto.setShopName(distributor.getShopName());
    orderRequestDto.setShopMobile(distributor.getMobile());
    orderRequestDto.setState(OrderStateEnum.nopay.state());
    orderRequestDto.setType(OrderTypeEnum.self.type());
    orderRequestDto.setOpenId(member.getOpenId());
    orderRequestDto.setName(member.getName());
    orderRequestDto.setMobile(member.getMobile());
    orderRequestDto.setCreateTime(new Date());
    OrderResponseDto orderResponseDto = this.orderCache.saveOrder(orderRequestDto);
    Long orderId = orderResponseDto.getOrderId();
    for (OrderDetail orderDetail : details) {
      orderDetail.setOrderId(orderId);
    }
    this.orderDetailCache.saveOrderDetail(orderId, details);
    
    OrderRequestDto param = new OrderRequestDto();
    param.setOrderId(orderId);
    param.setOrderCode(8000000 + orderId);
    this.orderCache.updateOrder(param);
    return orderId;
  }
  
  public Long saveDistributorOrder(OrderRequestDto orderRequestDto) {
    if (null == orderRequestDto || ! orderRequestDto.checkDistributorSave()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    DistributorResponseDto distributor = distributorService.getDistributor(orderRequestDto.getDistributorId());
    if (null == distributor) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "分销商不存在");
    }
    if (orderRequestDto.getDeliveryType() == DeliveryTypeEnum.express.type()) {
      orderRequestDto.setDeliveryName(orderRequestDto.getName());
      orderRequestDto.setDeliveryMobile(orderRequestDto.getMobile());
      orderRequestDto.setDespatchMoney(orderProperties.getDespatchMoney());
    }
    List<OrderDetail> details = orderRequestDto.getDetails();
    for (OrderDetail detail : details) {
      ProductResponseDto product = this.productService.getProduct(detail.getProductId());
      if (null == product || product.getState() != ProductEnum.onshelf.state()) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "供应已下架");
      }
      detail.setTitle(product.getTitle());
      detail.setMainPictureUrl(product.getMainPictureUrl());
      detail.setOriginalPrice(product.getOriginalPrice());
      detail.setPrice(product.getPrice());
      detail.setBrokerage(product.getBrokerageType() == BrokerageTypeEnum.money.type() ? product.getBrokerageValue() : Double.valueOf((product.getPrice() / 100.0) * (product.getBrokerageValue() / 100.0) * 100).intValue());
      detail.setPickupTime(product.getPickupTime());
      detail.setSpecification(product.getSpecification());
      detail.setProductInfo(starJson.obj2string(product));
    }
    orderRequestDto.setRegionId(distributor.getRegionId());
    orderRequestDto.setShopAddress(distributor.getAddress());
    orderRequestDto.setShopName(distributor.getShopName());
    orderRequestDto.setShopMobile(distributor.getMobile());
    orderRequestDto.setState(OrderStateEnum.nosend.state());
    orderRequestDto.setType(OrderTypeEnum.behalf.type());
    orderRequestDto.setCreateTime(new Date());
    OrderResponseDto orderResponseDto = this.orderCache.saveOrder(orderRequestDto);
    Long orderId = orderResponseDto.getOrderId();
    this.orderDetailCache.saveOrderDetail(orderId, details);
    
    OrderRequestDto param = new OrderRequestDto();
    param.setOrderId(orderId);
    param.setOrderCode(8000000 + orderId);
    this.orderCache.updateOrder(param);
    return orderId;
  }

  public void updateOrder(OrderRequestDto orderRequestDto) {
    this.orderCache.updateOrder(orderRequestDto);
  }

  public void deleteOrder(Long orderId) {
    if (null == orderId) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    this.orderCache.deleteOrder(orderId);
  }

  public void deleteOrder(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] orderIds = idstr.split(",");
    for (String str : orderIds) {
      Long orderId = Long.parseLong(str);
      this.orderCache.deleteOrder(orderId);
    }
  }

  public OrderResponseDto getOrder(Long orderId) {
    OrderResponseDto orderResponseDto = this.orderCache.getOrder(orderId);
    if (null != orderResponseDto) {
      List<OrderDetail> details = orderDetailCache.getOrderDetails(orderId);
      orderResponseDto.setDetails(details);
    }
    return orderResponseDto;
  }

  public List<OrderResponseDto> queryOrder(OrderRequestDto orderRequestDto) {
    return this.orderCache.queryOrder(orderRequestDto);
  }

  public Long queryOrderCount(OrderRequestDto orderRequestDto) {
    return this.orderCache.queryOrderCount(orderRequestDto);
  }

}