/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.order.dto.req.OrderAfterSaleRequestDto;
import com.star.truffle.module.order.dto.res.OrderAfterSaleResponseDto;
import com.star.truffle.module.order.service.OrderAfterSaleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/orderAfterSale")
@Api(tags = "售后")
public class OrderAfterSaleApiController {

  @Autowired
  private OrderAfterSaleService orderAfterSaleService;
  
  @RequestMapping(value = "/queryOrderAfterSale", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取订单售后列表", notes = "根据条件获取订单售后列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "distributorId", value = "分销商ID", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "orderId", value = "订单ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "afterCode", value = "售后单号", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "分销商手机号", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "state", value = "售后状态", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<OrderAfterSaleResponseDto>> queryOrderAfterSale(@ApiIgnore OrderAfterSaleRequestDto orderAfterSaleRequestDto) {
    try {
      List<OrderAfterSaleResponseDto> list = orderAfterSaleService.queryOrderAfterSale(orderAfterSaleRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryOrderAfterSaleCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取订单售后数量", notes = "根据条件获取订单售后数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "orderId", value = "订单ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "afterCode", value = "售后单号", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "分销商手机号", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "state", value = "售后状态", dataType = "int", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryOrderAfterSaleCount(@ApiIgnore OrderAfterSaleRequestDto orderAfterSaleRequestDto) {
    try {
      Long count = orderAfterSaleService.queryOrderAfterSaleCount(orderAfterSaleRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveOrderAfterSale", method = RequestMethod.POST)
  @ApiOperation(value = "新增订单售后", notes = "新增订单售后", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "orderId", value = "订单ID", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "distributorId", value = "分销商id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "remark", value = "申请售后说明", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveOrderAfterSale(@ApiIgnore OrderAfterSaleRequestDto orderAfterSale) {
    try {
      Long id = orderAfterSaleService.saveOrderAfterSale(orderAfterSale);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/cancelOrderAfterSale", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键取消订单售后", notes = "根据主键取消订单售后", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "售后id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "distributorId", value = "分销商id", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> cancelOrderAfterSale(Long id, Long distributorId) {
    try {
      orderAfterSaleService.cancelOrderAfterSale(id, distributorId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteOrderAfterSale", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除订单售后", notes = "根据主键删除订单售后", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteOrderAfterSale(Long id) {
    try {
      orderAfterSaleService.deleteOrderAfterSale(id);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}