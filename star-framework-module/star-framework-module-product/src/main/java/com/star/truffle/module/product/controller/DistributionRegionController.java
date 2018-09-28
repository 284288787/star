/**create by framework at 2018年09月04日 10:28:04**/
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import lombok.extern.slf4j.Slf4j;
import com.star.truffle.module.product.domain.DistributionRegion;
import com.star.truffle.module.product.service.DistributionRegionService;
import com.star.truffle.module.product.dto.req.DistributionRegionRequestDto;
import com.star.truffle.module.product.dto.res.DistributionRegionResponseDto;

@Slf4j
@Controller
@RequestMapping("/distributionRegion")
public class DistributionRegionController {

  @Autowired
  private DistributionRegionService distributionRegionService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/distributionRegion/list";
  }

  @RequestMapping(value = "/editBefore/{regionId}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long regionId, Model model) {
    DistributionRegionResponseDto distributionRegionResponseDto = this.distributionRegionService.getDistributionRegion(regionId);
    model.addAttribute("distributionRegionResponseDto", distributionRegionResponseDto);
    return "mgr/distributionRegion/editDistributionRegion";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(DistributionRegionRequestDto distributionRegionRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    distributionRegionRequestDto.setPager(pager);
    distributionRegionRequestDto.setStates("1,2");
    List<DistributionRegionResponseDto> list = distributionRegionService.queryDistributionRegion(distributionRegionRequestDto);
    Long count = distributionRegionService.queryDistributionRegionCount(distributionRegionRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(DistributionRegion distributionRegion) {
    try {
      Long id = distributionRegionService.saveDistributionRegion(distributionRegion);
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
  public ApiResult<Void> edit(DistributionRegionRequestDto distributionRegionRequestDto) {
    try {
      distributionRegionService.updateDistributionRegion(distributionRegionRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @ResponseBody
  @RequestMapping(value = "/enabled", method = RequestMethod.POST)
  public ApiResult<Void> enabled(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.distributionRegionService.enabledDistributionRegion(ids);
      apiResult = ApiResult.success();
    } catch (StarServiceException e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
    return apiResult;
  }

  @ResponseBody
  @RequestMapping(value = "/disabled", method = RequestMethod.POST)
  public ApiResult<Void> disabled(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.distributionRegionService.disabledDistributionRegion(ids);
      apiResult = ApiResult.success();
    } catch (StarServiceException e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
    return apiResult;
  }
  
  @ResponseBody
  @RequestMapping(value = "/deleted", method = RequestMethod.POST)
  public ApiResult<Void> delete(String ids) {
    try {
      distributionRegionService.deleteDistributionRegion(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}