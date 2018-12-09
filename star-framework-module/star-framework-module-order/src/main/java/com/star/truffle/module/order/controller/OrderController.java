/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import com.star.truffle.module.order.domain.Order;
import com.star.truffle.module.order.domain.OrderDetail;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.OrderDetailResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许为空, false:不允许为空
  }
  
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
    Long count = orderService.queryOrderCount(orderRequestDto);
    List<OrderResponseDto> list = new ArrayList<>();
    if (count > 0) {
      list = orderService.queryOrder(orderRequestDto);
    }
    
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
//    OrderResponseDto orderResponseDto = this.orderService.getOrder(orderDetail.getOrderId());
    List<OrderDetailResponseDto> list = orderService.getDetails(orderDetail.getOrderId());
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
  
  @ResponseBody
  @RequestMapping(value = "/deliverGoods", method = RequestMethod.POST)
  public ApiResult<Void> deliverGoods(Long orderId, String expressNumber) {
    try {
      orderService.deliverGoods(orderId, expressNumber);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/updateExpressNumber", method = RequestMethod.POST)
  public ApiResult<Void> updateExpressNumber(Long orderId, String expressNumber) {
    try {
      orderService.updateExpressNumber(orderId, expressNumber);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/setDiscountedPrice", method = RequestMethod.POST)
  public ApiResult<Void> setDiscountedPrice(Long orderId, Double price) {
    try {
      orderService.setDiscountedPrice(orderId, price);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/updateRemark", method = RequestMethod.POST)
  public ApiResult<Void> updateRemark(Long orderId, String remark) {
    try {
      orderService.updateRemark(orderId, remark);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/deliverGoodsFinish", method = RequestMethod.POST)
  public ApiResult<Void> deliverGoodsFinish(Long orderId) {
    try {
      orderService.deliverGoodsFinish(orderId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/getDistributorIds", method = RequestMethod.POST)
  public ApiResult<List<Map<String, Object>>> getDistributorIds(String beginTime, String endTime, String states, String transportStates) {
    try {
      List<Map<String, Object>> ids = orderService.getDistributorIds(beginTime, endTime, states, transportStates);
      return ApiResult.success(ids);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/getDistributorTotal", method = RequestMethod.POST)
  public ApiResult<Map<String, Object>> getDistributorTotal(Long distributorId) {
    try {
      Map<String, Object> dtrd = orderService.getDistributorTotal(distributorId);
      return ApiResult.success(dtrd);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}