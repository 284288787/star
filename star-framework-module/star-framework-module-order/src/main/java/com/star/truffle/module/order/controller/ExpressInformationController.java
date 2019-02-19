/**create by framework at 2019年02月19日 14:19:41**/
package com.star.truffle.module.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.order.dto.req.ExpressInformationRequestDto;
import com.star.truffle.module.order.dto.res.ExpressInformationResponseDto;
import com.star.truffle.module.order.service.ExpressInformationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/expressInformation")
public class ExpressInformationController {

  @Autowired
  private ExpressInformationService expressInformationService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/expressInformation/list";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(ExpressInformationRequestDto expressInformationRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    expressInformationRequestDto.setPager(pager);
    List<ExpressInformationResponseDto> list = expressInformationService.queryExpressInformation(expressInformationRequestDto);
    Long count = expressInformationService.queryExpressInformationCount(expressInformationRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/deleted", method = RequestMethod.POST)
  public ApiResult<Void> delete(String ids) {
    try {
      expressInformationService.deleteExpressInformation(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}