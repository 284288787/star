/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/order")
@Api(tags = "订单")
public class OrderApiController {

  @Autowired
  private OrderService orderService;
  
  @RequestMapping(value = "/getOrder", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取订单", notes = "根据主键获取订单", httpMethod = "POST", response = OrderResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "orderId", value = "主键", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<OrderResponseDto> getOrder(Long orderId) {
    try {
      OrderResponseDto orderResponseDto = orderService.getOrder(orderId);
      return ApiResult.success(orderResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryOrder", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取订单列表", notes = "根据条件获取订单列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "type", value = "订单类型 1自主下单 2代客下单", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "memberId", value = "用户ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "state", value = "订单状态 1待付款 2待提货 3已提货", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
  })
  public ApiResult<List<OrderResponseDto>> queryOrder(@ApiIgnore OrderRequestDto orderRequestDto) {
    try {
      List<OrderResponseDto> list = orderService.queryOrder(orderRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/saveMemberOrder", method = RequestMethod.POST)
  @ApiOperation(value = "用户新增订单", notes = "用户新增订单", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "用户ID", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "distributorId", value = "分销商ID", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "deliveryType", value = "收货类型 1自提 2快递, 如果快递则需要传省市区、详细地址", dataType = "int", required = true, paramType = "query"),
    @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "用户手机号", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "deliveryId", value = "收货地址id", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "details", value = "详细 [{productId:1,count:2},{productId:2,count:1}]", dataType = "json", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveMemberOrder(@ApiIgnore @RequestBody OrderRequestDto orderRequestDto) {
    try {
      Long id = orderService.saveMemberOrder(orderRequestDto);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveDistributorOrder", method = RequestMethod.POST)
  @ApiOperation(value = "代客下单", notes = "代客下单", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "distributorId", value = "分销商ID", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "deliveryType", value = "收货类型 1自提 2快递, 如果快递则需要传省市区、详细地址", dataType = "int", required = true, paramType = "query"),
    @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "用户手机号", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "provinceId", value = "快递 省", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "cityId", value = "快递 市", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "areaId", value = "快递 区县", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "deliveryAddress", value = "快递 详细地址", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "details", value = "详细 [{productId:1,count:2},{productId:2,count:1}]", dataType = "json", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveDistributorOrder(@ApiIgnore @RequestBody OrderRequestDto orderRequestDto) {
    try {
      Long id = orderService.saveDistributorOrder(orderRequestDto);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除订单", notes = "根据主键删除订单", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "orderId", value = "主键", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteOrder(Long orderId) {
    try {
      orderService.deleteOrder(orderId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}