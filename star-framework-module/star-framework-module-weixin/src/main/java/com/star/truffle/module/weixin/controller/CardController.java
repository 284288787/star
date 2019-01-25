/**create by liuhua at 2019年1月24日 下午4:23:48**/
package com.star.truffle.module.weixin.controller;

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
import com.star.truffle.module.weixin.dto.CardReq;
import com.star.truffle.module.weixin.dto.req.CouponRequestDto;
import com.star.truffle.module.weixin.dto.res.CouponResponseDto;
import com.star.truffle.module.weixin.service.CouponService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/card")
public class CardController {

  @Autowired
  private CouponService couponService;
  
  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/card/list";
  }
  
  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String create() {
    return "mgr/card/create";
  }
  
  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(CardReq cardReq) {
    try {
      Long id = couponService.saveCard(cardReq);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(CouponRequestDto couponRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    couponRequestDto.setPager(pager);
    Long count = couponService.queryCouponCount(couponRequestDto);
    Map<String, Object> map = new HashMap<>();
    if (count > 0) {
      List<CouponResponseDto> list = couponService.queryCoupon(couponRequestDto);
      map.put("rows", list);
    }

    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    return map;
  }
}
