/**create by liuhua at 2018年9月19日 下午5:26:25**/
package com.star.truffle.module.alibaba.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.alibaba.service.SmsIdentityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/sms")
@Api(tags = "短信")
public class SmsApiController {

  @Autowired
  private SmsIdentityService smsIdentityService;
  
  @RequestMapping(value = "/send", method = RequestMethod.POST)
  @ApiOperation(value = "发送短信", notes = "发送短信", httpMethod = "POST", response = String.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query", required = true, dataType = "String"),
    @ApiImplicitParam(name = "tag", value = "标识(1会员登录 2分销商登录)", paramType = "query", required = true, dataType = "int")
  })
  public ApiResult<Void> send(String mobile, Integer tag) {
    try {
      smsIdentityService.send(mobile, tag);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/verify", method = RequestMethod.POST)
  @ApiOperation(value = "校验短信", notes = "校验短信", httpMethod = "POST", response = String.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query", required = true, dataType = "String"),
    @ApiImplicitParam(name = "code", value = "验证码", paramType = "query", required = true, dataType = "String"),
    @ApiImplicitParam(name = "tag", value = "标识(1会员登录 2分销商登录)", paramType = "query", required = true, dataType = "int")
  })
  public ApiResult<Void> verify(String mobile, Integer tag, String code) {
    try {
      smsIdentityService.verify(mobile, tag, code);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}
