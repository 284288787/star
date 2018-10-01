/**create by framework at 2018年09月18日 11:52:25**/
package com.star.truffle.module.member.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.member.dto.req.MemberRequestDto;
import com.star.truffle.module.member.dto.res.MemberResponseDto;
import com.star.truffle.module.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/member")
@Api(tags = "会员操作：登录，退出，获取信息")
public class MemberApiController {

  @Autowired
  private MemberService memberService;

  @RequestMapping(value = "/getLoginMemberByOpenId", method = RequestMethod.POST)
  @ApiOperation(value = "获取登录信息", notes = "根据openId获取登录信息", httpMethod = "POST", response = MemberResponseDto.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "openId", value = "微信openId", dataType = "String", required = true, paramType = "query")
  })
  public ApiResult<MemberResponseDto> getMemberByOpenId(String openId) {
    try {
      MemberResponseDto memberResponseDto = memberService.getMemberByOpenId(openId);
      return ApiResult.success(memberResponseDto);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
  /**
   * 1.前端本地没有用户信息，调出登录界面
   * 2.填写手机号，获取验证码，正确后获取微信用户信息 openid 昵称 头像，并弹出可修改的界面
   * 3.将这些参数全部传入接口，
   * 3.1. 验证码是否正确，不正确则异常
   * 3.2. 用openid查询，找不到则新增
   * 3.3. 将用户信息置为已登录，如果本身是已登录，则不动
   * 4.返回用户信息
   * @param member
   * @return
   */
  @RequestMapping(value = "/loginAndReg", method = RequestMethod.POST)
  @ApiOperation(value = "登录", notes = "登录<br>1.前端本地没有用户信息，则调出登录界面<br>2.输入手机号，获取验证码，正确后，获取微信用户信息<br>3.调用该接口，会返回登录后的用户信息", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "head", value = "头像链接", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "name", value = "姓名", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "openId", value = "openId", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "code", value = "验证码", paramType = "query", required = true, dataType = "String"),
    @ApiImplicitParam(name = "tag", value = "标识(1会员登录 2分销商登录)", paramType = "query", required = true, dataType = "int")
  })
  public ApiResult<MemberResponseDto> loginAndReg(@ApiIgnore MemberRequestDto member) {
    try {
      MemberResponseDto memberResponseDto = memberService.login(member);
      return ApiResult.success(memberResponseDto);
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
      memberService.logout(openId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/updateMember", method = RequestMethod.POST)
  @ApiOperation(value = "编辑消费者", notes = "编辑消费者", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "用户id", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "head", value = "头像链接", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "name", value = "姓名", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "openId", value = "openId", dataType = "String", required = true, paramType = "query"),
  })
  public ApiResult<Void> updateMember(@ApiIgnore MemberRequestDto memberRequestDto) {
    try {
      memberService.updateMember(memberRequestDto);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

  @RequestMapping(value = "/deleteMember", method = RequestMethod.POST)
  @ApiOperation(value = "根据主键删除消费者", notes = "根据主键删除消费者", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "memberId", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Void> deleteMember(Long memberId) {
    try {
      memberService.deleteMember(memberId);
      return ApiResult.success();
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }

}