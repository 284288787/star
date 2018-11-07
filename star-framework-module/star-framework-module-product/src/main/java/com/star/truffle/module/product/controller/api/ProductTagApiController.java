/**create by framework at 2018年11月07日 14:22:22**/
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
import com.star.truffle.module.product.domain.ProductTag;
import com.star.truffle.module.product.service.ProductTagService;
import com.star.truffle.module.product.dto.req.ProductTagRequestDto;
import com.star.truffle.module.product.dto.res.ProductTagResponseDto;

@Slf4j
@RestController
@RequestMapping("/api/productTag")
@Api(tags = "ProductTagApiController")
public class ProductTagApiController {

  @Autowired
  private ProductTagService productTagService;

  @RequestMapping(value = "/getProductTag", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取商品标签", notes = "根据主键获取商品标签", httpMethod = "POST", response = ProductTagResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "tagId", value = "标签ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<ProductTagResponseDto> getProductTag(Long tagId) {
    try {
      ProductTagResponseDto productTagResponseDto = productTagService.getProductTag(tagId);
      return ApiResult.success(productTagResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryProductTag", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取商品标签列表", notes = "根据条件获取商品标签列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "tagId", value = "标签ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "tagName", value = "标签名称", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<ProductTagResponseDto>> queryProductTag(@ApiIgnore ProductTagRequestDto productTagRequestDto) {
    try {
      List<ProductTagResponseDto> list = productTagService.queryProductTag(productTagRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryProductTagCount", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取商品标签数量", notes = "根据条件获取商品标签数量", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "tagId", value = "标签ID", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "tagName", value = "标签名称", dataType = "String", required = false, paramType = "query"),
  })
  public ApiResult<Long> queryProductTagCount(@ApiIgnore ProductTagRequestDto productTagRequestDto) {
    try {
      Long count = productTagService.queryProductTagCount(productTagRequestDto);
      return ApiResult.success(count);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/saveProductTag", method = RequestMethod.POST)
  @ApiOperation(value = "新增商品标签", notes = "新增商品标签", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "tagName", value = "标签名称", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Long> saveProductTag(@ApiIgnore ProductTag productTag) {
    try {
      Long id = productTagService.saveProductTag(productTag);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateProductTag", method = RequestMethod.POST)
  @ApiOperation(value = "编辑商品标签", notes = "编辑商品标签", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "tagName", value = "标签名称", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateProductTag(@ApiIgnore ProductTagRequestDto productTagRequestDto) {
    try {
      productTagService.updateProductTag(productTagRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteProductTag", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除商品标签", notes = "根据主键删除商品标签", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "tagId", value = "标签ID", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteProductTag(Long tagId) {
    try {
      productTagService.deleteProductTag(tagId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}