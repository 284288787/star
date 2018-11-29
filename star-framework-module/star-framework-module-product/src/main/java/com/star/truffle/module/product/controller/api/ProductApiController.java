/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.product.dto.req.ProductRequestDto;
import com.star.truffle.module.product.dto.res.ProductResponseDto;
import com.star.truffle.module.product.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/product")
@Api(tags = "供应")
public class ProductApiController {

  @Autowired
  private ProductService productService;

  @RequestMapping(value = "/getProductInfo", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取商品信息", notes = "根据主键获取商品信息", httpMethod = "POST", response = ProductResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productId", value = "商品ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<ProductResponseDto> getProduct(Long productId) {
    try {
      ProductResponseDto productResponseDto = productService.getProduct(productId);
      return ApiResult.success(productResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryProduct", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取商品信息列表", notes = "根据条件获取商品信息列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "title", value = "商品标题", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<ProductResponseDto>> queryProduct(@ApiIgnore ProductRequestDto productRequestDto) {
    try {
      if (null == productRequestDto) {
        productRequestDto = new ProductRequestDto();
      }
      productRequestDto.setStates("1,2,3");
      List<ProductResponseDto> list = productService.queryProduct(productRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/queryProductCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取商品信息数量", notes = "根据条件获取商品信息数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "title", value = "商品标题", dataType = "String", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryProductCount(@ApiIgnore ProductRequestDto productRequestDto) {
    try {
      if (null == productRequestDto) {
        productRequestDto = new ProductRequestDto();
      }
      productRequestDto.setStates("1,2,3");
      Long count = productService.queryProductCount(productRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/syncProductState", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取商品信息数量", notes = "根据条件获取商品信息数量", httpMethod = "POST", response = ApiResult.class)
  public ApiResult<Void> syncProductState() {
    try {
      productService.syncProductState();
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}