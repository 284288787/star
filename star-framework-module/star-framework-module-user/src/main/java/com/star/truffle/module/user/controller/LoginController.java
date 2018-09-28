/** create by liuhua at 2018年7月4日 下午5:48:18 **/
package com.star.truffle.module.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.star.truffle.core.security.StarUserInfo;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.user.properties.BuiProperties;
import com.star.truffle.module.user.service.UserAccountService;

@Controller
public class LoginController {

  @Autowired
  private UserAccountService userAccountService;

  @Autowired
  private BuiProperties buiProperties;
  
  @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
  public String bui(Model model) {
    model.addAttribute("buiProperties", buiProperties);
    StarUserInfo starUserInfo = userAccountService.getLoginInfo();
    model.addAttribute("starUserInfo", starUserInfo);
    return "main";
  }

  @RequestMapping(value = "/accessDenied", method = {RequestMethod.GET, RequestMethod.POST})
  public String accessDenied() {
    return "accessDenied";
  }
  
  @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
  public String login(Model model) {
    model.addAttribute("buiProperties", buiProperties);
    return "login";
  }

  @ResponseBody
  @RequestMapping(value = "/changeMinePass", method = RequestMethod.POST)
  public ApiResult<Void> changeMinePass(String oldpass, String newpass) {
    userAccountService.changeMinePass(oldpass, newpass);
    return ApiResult.success();
  }

  @ResponseBody
  @RequestMapping(value = "/getLoginInfo", method = RequestMethod.POST)
  public ApiResult<StarUserInfo> getLoginInfo() {
    StarUserInfo starUserInfo = userAccountService.getLoginInfo();
    return ApiResult.success(starUserInfo);
  }
}
