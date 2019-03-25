/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.weixin.controller;

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
import com.star.truffle.module.weixin.domain.Coupon;
import com.star.truffle.module.weixin.dto.req.CouponRequestDto;
import com.star.truffle.module.weixin.dto.res.CouponResponseDto;
import com.star.truffle.module.weixin.service.CouponService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/coupon")
public class CouponController {

  @Autowired
  private CouponService couponService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/coupon/list";
  }

  @RequestMapping(value = "/editBefore/{couponId}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long couponId, Model model) {
    CouponResponseDto couponResponseDto = this.couponService.getCoupon(couponId);
    model.addAttribute("couponResponseDto", couponResponseDto);
    return "mgr/coupon/editCoupon";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(CouponRequestDto couponRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    couponRequestDto.setPager(pager);
    List<CouponResponseDto> list = couponService.queryCoupon(couponRequestDto);
    Long count = couponService.queryCouponCount(couponRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(@RequestBody Coupon coupon) {
    try {
      Long id = couponService.saveCoupon(coupon);
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
  public ApiResult<Void> edit(@RequestBody CouponRequestDto couponRequestDto) {
    try {
      couponService.updateCoupon(couponRequestDto);
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
      couponService.deleteCoupon(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}