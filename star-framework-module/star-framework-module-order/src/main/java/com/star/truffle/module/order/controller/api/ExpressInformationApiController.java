/**create by framework at 2019年02月19日 14:19:41**/
package com.star.truffle.module.order.controller.api;

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
import com.star.truffle.module.order.domain.ExpressInformation;
import com.star.truffle.module.order.service.ExpressInformationService;
import com.star.truffle.module.order.dto.req.ExpressInformationRequestDto;
import com.star.truffle.module.order.dto.res.ExpressInformationResponseDto;

@Slf4j
@RestController
@RequestMapping("/api/expressInformation")
@Api(tags = "ExpressInformationApiController")
public class ExpressInformationApiController {

  @Autowired
  private ExpressInformationService expressInformationService;

  @RequestMapping(value = "/getExpressInformation", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键获取物流信息", notes = "根据主键获取物流信息", httpMethod = "POST", response = ExpressInformationResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<ExpressInformationResponseDto> getExpressInformation(Long id) {
    try {
      ExpressInformationResponseDto expressInformationResponseDto = expressInformationService.getExpressInformation(id);
      return ApiResult.success(expressInformationResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/queryExpressInformation", method = RequestMethod.POST)
  @ApiOperation(value = "根据条件获取物流信息列表", notes = "根据条件获取物流信息列表", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "主键", dataType = "Long", required = false, paramType = "query"),
    @ApiImplicitParam(name = "code", value = "编号", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "sender", value = "寄件人", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "senderMobile", value = "寄件人手机号", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "senderTel", value = "寄件人座机号", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "senderAddress", value = "寄件人地址", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "receiver", value = "收件人", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "receiverMobile", value = "收件人手机号", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "receiverTel", value = "收件人座机号", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "receiverAddress", value = "收件人地址", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "goodsInfo", value = "物品信息", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "expressCompany", value = "快递公司", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "pointName", value = "快递网点", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "trackingNumber", value = "快递单号", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageNum", value = "页码，页码如果没有则查询满足条件的所有记录", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.pageSize", value = "一页最大记录数，当页码有值时，该值没有指定则默认为10", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderBy", value = "排序字段，必须和数据库的字段一致", dataType = "String", required = false, paramType = "query"),
    @ApiImplicitParam(name = "page.orderType", value = "升序/降序", dataType = "String", required = false, paramType = "query", allowableValues = "asc,desc")
  })
  public ApiResult<List<ExpressInformationResponseDto>> queryExpressInformation(@ApiIgnore ExpressInformationRequestDto expressInformationRequestDto) {
    try {
      List<ExpressInformationResponseDto> list = expressInformationService.queryExpressInformation(expressInformationRequestDto);
      return ApiResult.success(list);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}