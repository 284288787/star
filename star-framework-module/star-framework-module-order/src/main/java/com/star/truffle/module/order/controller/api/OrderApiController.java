/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.controller.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.OrderDetailResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.dto.res.OrderTotal;
import com.star.truffle.module.order.properties.OrderProperties;
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
  @Autowired
  private OrderProperties orderProperties;
  
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许为空, false:不允许为空
  }
  
  @RequestMapping(value = "/orderNum", method = RequestMethod.POST)
  @ApiOperation(value = "会员订单各个状态的数量", notes = "会员订单各个状态的数量", httpMethod = "POST", response = OrderResponseDto.class)
  @ApiImplicitParams({ 
    @ApiImplicitParam(name = "memberId", value = "会员id", dataType = "Long", required = true, paramType = "query"), 
  })
  public ApiResult<Map<String, Object>> orderNum(Long memberId) {
    try {
      Map<String, Object> map = orderService.orderNum(memberId);
      return ApiResult.success(map);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/seeUser", method = RequestMethod.POST)
  @ApiOperation(value = "分销商的所有会员", notes = "分销商的所有会员", httpMethod = "POST", response = OrderResponseDto.class)
  @ApiImplicitParams({ 
    @ApiImplicitParam(name = "distributorId", value = "分销商id", dataType = "Long", required = true, paramType = "query"), 
    @ApiImplicitParam(name = "keyword", value = "查询条件 可以是昵称和手机号", dataType = "String", required = false, paramType = "query"), 
    @ApiImplicitParam(name = "pageNum", value = "默认1", dataType = "int", required = false, paramType = "query"), 
    @ApiImplicitParam(name = "pageSize", value = "默认10", dataType = "int", required = false, paramType = "query")
  })
  public ApiResult<List<Map<String, Object>>> seeUser(Long distributorId, String keyword, Integer pageNum, Integer pageSize) {
    try {
      List<Map<String, Object>> list = orderService.seeUser(distributorId, keyword, pageNum, pageSize);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/ranking", method = RequestMethod.POST)
  @ApiOperation(value = "分销商排名", notes = "分销商排名", httpMethod = "POST", response = OrderResponseDto.class)
  @ApiImplicitParams({ 
    @ApiImplicitParam(name = "keyword", value = "查询条件 可以是昵称和手机号", dataType = "String", required = false, paramType = "query"), 
    @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "pageSize", value = "一页最大记录数 默认10", dataType = "int", required = false, paramType = "query") 
  })
  public ApiResult<List<OrderTotal>> ranking(Integer pageNum, Integer pageSize, String keyword) {
    try {
      List<OrderTotal> list = orderService.ranking(pageNum, pageSize, keyword);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/shopIndex", method = RequestMethod.POST)
  @ApiOperation(value = "分销商首页信息", notes = "分销商首页信息", httpMethod = "POST", response = OrderResponseDto.class)
  @ApiImplicitParams({ @ApiImplicitParam(name = "distributorId", value = "分销商id", dataType = "Long", required = true, paramType = "query") })
  public ApiResult<Map<String, Object>> shopIndex(Long distributorId) {
    try {
      Map<String, Object> map = orderService.shopIndex(distributorId);
      return ApiResult.success(map);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/orderNumRanking", method = RequestMethod.POST)
  @ApiOperation(value = "订单量排名", notes = "订单量排名", httpMethod = "POST", response = OrderResponseDto.class)
  @ApiImplicitParams({ 
    @ApiImplicitParam(name = "pageNum", value = "默认1", dataType = "int", required = false, paramType = "query"), 
    @ApiImplicitParam(name = "pageSize", value = "默认10", dataType = "int", required = false, paramType = "query") 
  })
  public ApiResult<List<OrderTotal>> orderNumRanking(Integer pageNum, Integer pageSize) {
    try {
      List<OrderTotal> ranking = orderService.orderNumRanking(pageNum, pageSize);
      return ApiResult.success(ranking);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/getOrder", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取订单", notes = "根据主键获取订单", httpMethod = "POST", response = OrderResponseDto.class)
  @ApiImplicitParams({ @ApiImplicitParam(name = "orderId", value = "主键", dataType = "Long", required = true, paramType = "query") })
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
  @ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "订单类型 1自主下单 2代客下单", dataType = "int", required = false, paramType = "query"), @ApiImplicitParam(name = "memberId", value = "用户ID", dataType = "Long", required = false, paramType = "query"), @ApiImplicitParam(name = "states", value = "订单状态 1待付款 2待提货 3已提货, 多个用逗号分隔", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"), @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"), })
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

  @RequestMapping(value = "/sumBrokerage", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取订单列表", notes = "根据条件获取订单列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "订单类型 1自主下单 2代客下单", dataType = "int", required = false, paramType = "query"), @ApiImplicitParam(name = "memberId", value = "用户ID", dataType = "Long", required = false, paramType = "query"), @ApiImplicitParam(name = "states", value = "订单状态 1待付款 2待提货 3已提货, 多个用逗号分隔", dataType = "int", required = false, paramType = "query"),
      @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"), @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"), })
  public ApiResult<Long> sumBrokerage(@ApiIgnore OrderRequestDto orderRequestDto) {
    try {
      Long sum = orderService.sumBrokerage(orderRequestDto);
      return ApiResult.success(sum);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveMemberOrder", method = RequestMethod.POST)
  @ApiOperation(value = "用户新增订单", notes = "用户新增订单", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "用户ID", dataType = "Long", required = true, paramType = "query"), @ApiImplicitParam(name = "distributorId", value = "分销商ID", dataType = "Long", required = true, paramType = "query"), @ApiImplicitParam(name = "deliveryType", value = "收货类型 1自提 2快递, 如果快递则需要传省市区、详细地址", dataType = "int", required = true, paramType = "query"), @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = false, paramType = "query"),
      @ApiImplicitParam(name = "mobile", value = "用户手机号", dataType = "String", required = false, paramType = "query"), @ApiImplicitParam(name = "deliveryId", value = "收货地址id", dataType = "String", required = false, paramType = "query"), @ApiImplicitParam(name = "details", value = "详细 [{productId:1,count:2},{productId:2,count:1}]", dataType = "json", required = true, paramType = "query"), })
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
    @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = true, paramType = "query"), @ApiImplicitParam(name = "mobile", value = "用户手机号", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "provinceName", value = "快递 省", dataType = "String", required = false, paramType = "query"), 
    @ApiImplicitParam(name = "cityName", value = "快递 市", dataType = "String", required = false, paramType = "query"), 
    @ApiImplicitParam(name = "areaName", value = "快递 区县", dataType = "String", required = false, paramType = "query"), 
    @ApiImplicitParam(name = "deliveryAddress", value = "快递 详细地址", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "details", value = "详细 [{productId:1,count:2},{productId:2,count:1}]", dataType = "json", required = true, paramType = "query"), })
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
  @ApiImplicitParams({ @ApiImplicitParam(name = "orderId", value = "主键", dataType = "Long", required = true, paramType = "query") })
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

  @RequestMapping(value = "/pickupOrder", method = RequestMethod.POST)
  @ApiOperation(value = "确认提货", notes = "确认提货", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({ @ApiImplicitParam(name = "orderId", value = "主键", dataType = "Long", required = true, paramType = "query") })
  public ApiResult<Void> pickupOrder(Long orderId) {
    try {
      orderService.pickupOrder(orderId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/buyRecord", method = RequestMethod.POST)
  @ApiOperation(value = "购买记录", notes = "购买记录", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({ @ApiImplicitParam(name = "productId", value = "供应id", dataType = "Long", required = true, paramType = "query") })
  public ApiResult<List<OrderDetailResponseDto>> buyRecord(Long productId, Integer pageNum, Integer pageSize) {
    try {
      List<OrderDetailResponseDto> list = orderService.buyRecord(productId, pageNum, pageSize);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/buyRecordTotal", method = RequestMethod.POST)
  @ApiOperation(value = "购买记录统计", notes = "购买记录统计", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({ @ApiImplicitParam(name = "productId", value = "供应id", dataType = "Long", required = true, paramType = "query") })
  public ApiResult<Map<String, Integer>> buyRecordTotal(Long productId) {
    try {
      Map<String, Integer> map = orderService.buyRecordTotal(productId);
      return ApiResult.success(map);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/getProperties", method = RequestMethod.POST)
  @ApiOperation(value = "订单相关的参数", notes = "订单相关的参数", httpMethod = "POST", response = ApiResult.class)
  public ApiResult<OrderProperties> getProperties() {
    try {
      return ApiResult.success(orderProperties);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}