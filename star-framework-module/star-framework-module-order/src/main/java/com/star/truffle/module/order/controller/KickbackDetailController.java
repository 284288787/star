/**create by framework at 2018年10月11日 11:07:21**/
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
import com.star.truffle.module.order.domain.KickbackDetail;
import com.star.truffle.module.order.service.KickbackDetailService;
import com.star.truffle.module.order.dto.req.KickbackDetailRequestDto;
import com.star.truffle.module.order.dto.res.KickbackDetailResponseDto;

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

  @RequestMapping(value = "/editBefore/{id}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long id, Model model) {
    KickbackDetailResponseDto kickbackDetailResponseDto = this.kickbackDetailService.getKickbackDetail(id);
    model.addAttribute("kickbackDetailResponseDto", kickbackDetailResponseDto);
    return "mgr/kickbackDetail/editKickbackDetail";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(KickbackDetailRequestDto kickbackDetailRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
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
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(@RequestBody KickbackDetail kickbackDetail) {
    try {
      Long id = kickbackDetailService.saveKickbackDetail(kickbackDetail);
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
  public ApiResult<Void> edit(@RequestBody KickbackDetailRequestDto kickbackDetailRequestDto) {
    try {
      kickbackDetailService.updateKickbackDetail(kickbackDetailRequestDto);
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
      kickbackDetailService.deleteKickbackDetail(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}