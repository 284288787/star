/**create by framework at 2018年11月27日 10:12:39**/
package com.star.truffle.module.order.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.order.dto.req.DistributorTotalRequestDto;
import com.star.truffle.module.order.dto.res.DistributorTotalResponseDto;
import com.star.truffle.module.order.service.DistributorTotalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/distributorTotal")
@Api(tags = "DistributorTotalApiController")
public class DistributorTotalApiController {

  @Autowired
  private DistributorTotalService distributorTotalService;

  @RequestMapping(value = "/queryDistributorTotal", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取每天统计明细列表", notes = "根据条件获取每天统计明细列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "distributorId", value = "分销商", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<DistributorTotalResponseDto>> queryDistributorTotal(@ApiIgnore DistributorTotalRequestDto distributorTotalRequestDto) {
    try {
      List<DistributorTotalResponseDto> list = distributorTotalService.queryDistributorTotal(distributorTotalRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryDistributorTotalCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取每天统计明细数量", notes = "根据条件获取每天统计明细数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "distributorId", value = "分销商", dataType = "Long", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryDistributorTotalCount(@ApiIgnore DistributorTotalRequestDto distributorTotalRequestDto) {
    try {
      Long count = distributorTotalService.queryDistributorTotalCount(distributorTotalRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @ResponseBody
  @RequestMapping(value = "/updateDistributorTotal", method = RequestMethod.POST)
  public ApiResult<Void> updateDistributorTotal(Integer day) {
    try {
      distributorTotalService.updateDistributorTotal(day);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}