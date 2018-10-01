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
import com.star.truffle.module.order.dto.req.ShoppingCartRequestDto;
import com.star.truffle.module.order.dto.res.ShoppingCartResponseDto;
import com.star.truffle.module.order.service.ShoppingCartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/shoppingCart")
@Api(tags = "购物车")
public class ShoppingCartApiController {

  @Autowired
  private ShoppingCartService shoppingCartService;

  @RequestMapping(value = "/queryShoppingCart", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取购物车列表", notes = "根据条件获取购物车列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "会员ID", dataType = "Long", required = true, paramType = "query"),
  })
  public ApiResult<List<ShoppingCartResponseDto>> queryShoppingCart(@ApiIgnore ShoppingCartRequestDto shoppingCartRequestDto) {
    try {
      List<ShoppingCartResponseDto> list = shoppingCartService.queryShoppingCart(shoppingCartRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/addShoppingCart", method = RequestMethod.POST)
  @ApiOperation(value = "添加到购物车", notes = "添加到购物车", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productId", value = "供应Id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "memberId", value = "用户Id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "num", value = "数量", dataType = "int", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveShoppingCart(@ApiIgnore ShoppingCartRequestDto shoppingCart) {
    try {
      Long id = shoppingCartService.saveShoppingCart(shoppingCart);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/updateShoppingCartNum", method = RequestMethod.POST)
  @ApiOperation(value = "编辑购物车数量", notes = "编辑购物车数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "用户Id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "cartId", value = "购物车Id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "num", value = "数量", dataType = "int", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateShoppingCart(@ApiIgnore ShoppingCartRequestDto shoppingCartRequestDto) {
    try {
      shoppingCartService.updateShoppingCart(shoppingCartRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateShoppingCartChecked", method = RequestMethod.POST)
  @ApiOperation(value = "选中/不选中", notes = "选中/不选中", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "用户Id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "cartId", value = "购物车Id", dataType = "Long", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateShoppingCartChecked(@ApiIgnore ShoppingCartRequestDto shoppingCartRequestDto) {
    try {
      shoppingCartService.updateShoppingCartChecked(shoppingCartRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteShoppingCart", method = RequestMethod.POST)
  @ApiOperation(value = "删除购物车", notes = "删除购物车", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "用户Id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "cartIds", value = "主键，多个逗号分隔", dataType = "String", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteShoppingCart(String cartIds) {
    try {
      shoppingCartService.deleteShoppingCart(cartIds);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}