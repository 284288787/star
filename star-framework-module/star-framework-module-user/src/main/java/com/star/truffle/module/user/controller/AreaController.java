/**create by liuhua at 2018年8月20日 下午2:31:19**/
package com.star.truffle.module.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.user.domain.Area;
import com.star.truffle.module.user.dto.AreaDto;
import com.star.truffle.module.user.service.AreaService;

@Controller
@RequestMapping("/area")
public class AreaController {

  @Autowired
  private AreaService areaService;
  
  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/area/list";
  }
  
  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(AreaDto areaDto, Integer page, Integer rows, String sord, String sidx) {
    areaDto.setPageNum(page);
    areaDto.setPageSize(rows);
    areaDto.setOrderBy(sidx);
    areaDto.setOrderType(OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    List<Area> userAccounts = areaService.queryArea(areaDto);
    Long total = areaService.queryAreaCount(areaDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", page);
    map.put("total", total % areaDto.getPageSize() == 0 ? total / areaDto.getPageSize() : total / areaDto.getPageSize() + 1);
    map.put("records", total);
    map.put("rows", userAccounts);
    return map;
  }
  
  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Void> add(Area area) {
    ApiResult<Void> apiResult = null;
    try {
      this.areaService.saveArea(area);
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
  
  @RequestMapping(value = "/choose", method = RequestMethod.GET)
  public String choose(Model model){
    List<Area> provinces = areaService.allProvinceAreas();
    model.addAttribute("provinces", provinces);
    return "mgr/area/choose";
  }
  
  @RequestMapping(value = "/chooseArea", method = RequestMethod.GET)
  public String chooseArea(Model model){
    model.addAttribute("provinces", this.areaService.getAreasByParentId(0L));
    return "mgr/area/chooseArea";
  }
  
  @ResponseBody
  @RequestMapping(value = "/getAreasByParentId", method = RequestMethod.POST)
  public List<Area> getAreasByParentId(Long parentId){
    if (null == parentId) {
      parentId = 0L;
    }
    List<Area> areas = this.areaService.getAreasByParentId(parentId);
    for (Area area : areas) {
      area.setChildren(this.areaService.getAreasByParentId(area.getAreaId()));
    }
    return areas;
  }
  
  @ResponseBody
  @RequestMapping(value = "/enabled", method = RequestMethod.POST)
  public ApiResult<Void> enabled(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.areaService.enabledArea(ids);
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
      this.areaService.disabledArea(ids);
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
  public ApiResult<Void> deleted(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.areaService.deleteArea(ids);
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
}
