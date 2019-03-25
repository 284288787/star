/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.weixin.controller.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.core.web.ApiCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import lombok.extern.slf4j.Slf4j;
import com.star.truffle.module.weixin.domain.Coupon;
import com.star.truffle.module.weixin.service.CouponService;
import com.star.truffle.module.weixin.dto.req.CouponRequestDto;
import com.star.truffle.module.weixin.dto.res.CouponResponseDto;

@Slf4j
@RestController
@RequestMapping("/api/coupon")
@Api(tags = "CouponApiController")
public class CouponApiController {

  @Autowired
  private CouponService couponService;

  @RequestMapping(value = "/getCoupon", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取卡券", notes = "根据主键获取卡券", httpMethod = "POST", response = CouponResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "couponId", value = "卡券ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<CouponResponseDto> getCoupon(Long couponId) {
    try {
      CouponResponseDto couponResponseDto = couponService.getCoupon(couponId);
      return ApiResult.success(couponResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryCoupon", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取卡券列表", notes = "根据条件获取卡券列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "couponId", value = "卡券ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "title", value = "卡券标题", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<CouponResponseDto>> queryCoupon(@ApiIgnore CouponRequestDto couponRequestDto) {
    try {
      List<CouponResponseDto> list = couponService.queryCoupon(couponRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryCouponCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取卡券数量", notes = "根据条件获取卡券数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "couponId", value = "卡券ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "title", value = "卡券标题", dataType = "String", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryCouponCount(@ApiIgnore CouponRequestDto couponRequestDto) {
    try {
      Long count = couponService.queryCouponCount(couponRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveCoupon", method = RequestMethod.POST)
  @ApiOperation(value = "新增卡券", notes = "新增卡券", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "title", value = "卡券标题", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "cardId", value = "微信卡券Id", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveCoupon(@ApiIgnore Coupon coupon) {
    try {
      Long id = couponService.saveCoupon(coupon);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateCoupon", method = RequestMethod.POST)
  @ApiOperation(value = "编辑卡券", notes = "编辑卡券", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "title", value = "卡券标题", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "cardId", value = "微信卡券Id", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateCoupon(@ApiIgnore CouponRequestDto couponRequestDto) {
    try {
      couponService.updateCoupon(couponRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteCoupon", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除卡券", notes = "根据主键删除卡券", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "couponId", value = "卡券ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteCoupon(Long couponId) {
    try {
      couponService.deleteCoupon(couponId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}