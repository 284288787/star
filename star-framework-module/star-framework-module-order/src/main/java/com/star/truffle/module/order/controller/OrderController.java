/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import lombok.extern.slf4j.Slf4j;
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.domain.OrderDetail;
import com.star.truffle.module.order.service.OrderService;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;

@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/order/list";
  }

  @RequestMapping(value = "/editBefore/{orderId}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long orderId, Model model) {
    OrderResponseDto orderResponseDto = this.orderService.getOrder(orderId);
    model.addAttribute("orderResponseDto", orderResponseDto);
    return "mgr/order/editOrder";
  }
  
  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(OrderRequestDto orderRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    orderRequestDto.setPager(pager);
    List<OrderResponseDto> list = orderService.queryOrder(orderRequestDto);
    Long count = orderService.queryOrderCount(orderRequestDto);
    
    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/listDetails", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> listDetails(OrderDetail orderDetail, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    OrderResponseDto orderResponseDto = this.orderService.getOrder(orderDetail.getOrderId());
    List<OrderDetail> list = orderResponseDto.getDetails();
    int count = 0;
    if (null != list && ! list.isEmpty()) {
      count = list.size();
    }

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(@RequestBody Order order) {
    try {
      Long id = orderService.saveOrder(order);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public ApiResult<Void> edit(@RequestBody OrderRequestDto orderRequestDto) {
    try {
      orderService.updateOrder(orderRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = "/deleted", method = RequestMethod.POST)
  public ApiResult<Void> delete(String ids) {
    try {
      orderService.deleteOrder(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}