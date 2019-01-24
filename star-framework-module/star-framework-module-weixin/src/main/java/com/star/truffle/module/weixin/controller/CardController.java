/**create by liuhua at 2019年1月24日 下午4:23:48**/
package com.star.truffle.module.weixin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.module.order.dto.req.DeliveryAddressRequestDto;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/card")
public class CardController {

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/card/list";
  }
  
//  @ResponseBody
//  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
//  public Map<String, Object> list(DeliveryAddressRequestDto deliveryAddressRequestDto, Integer page, Integer rows, String sord, String sidx) {
//    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
//    deliveryAddressRequestDto.setPager(pager);
//    List<DeliveryAddressResponseDto> list = deliveryAddressService.queryDeliveryAddress(deliveryAddressRequestDto);
//    Long count = deliveryAddressService.queryDeliveryAddressCount(deliveryAddressRequestDto);
//
//    Map<String, Object> map = new HashMap<>();
//    map.put("page", pager.getPageNum());
//    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
//    map.put("records", count);
//    map.put("rows", list);
//    return map;
//  }
}
