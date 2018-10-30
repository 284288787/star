/**create by framework at 2018年10月26日 09:40:50**/
package com.star.truffle.module.member.controller;

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
import com.star.truffle.module.member.constant.DistributorApplyStateEnum;
import com.star.truffle.module.member.dto.req.DistributorApplyRequestDto;
import com.star.truffle.module.member.dto.res.DistributorApplyResponseDto;
import com.star.truffle.module.member.service.DistributorApplyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/distributorApply")
public class DistributorApplyController {

  @Autowired
  private DistributorApplyService distributorApplyService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/distributorApply/list";
  }

  @RequestMapping(value = "/editBefore/{id}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long id, Model model) {
    DistributorApplyResponseDto distributorApplyResponseDto = this.distributorApplyService.getDistributorApply(id);
    model.addAttribute("distributorApplyResponseDto", distributorApplyResponseDto);
    return "mgr/distributorApply/editDistributorApply";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(DistributorApplyRequestDto distributorApplyRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    distributorApplyRequestDto.setPager(pager);
    List<DistributorApplyResponseDto> list = distributorApplyService.queryDistributorApply(distributorApplyRequestDto);
    Long count = distributorApplyService.queryDistributorApplyCount(distributorApplyRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/pass/{id}", method = RequestMethod.POST)
  public ApiResult<Void> pass(@PathVariable Long id, Long regionId) {
    try {
      distributorApplyService.pass(id, regionId);
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
      distributorApplyService.changeState(id, DistributorApplyStateEnum.nopass.getState(), reject);
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
      distributorApplyService.deleteDistributorApply(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}