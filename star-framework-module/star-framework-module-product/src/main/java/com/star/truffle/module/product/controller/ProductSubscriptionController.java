/**create by framework at 2018年09月18日 10:59:57**/
package com.star.truffle.module.product.controller;

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
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import lombok.extern.slf4j.Slf4j;
import com.star.truffle.module.product.domain.ProductSubscription;
import com.star.truffle.module.product.service.ProductSubscriptionService;
import com.star.truffle.module.product.dto.req.ProductSubscriptionRequestDto;
import com.star.truffle.module.product.dto.res.ProductSubscriptionResponseDto;

@Slf4j
@Controller
@RequestMapping("/productSubscription")
public class ProductSubscriptionController {

  @Autowired
  private ProductSubscriptionService productSubscriptionService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/productSubscription/list";
  }

  @RequestMapping(value = "/editBefore/{id}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long id, Model model) {
    ProductSubscriptionResponseDto productSubscriptionResponseDto = this.productSubscriptionService.getProductSubscription(id);
    model.addAttribute("productSubscriptionResponseDto", productSubscriptionResponseDto);
    return "mgr/productSubscription/editProductSubscription";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(ProductSubscriptionRequestDto productSubscriptionRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    productSubscriptionRequestDto.setPager(pager);
    List<ProductSubscriptionResponseDto> list = productSubscriptionService.queryProductSubscription(productSubscriptionRequestDto);
    Long count = productSubscriptionService.queryProductSubscriptionCount(productSubscriptionRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(ProductSubscription productSubscription) {
    try {
      Long id = productSubscriptionService.saveProductSubscription(productSubscription);
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
  public ApiResult<Void> edit(ProductSubscriptionRequestDto productSubscriptionRequestDto) {
    try {
      productSubscriptionService.updateProductSubscription(productSubscriptionRequestDto);
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
      productSubscriptionService.deleteProductSubscription(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}