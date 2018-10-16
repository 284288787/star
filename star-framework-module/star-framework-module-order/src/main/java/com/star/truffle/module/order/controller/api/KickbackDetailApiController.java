/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.order.dto.req.KickbackDetailRequestDto;
import com.star.truffle.module.order.dto.res.KickbackDetailResponseDto;
import com.star.truffle.module.order.service.KickbackDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/kickbackDetail")
@Api(tags = "提成")
public class KickbackDetailApiController {

  @Autowired
  private KickbackDetailService kickbackDetailService;

  @RequestMapping(value = "/getKickbackDetail", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取提成明细", notes = "根据主键获取提成明细", httpMethod = "POST", response = KickbackDetailResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<KickbackDetailResponseDto> getKickbackDetail(Long id) {
    try {
      KickbackDetailResponseDto kickbackDetailResponseDto = kickbackDetailService.getKickbackDetail(id);
      return ApiResult.success(kickbackDetailResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryKickbackDetail", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取提成明细列表", notes = "根据条件获取提成明细列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "distributorId", value = "分销商", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "state", value = "订单状态", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<KickbackDetailResponseDto>> queryKickbackDetail(@ApiIgnore KickbackDetailRequestDto kickbackDetailRequestDto) {
    try {
      List<KickbackDetailResponseDto> list = kickbackDetailService.queryKickbackDetail(kickbackDetailRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryKickbackDetailCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取提成明细数量", notes = "根据条件获取提成明细数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "distributorId", value = "分销商", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "state", value = "订单状态", dataType = "int", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryKickbackDetailCount(@ApiIgnore KickbackDetailRequestDto kickbackDetailRequestDto) {
    try {
      Long count = kickbackDetailService.queryKickbackDetailCount(kickbackDetailRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/apply", method = RequestMethod.POST)
  @ApiOperation(value = "申请提现", notes = "申请提现", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "distributorId", value = "分销商id", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> apply(Long distributorId) {
    try {
      kickbackDetailService.apply(distributorId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateKickbackDetail", method = RequestMethod.POST)
  @ApiOperation(value = "编辑提成明细", notes = "编辑提成明细", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
  })
  public ApiResult<Void> updateKickbackDetail(@ApiIgnore KickbackDetailRequestDto kickbackDetailRequestDto) {
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

  @RequestMapping(value = "/deleteKickbackDetail", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除提成明细", notes = "根据主键删除提成明细", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteKickbackDetail(Long id) {
    try {
      kickbackDetailService.deleteKickbackDetail(id);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/getDistributorMoney", method = RequestMethod.POST)
  @ApiOperation(value = "获取可提现金额", notes = "获取可提现金额", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "distributorId", value = "分销商id", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Map<String, Object>> getDistributorMoney(Long distributorId) {
    try {
      Map<String, Object> map = kickbackDetailService.getDistributorMoney(distributorId);
      return ApiResult.success(map);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}