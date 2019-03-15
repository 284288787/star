/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.member.dto.req.DistributorRequestDto;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;
import com.star.truffle.module.member.service.DistributorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/distributor")
public class DistributorController {

  @Autowired
  private DistributorService distributorService;

  @ResponseBody
  @RequestMapping(value = "/changeLevel", method = RequestMethod.POST)
  public ApiResult<Void> changeLevel(Long distributorId, Long parentDistributorId) {
    try {
      distributorService.changeLevel(distributorId, parentDistributorId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/tree", method = {RequestMethod.POST, RequestMethod.GET})
  public List<DistributorResponseDto> tree(DistributorRequestDto distributorRequestDto) {
    List<DistributorResponseDto> distributors = distributorService.queryDistributor(distributorRequestDto);
    return distributors;
  }
  
  @RequestMapping(value = "/operators", method = RequestMethod.GET)
  public String operators() {
    return "mgr/distributor/operators";
  }
  
  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/distributor/list";
  }

  @RequestMapping(value = "/editBefore/{distributorId}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long distributorId, Model model) {
    DistributorResponseDto distributorResponseDto = this.distributorService.getDistributor(distributorId);
    model.addAttribute("distributorResponseDto", distributorResponseDto);
    return "mgr/distributor/editDistributor";
  }

  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(DistributorRequestDto distributorRequestDto, Integer page, Integer rows, String sord, String sidx) {
    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    distributorRequestDto.setPager(pager);
    List<DistributorResponseDto> list = distributorService.queryDistributor(distributorRequestDto);
    Long count = distributorService.queryDistributorCount(distributorRequestDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", pager.getPageNum());
    map.put("total", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);
    map.put("records", count);
    map.put("rows", list);
    return map;
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(@RequestBody DistributorRequestDto distributorRequestDto) {
    try {
      Long id = distributorService.saveDistributor(distributorRequestDto);
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
  public ApiResult<Void> edit(@RequestBody DistributorRequestDto distributorRequestDto) {
    try {
      distributorService.updateDistributor(distributorRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/recommended", method = RequestMethod.POST)
  public ApiResult<Void> recommended(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.distributorService.recommendedDistributor(ids);
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
  @RequestMapping(value = "/unrecommended", method = RequestMethod.POST)
  public ApiResult<Void> unrecommended(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.distributorService.unrecommendedDistributor(ids);
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
  @RequestMapping(value = "/enabled", method = RequestMethod.POST)
  public ApiResult<Void> enabled(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.distributorService.enabledDistributor(ids);
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
      this.distributorService.disabledDistributor(ids);
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
      distributorService.deleteDistributor(ids);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}