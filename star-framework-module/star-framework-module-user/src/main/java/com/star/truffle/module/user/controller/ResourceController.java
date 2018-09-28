/**create by liuhua at 2018年7月22日 下午6:54:50**/
package com.star.truffle.module.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.star.truffle.common.constants.EnabledEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.user.domain.Uri;
import com.star.truffle.module.user.dto.ResourceDto;
import com.star.truffle.module.user.dto.UriDto;
import com.star.truffle.module.user.service.ResourceService;

@Controller
@RequestMapping("/resource")
public class ResourceController {

  @Autowired
  private ResourceService resourceService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/resource/list";
  }
  
  @ResponseBody
  @RequestMapping(value = "/resources", method = {RequestMethod.POST, RequestMethod.GET})
  public List<ResourceDto> resources(ResourceDto resource) {
    resource.setEnabled(EnabledEnum.enabled.val());
    if (null == resource.getParentId()) {
      resource.setParentId(0L);
    }
    List<ResourceDto> resources = resourceService.queryResource(resource);
    return resources;
  }
  
  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Long> add(ResourceDto resource) {
    ApiResult<Long> apiResult = null;
    try {
      Long id = this.resourceService.saveResource(resource);
      apiResult = ApiResult.success(id);
    } catch (StarServiceException e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
    return apiResult;
  }
  
  @RequestMapping(value = "/editBefore/{sourceId}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long sourceId, Model model) {
    ResourceDto resource = this.resourceService.getResource(sourceId);
    model.addAttribute("resource", resource);
    return "mgr/resource/editResource";
  }
  
  @ResponseBody
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public ApiResult<Long> edit(ResourceDto resource) {
    ApiResult<Long> apiResult = null;
    try {
      Long id = this.resourceService.editResource(resource);
      apiResult = ApiResult.success(id);
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
  @RequestMapping(value = "/editUri", method = RequestMethod.POST)
  public ApiResult<Long> editUri(Uri uri) {
    ApiResult<Long> apiResult = null;
    try {
      this.resourceService.editUri(uri);
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
  @RequestMapping(value = "/delete/{sourceId}", method = RequestMethod.POST)
  public ApiResult<Void> delete(@PathVariable Long sourceId) {
    ApiResult<Void> apiResult = null;
    try {
      this.resourceService.deleteResource(sourceId);
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
  @RequestMapping(value = "/uris", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> uris(UriDto uri, Integer page, Integer rows, String sord, String sidx) {
    uri.setPageNum(page);
    uri.setPageSize(rows);
    uri.setOrderBy(StringUtils.isBlank(sidx) ? "uri" : sidx);
    uri.setOrderType(OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    List<UriDto> uriDtos = resourceService.queryUri(uri);
    Integer total = resourceService.queryUriCount(uri);
    
    Map<String, Object> map = new HashMap<>();
    map.put("page", page);
    map.put("total", total % uri.getPageSize() == 0 ? total / uri.getPageSize() : total / uri.getPageSize() + 1);
    map.put("records", total);
    map.put("rows", uriDtos);
    return map;
  }
  
  @ResponseBody
  @RequestMapping(value = "/saveResourceUri", method = RequestMethod.POST)
  public ApiResult<Void> saveResourceUri(Uri uri, boolean status) {
    ApiResult<Void> apiResult = null;
    try {
      this.resourceService.saveResourceUri(uri, status);
      apiResult = ApiResult.success();
    } catch (StarServiceException e) {
      apiResult = ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail();
    }
    return apiResult;
  }
}
