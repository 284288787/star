/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.product.dto.req.DistributionRegionRequestDto;
import com.star.truffle.module.product.dto.res.DistributionRegionResponseDto;
import com.star.truffle.module.product.service.DistributionRegionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/distributionRegion")
@Api(hidden = true)
public class DistributionRegionApiController {

  @Autowired
  private DistributionRegionService distributionRegionService;

  @RequestMapping(value = "/getDistributionRegion", method = RequestMethod.POST)
  @ApiOperation(value = "根据分销拼音简称获取分销地区，及它下的分销商", notes = "根据分销拼音简称获取分销地区，及它下的分销商", httpMethod = "POST", response = DistributionRegionResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "py", value = "拼音简称", dataType = "String", required = true, paramType = "query")
  })
  public ApiResult<DistributionRegionResponseDto> getDistributionRegion(String py) {
    try {
      DistributionRegionResponseDto distributionRegionResponseDto = distributionRegionService.getDistributionRegion(py);
      return ApiResult.success(distributionRegionResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryDistributionRegion", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取分销区域列表", notes = "根据条件获取分销区域列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "regionId", value = "区域ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "name", value = "区域名称", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "provinceId", value = "省份", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "cityId", value = "城市", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "areaId", value = "区县", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "townId", value = "乡镇/街道", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "provinceName", value = "省", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "cityName", value = "市", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "areaName", value = "区县", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "townName", value = "乡镇", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "state", value = "区域状态", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<DistributionRegionResponseDto>> queryDistributionRegion(@ApiIgnore DistributionRegionRequestDto distributionRegionRequestDto) {
    try {
      List<DistributionRegionResponseDto> list = distributionRegionService.queryDistributionRegion(distributionRegionRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}