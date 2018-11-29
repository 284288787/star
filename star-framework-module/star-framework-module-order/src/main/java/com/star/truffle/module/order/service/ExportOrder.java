/**create by liuhua at 2018年11月21日 下午9:51:33**/
package com.star.truffle.module.order.service;

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
import com.star.truffle.module.product.constant.ProductConstant;

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
          name = detail.getDistributorName();
          mobile = detail.getShopMobile();
          address = detail.getShopAddress();
        }
        //订单编号,订单状态,提货号,代客下单,收货类型,收货人,联系电话,收货地址,购买数量,购买单价,小计(数量*单价),商品名称,商品规格,店铺名称,下单日期,会员姓名,会员手机,订单备注
        String[] arr = {orderCode, state, pickupCode, typeName, deliveryType, name, mobile, address, detail.getCount() + "",
            ProductConstant.formatMoney(detail.getPrice()), ProductConstant.formatMoney(detail.getPrice() * detail.getCount()), 
            detail.getTitle(), detail.getSpecification(), detail.getShopName(), DateUtils.formatDateTime(detail.getCreateTime()), detail.getName(), 
            detail.getMobile(), detail.getRemark()};
        list.add(arr);
      }
    }
    return list;
  }

  @Override
  public int getPageSize() {
    return 5000;
  }
}
