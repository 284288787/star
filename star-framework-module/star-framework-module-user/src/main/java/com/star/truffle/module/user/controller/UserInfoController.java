/**create by liuhua at 2018年7月12日 上午10:47:38**/
package com.star.truffle.module.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.user.domain.UserInfo;
import com.star.truffle.module.user.dto.UserInfoDto;
import com.star.truffle.module.user.service.UserInfoService;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

  @Autowired
  private UserInfoService userInfoService;
  
  @RequestMapping(value = "/editBefore/{userId}", method = RequestMethod.GET)
  public String userInfo(@PathVariable Long userId, Model model){
    UserInfo userInfo = userInfoService.getByUserId(userId);
    if (null == userInfo) {
      userInfo = new UserInfo();
      userInfo.setUserId(userId);
    }
    model.addAttribute("userInfo", userInfo);
    return "mgr/userAccount/editUserInfo";
  }
  
  @ResponseBody
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public ApiResult<Void> edit(UserInfoDto userInfo){
    ApiResult<Void> apiResult = null;
      try {
          this.userInfoService.updateUserInfo(userInfo);
          apiResult = ApiResult.success();
      } catch (StarServiceException e) {
          e.printStackTrace();
          apiResult = ApiResult.fail(e.getCode(), e.getMessage());
      } catch (Exception e) {
          e.printStackTrace();
          apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
      }
      return apiResult;
  }
}
