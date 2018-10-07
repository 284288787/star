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
import com.star.truffle.module.order.dto.req.DeliveryAddressRequestDto;
import com.star.truffle.module.order.dto.res.DeliveryAddressResponseDto;
import com.star.truffle.module.order.service.DeliveryAddressService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/deliveryAddress")
@Api(tags = "收货地址")
public class DeliveryAddressApiController {

  @Autowired
  private DeliveryAddressService deliveryAddressService;

  @RequestMapping(value = "/getDeliveryAddress", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取收货地址", notes = "根据主键获取收货地址", httpMethod = "POST", response = DeliveryAddressResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<DeliveryAddressResponseDto> getDeliveryAddress(Long id) {
    try {
      DeliveryAddressResponseDto deliveryAddressResponseDto = deliveryAddressService.getDeliveryAddress(id);
      return ApiResult.success(deliveryAddressResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryDeliveryAddress", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取收货地址列表", notes = "根据条件获取收货地址列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "会员ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<DeliveryAddressResponseDto>> queryDeliveryAddress(@ApiIgnore DeliveryAddressRequestDto deliveryAddressRequestDto) {
    try {
      List<DeliveryAddressResponseDto> list = deliveryAddressService.queryDeliveryAddress(deliveryAddressRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveDeliveryAddress", method = RequestMethod.POST)
  @ApiOperation(value = "新增收货地址", notes = "新增收货地址", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "会员ID", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "name", value = "收件人", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "provinceName", value = "省", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "cityName", value = "市", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "areaName", value = "区县", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "address", value = "详细地址", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "def", value = "是否默认收货地址 1是 0否", dataType = "int", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveDeliveryAddress(@ApiIgnore DeliveryAddressRequestDto deliveryAddress) {
    try {
      Long id = deliveryAddressService.saveDeliveryAddress(deliveryAddress);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateDeliveryAddress", method = RequestMethod.POST)
  @ApiOperation(value = "编辑收货地址", notes = "编辑收货地址", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "收货地址id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "memberId", value = "会员ID", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "name", value = "收件人", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "provinceName", value = "省", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "cityName", value = "市", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "areaName", value = "区县", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "address", value = "详细地址", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "def", value = "是否默认收货地址 1是 0否", dataType = "int", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateDeliveryAddress(@ApiIgnore DeliveryAddressRequestDto deliveryAddressRequestDto) {
    try {
      deliveryAddressService.updateDeliveryAddress(deliveryAddressRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/deleteDeliveryAddress", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除收货地址", notes = "根据主键删除收货地址", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "收货地址id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "memberId", value = "会员ID", dataType = "Long", required = true, paramType = "query"),
  })
  public ApiResult<Void> deleteDeliveryAddress(Long id, Long memberId) {
    try {
      deliveryAddressService.deleteDeliveryAddress(id, memberId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/setDef", method = RequestMethod.POST)
  @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "收货地址id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "memberId", value = "会员ID", dataType = "Long", required = true, paramType = "query"),
  })
  public ApiResult<Void> setDef(Long id, Long memberId) {
    try {
      deliveryAddressService.setDef(id, memberId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}