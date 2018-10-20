/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.common.constants.DeletedEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.util.DateUtils;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;
import com.star.truffle.module.member.dto.res.MemberResponseDto;
import com.star.truffle.module.member.service.DistributorService;
import com.star.truffle.module.member.service.MemberService;
import com.star.truffle.module.order.cache.OrderCache;
import com.star.truffle.module.order.cache.OrderDetailCache;
import com.star.truffle.module.order.constant.DeleteUserTypeEnum;
import com.star.truffle.module.order.constant.DeliveryTypeEnum;
import com.star.truffle.module.order.constant.OrderStateEnum;
import com.star.truffle.module.order.constant.OrderTypeEnum;
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.domain.OrderDetail;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.req.ShoppingCartRequestDto;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;
import com.star.truffle.module.order.dto.res.OrderDetailResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.dto.res.OrderTotal;
import com.star.truffle.module.order.dto.res.ShoppingCartResponseDto;
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
  @Autowired
  private ShoppingCartService shoppingCartService;
  
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
      orderRequestDto.setProvinceName(deliveryAddress.getProvinceName());
      orderRequestDto.setCityName(deliveryAddress.getCityName());
      orderRequestDto.setAreaName(deliveryAddress.getAreaName());
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
    
    //删除购物车里相关的商品
    ShoppingCartRequestDto shoppingCartRequestDto = new ShoppingCartRequestDto();
    shoppingCartRequestDto.setMemberId(orderRequestDto.getMemberId());
    for (OrderDetail orderDetail : details) {
      shoppingCartRequestDto.setProductId(orderDetail.getProductId());
      List<ShoppingCartResponseDto> ps = shoppingCartService.queryShoppingCart(shoppingCartRequestDto);
      if (null != ps && ! ps.isEmpty()) {
        for (ShoppingCartResponseDto shoppingCartResponseDto : ps) {
          this.shoppingCartService.deleteShoppingCart(shoppingCartResponseDto.getCartId());
        }
      }
    }
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
    orderRequestDto.setOpenId(distributor.getOpenId());
    orderRequestDto.setState(OrderStateEnum.nopay.state());
    orderRequestDto.setType(OrderTypeEnum.behalf.type());
    orderRequestDto.setCreateTime(new Date());
    OrderResponseDto orderResponseDto = this.orderCache.saveOrder(orderRequestDto);
    Long orderId = orderResponseDto.getOrderId();
    for (OrderDetail orderDetail : details) {
      orderDetail.setOrderId(orderId);
    }
    this.orderDetailCache.saveOrderDetail(orderId, details);
    
    OrderRequestDto param = new OrderRequestDto();
    param.setOrderId(orderId);
    param.setOrderCode(1000000 + orderId);
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
    OrderRequestDto param = new OrderRequestDto();
    param.setOrderId(orderId);
    param.setDeleted(DeletedEnum.delete.val());
    this.orderCache.updateOrder(param);
  }

  public void deleteOrder(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] orderIds = idstr.split(",");
    for (String str : orderIds) {
      Long orderId = Long.parseLong(str);
//      this.orderCache.deleteOrder(orderId);
      OrderRequestDto param = new OrderRequestDto();
      param.setOrderId(orderId);
      param.setDeleted(DeletedEnum.delete.val());
      this.orderCache.updateOrder(param);
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
    if (null != orderRequestDto.getTime()) {
      orderRequestDto.setBeginCreateTime(null);
      orderRequestDto.setEndCreateTime(null);
      switch (orderRequestDto.getTime()) {
      case 0: //今天
        orderRequestDto.setBeginCreateTime(new Date(LocalDateTime.of(LocalDate.now(), LocalTime.ofNanoOfDay(0)).toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        break;
      case 1: //本周
        Date date = DateUtils.getMondayOfThisWeek();
        orderRequestDto.setBeginCreateTime(new Date(LocalDateTime.of(date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDate(), LocalTime.ofNanoOfDay(0)).toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        break;
      case 2: //本月
        Date date2 = DateUtils.getFirstOfThisMonth();
        orderRequestDto.setBeginCreateTime(new Date(LocalDateTime.of(date2.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDate(), LocalTime.ofNanoOfDay(0)).toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        break;
      default: //全部
        break;
      }
    }
    List<OrderResponseDto> orders = this.orderCache.queryOrder(orderRequestDto);
    if (null != orders && ! orders.isEmpty()) {
      for (OrderResponseDto orderResponseDto : orders) {
        List<OrderDetail> details = orderDetailCache.getOrderDetails(orderResponseDto.getOrderId());
        orderResponseDto.setDetails(details);
      }
    }
    return orders;
  }

  public Long queryOrderCount(OrderRequestDto orderRequestDto) {
    return this.orderCache.queryOrderCount(orderRequestDto);
  }

  public void pickupOrder(Long orderId) {
    OrderRequestDto param = new OrderRequestDto();
    param.setOrderId(orderId);
    param.setState(OrderStateEnum.success.state());
    this.orderCache.updateOrder(param);
  }

  /**
   * 查询没有支付的供应的总数量
   * @param productId
   * @return
   */
  public Long getProductNoPayNumber(Long productId) {
    return orderDetailCache.getProductNoPayNumber(productId, OrderStateEnum.nopay.state());
  }

  public void deleteOrderJob() {
    int pageNum = 1;
    int pageSize = 50;
    OrderRequestDto param = new OrderRequestDto();
    param.setState(OrderStateEnum.nopay.state());
    param.setDeleted(DeletedEnum.notdelete.val());
    param.setMinutes30(0);
    while(true) {
      param.setPager(new Page(pageNum, pageSize, null, null));
      List<OrderResponseDto> orders = this.orderCache.queryOrder(param);
      pageNum ++;
      if (null != orders && ! orders.isEmpty()) {
        for (OrderResponseDto orderResponseDto : orders) {
          OrderRequestDto orderRequestDto = new OrderRequestDto();
          orderRequestDto.setOrderId(orderResponseDto.getOrderId());
          orderRequestDto.setDeleted(DeletedEnum.delete.val());
          orderRequestDto.setDeleteUser(DeleteUserTypeEnum.system.getType());
          this.orderCache.updateOrder(orderRequestDto);
        }
      }else {
        break;
      }
    }
  }

  public List<OrderDetailResponseDto> buyRecord(Long productId, Integer pageNum, Integer pageSize) {
    if (null == pageNum) {
      pageNum = 1;
    }
    if (null == pageSize) {
      pageSize = 10;
    }
    Page page = new Page(pageNum, pageSize, null, null);
    List<OrderDetailResponseDto> list = this.orderDetailCache.buyRecord(productId, page);
    return list;
  }

  public Map<String, Integer> buyRecordTotal(Long productId) {
    return this.orderDetailCache.buyRecordTotal(productId);
  }

  /**
   * 订单总比
   * 订单排名
   * 今日收益
   * 累计收益
   * @param distributorId
   * @return
   */
  public Map<String, Object> shopIndex(Long distributorId) {
    Long totalOrderNumOfToday = orderCache.totalOrderNumOfToday();
    
    Map<String, Object> map = new HashMap<>();
    map.put("totalOrderNumOfToday", totalOrderNumOfToday);
    map.put("totalMoney", orderCache.totalMoney(distributorId, null));
    
    List<OrderTotal> orderIndex = orderCache.orderIndexToday(distributorId, null, null);
    if (null == orderIndex || orderIndex.isEmpty()) {
      map.put("orderNumOfToday", 0);
      map.put("orderNumRateOfToday", 0);
      map.put("rankingOfToday", 0);
      map.put("totalMoneyOfToday", 0);
    }else {
      OrderTotal orderTotal = orderIndex.get(0);
      map.put("orderNumOfToday", orderTotal.getTotalOrderNum());
      map.put("orderNumRateOfToday", orderTotal.getTotalOrderNum() / (totalOrderNumOfToday * 1.0));
      map.put("rankingOfToday", orderTotal.getIdx());
      map.put("totalMoneyOfToday", orderTotal.getTotalOrderMoney());
    }
    return map;
  }

  public List<OrderTotal> orderNumRanking(Integer pageNum, Integer pageSize) {
    if (null == pageNum) {
      pageNum = 1;
    }
    if (null == pageSize) {
      pageSize = 10;
    }
    Integer startIndex = (pageNum - 1) * pageSize;
    List<OrderTotal> list = orderCache.orderIndexToday(null, startIndex, pageSize);
    return list;
  }

  public Long sumBrokerage(OrderRequestDto orderRequestDto) {
    if (null != orderRequestDto.getTime()) {
      orderRequestDto.setBeginCreateTime(null);
      orderRequestDto.setEndCreateTime(null);
      switch (orderRequestDto.getTime()) {
      case 0: //今天
        orderRequestDto.setBeginCreateTime(new Date(LocalDateTime.of(LocalDate.now(), LocalTime.ofNanoOfDay(0)).toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        break;
      case 1: //本周
        Date date = DateUtils.getMondayOfThisWeek();
        orderRequestDto.setBeginCreateTime(new Date(LocalDateTime.of(date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDate(), LocalTime.ofNanoOfDay(0)).toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        break;
      case 2: //本月
        Date date2 = DateUtils.getFirstOfThisMonth();
        orderRequestDto.setBeginCreateTime(new Date(LocalDateTime.of(date2.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDate(), LocalTime.ofNanoOfDay(0)).toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        break;
      default: //全部
        break;
      }
    }
    Long sum = this.orderCache.sumBrokerage(orderRequestDto);
    return sum;
  }

  public List<Map<String, Object>> seeUser(Long distributorId, String keyword, Integer pageNum, Integer pageSize) {
    Page pager = new Page(null == pageNum ? 1 : pageNum, pageSize, null, null);
    return this.orderCache.seeUser(distributorId, keyword, pager);
  }

}