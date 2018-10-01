/**create by liuhua at 2018年10月1日 下午5:08:29**/
package com.star.truffle.module.user.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.user.domain.Area;
import com.star.truffle.module.user.service.AreaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "地区接口")
public class AreaApiController {

  @Autowired
  private AreaService areaService;
  
  @RequestMapping(value = "/getAreasByParentId", method = RequestMethod.POST)
  @ApiOperation(value = "获取下级地区列表", notes = "获取下级地区列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "parentId", value = "父地区ID，省传0", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<List<Area>> getAreasByParentId(Long parentId){
    try {
      List<Area> areas = this.areaService.getAreasByParentId(parentId);
      return ApiResult.success(areas);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/getAreaByAreaId", method = RequestMethod.POST)
  @ApiOperation(value = "获取地区信息", notes = "获取地区信息", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "areaId", value = "地区ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Area> getAreaByAreaId(Long areaId){
    try {
      Area area = this.areaService.getAreaByAreaId(areaId);
      return ApiResult.success(area);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}
