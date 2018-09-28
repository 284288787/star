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
import com.star.truffle.module.order.domain.OrderAfterSale;
import com.star.truffle.module.order.service.OrderAfterSaleService;
import com.star.truffle.module.order.dto.req.OrderAfterSaleRequestDto;
import com.star.truffle.module.order.dto.res.OrderAfterSaleResponseDto;

@Slf4j
@Controller
@RequestMapping("/orderAfterSale")
public class OrderAfterSaleController {

  @Autowired
  private OrderAfterSaleService orderAfterSaleService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/orderAfterSale/list";
  }

  @RequestMapping(value = "/editBefore/{id}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long id, Model model) {
    OrderAfterSaleResponseDto orderAfterSaleResponseDto = this.orderAfterSaleService.getOrderAfterSale(id);
    model.addAttribute("orderAfterSaleResponseDto", orderAfterSaleResponseDto);
    return "mgr/orderAfterSale/editOrderAfterSale";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(OrderAfterSaleRequestDto orderAfterSaleRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    orderAfterSaleRequestDto.setPager(pager);
    List<OrderAfterSaleResponseDto> list = orderAfterSaleService.queryOrderAfterSale(orderAfterSaleRequestDto);
    Long count = orderAfterSaleService.queryOrderAfterSaleCount(orderAfterSaleRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(@RequestBody OrderAfterSale orderAfterSale) {
    try {
      Long id = orderAfterSaleService.saveOrderAfterSale(orderAfterSale);
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
  public ApiResult<Void> edit(@RequestBody OrderAfterSaleRequestDto orderAfterSaleRequestDto) {
    try {
      orderAfterSaleService.updateOrderAfterSale(orderAfterSaleRequestDto);
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
      orderAfterSaleService.deleteOrderAfterSale(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}