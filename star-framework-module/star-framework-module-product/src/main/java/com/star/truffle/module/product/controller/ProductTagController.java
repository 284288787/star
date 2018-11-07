/**create by framework at 2018年11月07日 14:22:22**/
package com.star.truffle.module.product.controller;

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
import com.star.truffle.module.product.domain.ProductTag;
import com.star.truffle.module.product.service.ProductTagService;
import com.star.truffle.module.product.dto.req.ProductTagRequestDto;
import com.star.truffle.module.product.dto.res.ProductTagResponseDto;

@Slf4j
@Controller
@RequestMapping("/productTag")
public class ProductTagController {

  @Autowired
  private ProductTagService productTagService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/productTag/list";
  }

  @RequestMapping(value = "/editBefore/{tagId}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long tagId, Model model) {
    ProductTagResponseDto productTagResponseDto = this.productTagService.getProductTag(tagId);
    model.addAttribute("productTagResponseDto", productTagResponseDto);
    return "mgr/productTag/editProductTag";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(ProductTagRequestDto productTagRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    productTagRequestDto.setPager(pager);
    List<ProductTagResponseDto> list = productTagService.queryProductTag(productTagRequestDto);
    Long count = productTagService.queryProductTagCount(productTagRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(@RequestBody ProductTag productTag) {
    try {
      Long id = productTagService.saveProductTag(productTag);
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
  public ApiResult<Void> edit(@RequestBody ProductTagRequestDto productTagRequestDto) {
    try {
      productTagService.updateProductTag(productTagRequestDto);
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
      productTagService.deleteProductTag(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}