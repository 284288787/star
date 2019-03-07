/**create by framework at 2019年03月07日 10:03:37**/
package com.star.truffle.module.product.controller.api;

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
import com.star.truffle.module.product.domain.ProductCategory;
import com.star.truffle.module.product.service.ProductCategoryService;
import com.star.truffle.module.product.dto.req.ProductCategoryRequestDto;
import com.star.truffle.module.product.dto.res.ProductCategoryResponseDto;

@Slf4j
@RestController
@RequestMapping("/api/productCategory")
@Api(tags = "ProductCategoryApiController")
public class ProductCategoryApiController {

  @Autowired
  private ProductCategoryService productCategoryService;

  @RequestMapping(value = "/getProductCategory", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取商品分类", notes = "根据主键获取商品分类", httpMethod = "POST", response = ProductCategoryResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productCateId", value = "分类ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<ProductCategoryResponseDto> getProductCategory(Long productCateId) {
    try {
      ProductCategoryResponseDto productCategoryResponseDto = productCategoryService.getProductCategory(productCateId);
      return ApiResult.success(productCategoryResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryProductCategory", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取商品分类列表", notes = "根据条件获取商品分类列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productCateId", value = "分类ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "productCateName", value = "分类名称", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<ProductCategoryResponseDto>> queryProductCategory(@ApiIgnore ProductCategoryRequestDto productCategoryRequestDto) {
    try {
      List<ProductCategoryResponseDto> list = productCategoryService.queryProductCategory(productCategoryRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryProductCategoryCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取商品分类数量", notes = "根据条件获取商品分类数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productCateId", value = "分类ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "productCateName", value = "分类名称", dataType = "String", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryProductCategoryCount(@ApiIgnore ProductCategoryRequestDto productCategoryRequestDto) {
    try {
      Long count = productCategoryService.queryProductCategoryCount(productCategoryRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveProductCategory", method = RequestMethod.POST)
  @ApiOperation(value = "新增商品分类", notes = "新增商品分类", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productCateName", value = "分类名称", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveProductCategory(@ApiIgnore ProductCategory productCategory) {
    try {
      Long id = productCategoryService.saveProductCategory(productCategory);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateProductCategory", method = RequestMethod.POST)
  @ApiOperation(value = "编辑商品分类", notes = "编辑商品分类", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productCateName", value = "分类名称", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateProductCategory(@ApiIgnore ProductCategoryRequestDto productCategoryRequestDto) {
    try {
      productCategoryService.updateProductCategory(productCategoryRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteProductCategory", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除商品分类", notes = "根据主键删除商品分类", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "productCateId", value = "分类ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteProductCategory(Long productCateId) {
    try {
      productCategoryService.deleteProductCategory(productCateId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}