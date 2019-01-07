/**create by liuhua at 2018年11月16日 下午8:50:52**/
package com.star.truffle.module.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.star.truffle.common.importdata.AbstractDataExport;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.util.DateUtils;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;
import com.star.truffle.module.member.service.DistributorService;
import com.star.truffle.module.order.cache.OrderDetailCache;
import com.star.truffle.module.order.constant.DeliveryTypeEnum;
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.dto.req.OrderDetailRequestDto;
import com.star.truffle.module.order.dto.res.OrderDetailResponseDto;
import com.star.truffle.module.product.constant.ProductConstant;

public class ImportData extends AbstractDataExport<Order> {

  private DistributorService distributorService;
  private OrderDetailCache orderDetailCache;
  
  @Override
  public Map<String, Object> getTemplateDatas() {
    Map<String, Object> params = getParams();
    String beginTime = params.get("beginTime") + "";
    String endTime = params.get("endTime") + "";
    Long distributorId = Long.parseLong(params.get("distributorId") + "");
    DistributorResponseDto distributor = this.distributorService.getDistributor(distributorId);
    Map<String, Object> map = new HashMap<>();
    map.put("shopCode", distributor.getShopCode());
    map.put("shopName", distributor.getShopName());
    map.put("orderDate", beginTime + (beginTime.equals(endTime) ? "" : "至" + endTime));
    map.put("useObj", "门店");
    map.put("shopMobile", distributor.getMobile());
    map.put("shopAddress", distributor.getAddress());
    return map;
  }

  @Override
  public List<String[]> getRecordsData(Map<String, Object> params, int pageNumber, int pageSize) {
    List<String[]> list = new ArrayList<>();
    OrderDetailRequestDto orderDetailRequestDto = new OrderDetailRequestDto();
    orderDetailRequestDto.setDistributorId(Long.parseLong(params.get("distributorId") + ""));
    orderDetailRequestDto.setStates(params.get("states") + "");
    orderDetailRequestDto.setTransportStates(params.get("transportStates") + "");
    String beginTime = params.get("beginTime") + "";
    if (beginTime.length() > 4) {
      orderDetailRequestDto.setBeginCreateTime(DateUtils.toDateYmdHms(beginTime + " 00:00:00"));
    }
    String endTime = params.get("endTime") + "";
    if (endTime.length() > 4) {
      orderDetailRequestDto.setEndCreateTime(DateUtils.toDateYmdHms(endTime + " 23:59:59"));
    }
    Page pager = new Page(pageNumber, pageSize, null, null);
    orderDetailRequestDto.setPager(pager);
    List<OrderDetailResponseDto> details = this.orderDetailCache.queryOrderDetail(orderDetailRequestDto);
    if (null != details && ! details.isEmpty()) {
      int index = 1;
      for (OrderDetailResponseDto detail : details) {
        if (detail.getSaleafter() == 1) { //已退货/退货中 不导出
          continue;
        }
        String pickupCode = StringUtils.isBlank(detail.getPickupCode()) ? "" : detail.getPickupCode();
//        String typeName = detail.getType() == OrderTypeEnum.self.type() ? OrderTypeEnum.self.caption() : OrderTypeEnum.behalf.caption();
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
        //商品名称,商品规格,订单号,提货号,收货人,收货电话,收货地址,会员姓名,会员电话,下单日期,单价,小计,数量,订单备注
        String[] arr = {"" + index++, detail.getOrderCode(), pickupCode, detail.getTitle(), detail.getSpecification(), 
            name, mobile, address, detail.getName(), detail.getMobile(), DateUtils.formatDate(detail.getCreateTime(), "MM/dd\r\nHH点mm分"), 
            ProductConstant.formatMoney(detail.getPrice()), 
            ProductConstant.formatMoney(detail.getCount() * detail.getPrice()), detail.getCount() + "", detail.getRemark()};
        list.add(arr);
      }
    }
    return list;
  }

  @Override
  public void getApplication(ApplicationContext applicationContext) {
    this.distributorService = applicationContext.getBean(DistributorService.class);
    this.orderDetailCache = applicationContext.getBean(OrderDetailCache.class);
  }

}
