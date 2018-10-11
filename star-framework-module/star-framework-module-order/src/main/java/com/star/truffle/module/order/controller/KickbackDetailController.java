/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.order.constant.KickbackStateEnum;
import com.star.truffle.module.order.dto.req.KickbackDetailRequestDto;
import com.star.truffle.module.order.dto.res.KickbackDetailResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.service.KickbackDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/kickbackDetail")
public class KickbackDetailController {

  @Autowired
  private KickbackDetailService kickbackDetailService;
  
  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/kickbackDetail/list";
  }

  @ResponseBody
  @RequestMapping(value = "/detail/{id}", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> detail(@PathVariable Long id, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, null);
    List<OrderResponseDto> list = kickbackDetailService.detail(id, pager);
    Long count = kickbackDetailService.detailCount(id, pager);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(KickbackDetailRequestDto kickbackDetailRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, null);
    kickbackDetailRequestDto.setPager(pager);
    List<KickbackDetailResponseDto> list = kickbackDetailService.queryKickbackDetail(kickbackDetailRequestDto);
    Long count = kickbackDetailService.queryKickbackDetailCount(kickbackDetailRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }
  
  @ResponseBody
  @RequestMapping(value = "/pass/{id}", method = RequestMethod.POST)
  public ApiResult<Void> pass(@PathVariable Long id) {
    try {
      kickbackDetailService.changeState(id, KickbackStateEnum.remittance.state(), null);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/nopass/{id}", method = RequestMethod.POST)
  public ApiResult<Void> nopass(@PathVariable Long id, String reject) {
    try {
      kickbackDetailService.changeState(id, KickbackStateEnum.nopass.state(), reject);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = "/finish/{id}", method = RequestMethod.POST)
  public ApiResult<Void> finish(@PathVariable Long id, String reject) {
    try {
      kickbackDetailService.changeState(id, KickbackStateEnum.finish.state(), null);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}