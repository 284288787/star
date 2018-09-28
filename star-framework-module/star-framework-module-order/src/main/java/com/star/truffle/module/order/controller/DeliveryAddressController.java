/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.module.order.dto.req.DeliveryAddressRequestDto;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;
import com.star.truffle.module.order.service.DeliveryAddressService;

@Controller
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController {

  @Autowired
  private DeliveryAddressService deliveryAddressService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/deliveryAddress/list";
  }

  @RequestMapping(value = "/editBefore/{id}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long id, Model model) {
    DeliveryAddressResponseDto deliveryAddressResponseDto = this.deliveryAddressService.getDeliveryAddress(id);
    model.addAttribute("deliveryAddressResponseDto", deliveryAddressResponseDto);
    return "mgr/deliveryAddress/editDeliveryAddress";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(DeliveryAddressRequestDto deliveryAddressRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    deliveryAddressRequestDto.setPager(pager);
    List<DeliveryAddressResponseDto> list = deliveryAddressService.queryDeliveryAddress(deliveryAddressRequestDto);
    Long count = deliveryAddressService.queryDeliveryAddressCount(deliveryAddressRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

}