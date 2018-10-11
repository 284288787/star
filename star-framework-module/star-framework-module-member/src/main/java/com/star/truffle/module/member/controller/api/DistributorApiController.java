/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.member.dto.req.DistributorRequestDto;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;
import com.star.truffle.module.member.service.DistributorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/distributor")
@Api(tags = "分销商操作：登录，退出，获取信息")
public class DistributorApiController {

  @Autowired
  private DistributorService distributorService;

  /**
   * 1.前端本地没有用户信息，调出登录界面
   * 2.填写手机号，获取验证码，正确后获取微信用户信息 openid 和 头像
   * 3.将这些参数全部传入接口，
   * 3.1. 验证码是否正确，不正确则异常
   * 3.2. 先用openid查询，查不到则用手机号查询，最终找不到用户信息，则异常
   * 3.3. 如果用户头像信息为空，则替换为获取微信得到的头像
   * 3.4. 如果用户openid为空，则替换
   * 3.5. 置为已登录
   * 4.返回用户信息
   * @param distributorRequestDto
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ApiOperation(value = "登录", notes = "登录<br>1.前端本地没有用户信息，则调出登录界面<br>2.输入手机号，获取验证码，正确后，获取微信用户信息<br>3.调用该接口，会返回登录后的用户信息", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "head", value = "头像链接", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "name", value = "昵称", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "openId", value = "openId", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "code", value = "验证码", paramType = "query", required = true, dataType = "String"),
    @ApiImplicitParam(name = "tag", value = "标识(2分销商登录)", paramType = "query", required = true, dataType = "int")
  })
  public ApiResult<DistributorResponseDto> login(@ApiIgnore DistributorRequestDto distributorRequestDto) {
    try {
      DistributorResponseDto distributorResponseDto = distributorService.login(distributorRequestDto);
      return ApiResult.success(distributorResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ApiOperation(value = "退出登录", notes = "退出登录", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "openId", value = "微信openId", dataType = "String", required = true, paramType = "query")
  })
  public ApiResult<Void> logout(String openId) {
    try {
      distributorService.logout(openId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  @RequestMapping(value = "/getDistributorByOpenId", method = RequestMethod.POST)
  @ApiOperation(value = "根据openId获取分销商", notes = "根据openId获取分销商", httpMethod = "POST", response = DistributorResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "openId", value = "openId", dataType = "String", required = true, paramType = "query")
  })
  public ApiResult<DistributorResponseDto> getDistributorByOpenId(String openId) {
    try {
      DistributorResponseDto distributorResponseDto = distributorService.getDistributorByOpenId(openId);
      return ApiResult.success(distributorResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateDistributorHead", method = RequestMethod.POST)
  @ApiOperation(value = "编辑分销商的头像", notes = "编辑分销商的头像", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "head", value = "头像", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "distributorId", value = "主键", dataType = "Long", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateDistributorHead(@ApiIgnore DistributorRequestDto distributorRequestDto) {
    try {
      distributorService.updateDistributorHead(distributorRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}