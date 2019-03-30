/**create by framework at 2019年03月30日 10:29:30**/
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
import com.star.truffle.module.weixin.domain.CouponRelation;
import com.star.truffle.module.weixin.service.CouponRelationService;
import com.star.truffle.module.weixin.dto.req.CouponRelationRequestDto;
import com.star.truffle.module.weixin.dto.res.CouponRelationResponseDto;

@Slf4j
@RestController
@RequestMapping("/api/couponRelation")
@Api(tags = "CouponRelationApiController")
public class CouponRelationApiController {

  @Autowired
  private CouponRelationService couponRelationService;

  @RequestMapping(value = "/getCouponRelation", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取卡券关联关系", notes = "根据主键获取卡券关联关系", httpMethod = "POST", response = CouponRelationResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<CouponRelationResponseDto> getCouponRelation(Long id) {
    try {
      CouponRelationResponseDto couponRelationResponseDto = couponRelationService.getCouponRelation(id);
      return ApiResult.success(couponRelationResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryCouponRelation", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取卡券关联关系列表", notes = "根据条件获取卡券关联关系列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "businessType", value = "业务类型", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "businessId", value = "业务ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "couponId", value = "卡券ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<CouponRelationResponseDto>> queryCouponRelation(@ApiIgnore CouponRelationRequestDto couponRelationRequestDto) {
    try {
      List<CouponRelationResponseDto> list = couponRelationService.queryCouponRelation(couponRelationRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryCouponRelationCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取卡券关联关系数量", notes = "根据条件获取卡券关联关系数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "businessType", value = "业务类型", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "businessId", value = "业务ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "couponId", value = "卡券ID", dataType = "Long", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryCouponRelationCount(@ApiIgnore CouponRelationRequestDto couponRelationRequestDto) {
    try {
      Long count = couponRelationService.queryCouponRelationCount(couponRelationRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveCouponRelation", method = RequestMethod.POST)
  @ApiOperation(value = "新增卡券关联关系", notes = "新增卡券关联关系", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "businessType", value = "业务类型", dataType = "int", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveCouponRelation(@ApiIgnore CouponRelation couponRelation) {
    try {
      Long id = couponRelationService.saveCouponRelation(couponRelation);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateCouponRelation", method = RequestMethod.POST)
  @ApiOperation(value = "编辑卡券关联关系", notes = "编辑卡券关联关系", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "businessType", value = "业务类型", dataType = "int", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateCouponRelation(@ApiIgnore CouponRelationRequestDto couponRelationRequestDto) {
    try {
      couponRelationService.updateCouponRelation(couponRelationRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteCouponRelation", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除卡券关联关系", notes = "根据主键删除卡券关联关系", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteCouponRelation(Long id) {
    try {
      couponRelationService.deleteCouponRelation(id);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}