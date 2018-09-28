/** create by liuhua at 2018年7月12日 上午10:47:38 **/
package com.star.truffle.module.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.user.domain.UserAccount;
import com.star.truffle.module.user.dto.UserAccountDto;
import com.star.truffle.module.user.service.UserAccountService;

@Controller
@RequestMapping("/userAccount")
public class UserAccountController {

  @Autowired
  private UserAccountService userAccountService;

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/userAccount/list";
  }
  
  @ResponseBody
  @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
  public Map<String, Object> list(UserAccountDto userAccountDto, Integer page, Integer rows, String sord, String sidx) {
    userAccountDto.setDeleted(0);
    userAccountDto.setPageNum(page);
    userAccountDto.setPageSize(rows);
    userAccountDto.setOrderBy(sidx);
    userAccountDto.setOrderType(OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);
    List<UserAccountDto> userAccounts = userAccountService.queryUserAccount(userAccountDto);
    Integer total = userAccountService.queryUserAccountCount(userAccountDto);

    Map<String, Object> map = new HashMap<>();
    map.put("page", page);
    map.put("total", total % userAccountDto.getPageSize() == 0 ? total / userAccountDto.getPageSize() : total / userAccountDto.getPageSize() + 1);
    map.put("records", total);
    map.put("rows", userAccounts);
    return map;
  }

  @RequestMapping(value = "/editBefore/{userId}", method = RequestMethod.GET)
  public String editBefore(@PathVariable Long userId, Model model) {
    UserAccountDto userAccount = this.userAccountService.getUserAccount(userId);
    model.addAttribute("userAccount", userAccount);
    return "mgr/userAccount/editUser";
  }

  @ResponseBody
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public ApiResult<Void> edit(UserAccount userAccount) {
    ApiResult<Void> apiResult = null;
    try {
      this.userAccountService.updateUserAccount(userAccount);
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

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ApiResult<Void> add(UserAccountDto userAccount) {
    ApiResult<Void> apiResult = null;
    try {
      this.userAccountService.saveUserAccount(userAccount);
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

  @ResponseBody
  @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
  public ApiResult<Void> changePassword(UserAccount userAccount) {
    ApiResult<Void> apiResult = null;
    try {
      this.userAccountService.updateUserAccountPassword(userAccount);
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

  @ResponseBody
  @RequestMapping(value = "/enabled", method = RequestMethod.POST)
  public ApiResult<Void> enabled(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.userAccountService.enabledUserAccount(ids);
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

  @ResponseBody
  @RequestMapping(value = "/disabled", method = RequestMethod.POST)
  public ApiResult<Void> disabled(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.userAccountService.disabledUserAccount(ids);
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

  @ResponseBody
  @RequestMapping(value = "/unlock", method = RequestMethod.POST)
  public ApiResult<Void> unlock(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.userAccountService.unlockUserAccount(ids);
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

  @ResponseBody
  @RequestMapping(value = "/locked", method = RequestMethod.POST)
  public ApiResult<Void> locked(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.userAccountService.lockedUserAccount(ids);
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

  @ResponseBody
  @RequestMapping(value = "/deleted", method = RequestMethod.POST)
  public ApiResult<Void> deleted(@RequestParam String ids) {
    ApiResult<Void> apiResult = null;
    try {
      this.userAccountService.deleteUserAccount(ids);
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
