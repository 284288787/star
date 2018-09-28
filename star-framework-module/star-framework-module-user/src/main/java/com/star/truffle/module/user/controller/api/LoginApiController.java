/** create by liuhua at 2018年7月10日 上午9:38:59 **/
package com.star.truffle.module.user.controller.api;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.star.truffle.core.security.StarUserInfo;
import com.star.truffle.core.util.MemoryCacheUtil;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.user.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "登录、登出、获取用户信息")
public class LoginApiController {
  
  @Autowired
  private UserAccountService userAccountService;
  
  @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
  @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", required = true, paramType = "query"), 
    @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true, paramType = "query")
  })
  public ApiResult<Void> login(String username, String password) {
    return ApiResult.success();
  }

  @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
  @ApiOperation(value = "登出", notes = "登出", httpMethod = "POST", response = ApiResult.class)
  public ApiResult<Void> logout(HttpServletRequest request) {
    String token = request.getHeader("token");
    MemoryCacheUtil.remove(token);
    return ApiResult.success();
  }

  @RequestMapping(value = "/getLoginInfo", method = RequestMethod.POST)
  @ApiOperation(value = "获取登录用户信息", notes = "获取登录用户信息", httpMethod = "POST", response = StarUserInfo.class)
  public ApiResult<StarUserInfo> getLoginInfo() {
    StarUserInfo starUserInfo = userAccountService.getLoginInfo();
    return ApiResult.success(starUserInfo);
  }
}
