/**create by framework at 2018年09月18日 10:59:57**/
package com.star.truffle.module.product.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.product.service.ProductSubscriptionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/productSubscription")
@Api(tags = "供应关注")
public class ProductSubscriptionApiController {

  @Autowired
  private ProductSubscriptionService productSubscriptionService;

  @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
  @ApiOperation(value = "关注供应", notes = "关注供应", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productId", value = "供应id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "openId", value = "用户的openId", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> subscribe(Long productId, String openId) {
    try {
      productSubscriptionService.subscribe(productId, openId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
  @ApiOperation(value = "取消关注供应", notes = "取消关注供应", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productId", value = "供应id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "openId", value = "用户的openId", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> unsubscribe(Long productId, String openId) {
    try {
      productSubscriptionService.unsubscribe(productId, openId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/isSubscription", method = RequestMethod.POST)
  @ApiOperation(value = "是否关注供应", notes = "取消关注供应", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productIds", value = "供应id，多个用逗号分隔", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "openId", value = "用户的openId", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Map<Long, Boolean>> isSubscription(String productIds, String openId) {
    try {
      Map<Long, Boolean> map = productSubscriptionService.isSubscription(productIds, openId);
      return ApiResult.success(map);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}