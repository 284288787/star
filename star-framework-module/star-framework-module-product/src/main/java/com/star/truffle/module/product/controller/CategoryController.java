/**create by framework at 2018年11月07日 09:43:30**/
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
import com.star.truffle.module.product.domain.Category;
import com.star.truffle.module.product.service.CategoryService;
import com.star.truffle.module.product.dto.req.CategoryRequestDto;
import com.star.truffle.module.product.dto.res.CategoryResponseDto;

@Slf4j
@Controller
@RequestMapping("/category")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/category/list";
  }

  @RequestMapping(value = "/editBefore/{cateId}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long cateId, Model model) {
    CategoryResponseDto categoryResponseDto = this.categoryService.getCategory(cateId);
    model.addAttribute("categoryResponseDto", categoryResponseDto);
    return "mgr/category/editCategory";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(CategoryRequestDto categoryRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    categoryRequestDto.setPager(pager);
    List<CategoryResponseDto> list = categoryService.queryCategory(categoryRequestDto);
    Long count = categoryService.queryCategoryCount(categoryRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(@RequestBody Category category) {
    try {
      Long id = categoryService.saveCategory(category);
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
  public ApiResult<Void> edit(@RequestBody CategoryRequestDto categoryRequestDto) {
    try {
      categoryService.updateCategory(categoryRequestDto);
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
      categoryService.deleteCategory(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}