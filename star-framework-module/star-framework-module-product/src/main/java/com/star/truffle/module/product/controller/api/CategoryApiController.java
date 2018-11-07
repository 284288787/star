/**create by framework at 2018年11月07日 09:43:30**/
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
import com.star.truffle.module.product.domain.Category;
import com.star.truffle.module.product.service.CategoryService;
import com.star.truffle.module.product.dto.req.CategoryRequestDto;
import com.star.truffle.module.product.dto.res.CategoryResponseDto;

@Slf4j
@RestController
@RequestMapping("/api/category")
@Api(tags = "CategoryApiController")
public class CategoryApiController {

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value = "/getCategory", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取商品分类", notes = "根据主键获取商品分类", httpMethod = "POST", response = CategoryResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "cateId", value = "分类ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<CategoryResponseDto> getCategory(Long cateId) {
    try {
      CategoryResponseDto categoryResponseDto = categoryService.getCategory(cateId);
      return ApiResult.success(categoryResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryCategory", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取商品分类列表", notes = "根据条件获取商品分类列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "cateId", value = "分类ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "cateName", value = "分类名称", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<CategoryResponseDto>> queryCategory(@ApiIgnore CategoryRequestDto categoryRequestDto) {
    try {
      List<CategoryResponseDto> list = categoryService.queryCategory(categoryRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryCategoryCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取商品分类数量", notes = "根据条件获取商品分类数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "cateId", value = "分类ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "cateName", value = "分类名称", dataType = "String", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryCategoryCount(@ApiIgnore CategoryRequestDto categoryRequestDto) {
    try {
      Long count = categoryService.queryCategoryCount(categoryRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
  @ApiOperation(value = "新增商品分类", notes = "新增商品分类", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "cateName", value = "分类名称", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveCategory(@ApiIgnore Category category) {
    try {
      Long id = categoryService.saveCategory(category);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
  @ApiOperation(value = "编辑商品分类", notes = "编辑商品分类", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "cateName", value = "分类名称", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateCategory(@ApiIgnore CategoryRequestDto categoryRequestDto) {
    try {
      categoryService.updateCategory(categoryRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除商品分类", notes = "根据主键删除商品分类", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "cateId", value = "分类ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteCategory(Long cateId) {
    try {
      categoryService.deleteCategory(cateId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}