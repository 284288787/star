/**create by liuhua at 2018年11月21日 下午9:51:33**/
package com.star.truffle.module.order.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.star.truffle.common.importdata.AbstractDataExport;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.util.DateUtils;
import com.star.truffle.module.order.cache.OrderDetailCache;
import com.star.truffle.module.order.constant.DeliveryTypeEnum;
import com.star.truffle.module.order.constant.OrderStateEnum;
import com.star.truffle.module.order.constant.OrderTypeEnum;
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.dto.req.OrderDetailRequestDto;
import com.star.truffle.module.order.dto.res.OrderDetailResponseDto;

public class ExportOrder extends AbstractDataExport<Order> {
  private OrderDetailCache orderDetailCache;
  private StarJson starJson;
  
  @Override
  public void getApplication(ApplicationContext applicationContext) {
    this.orderDetailCache = applicationContext.getBean(OrderDetailCache.class);
    this.starJson = applicationContext.getBean(StarJson.class);
  }

  @Override
  public Map<String, Object> getTemplateDatas() {
    return new HashMap<>();
  }

  @Override
  public List<String[]> getRecordsData(Map<String, Object> params, int pageNumber, int pageSize) {
    List<String[]> list = new ArrayList<>();
    OrderDetailRequestDto orderDetailRequestDto = starJson.map2Bean(params, OrderDetailRequestDto.class);
    Page pager = new Page(pageNumber, pageSize, null, null);
    orderDetailRequestDto.setPager(pager);
    List<OrderDetailResponseDto> details = this.orderDetailCache.queryOrderDetail(orderDetailRequestDto);
    if (null != details && ! details.isEmpty()) {
      for (OrderDetailResponseDto detail : details) {
        String orderCode = detail.getOrderCode();
        String state = Arrays.stream(OrderStateEnum.values()).filter(en -> en.state() == detail.getState()).findFirst().get().caption();
        String pickupCode = StringUtils.isBlank(detail.getPickupCode()) ? "" : detail.getPickupCode();
        String typeName = detail.getType() == OrderTypeEnum.self.type() ? OrderTypeEnum.self.caption() : OrderTypeEnum.behalf.caption();
        String deliveryType = detail.getDeliveryType() == DeliveryTypeEnum.self.type() ? DeliveryTypeEnum.self.caption() : DeliveryTypeEnum.express.caption();
        String name = detail.getDeliveryName();
        String mobile = detail.getDeliveryMobile();
        String address = "";
        if (StringUtils.isNotBlank(detail.getProvinceName())) {
          address += detail.getProvinceName();
        }
        if (StringUtils.isNotBlank(detail.getCityName())) {
          address += detail.getCityName();
        }
        if (StringUtils.isNotBlank(detail.getAreaName())) {
          address += detail.getAreaName();
        }
        address += detail.getDeliveryAddress();
        if (detail.getDeliveryType() == DeliveryTypeEnum.self.type()) {
          name = detail.getShopName();
          mobile = detail.getShopMobile();
          address = detail.getShopAddress();
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");  
        String[] arr = {orderCode, state, pickupCode, typeName, deliveryType, name, mobile, address, detail.getCount() + "", decimalFormat.format(detail.getPrice() / 100.0), decimalFormat.format((detail.getPrice() * detail.getCount()) / 100.0), detail.getTitle(), detail.getShopName(), DateUtils.formatDateTime(detail.getCreateTime()), detail.getName(), detail.getMobile()};
        list.add(arr);
      }
    }
    return list;
  }

  @Override
  public int getPageSize() {
    return 30;
  }
}
