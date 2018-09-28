/** create by liuhua at 2018年7月12日 上午10:02:42 **/
package com.star.truffle.module.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.star.truffle.common.constants.EnabledEnum;
import com.star.truffle.common.constants.LockedEnum;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.security.StarUserInfo;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.config.SpringContextConfig;
import com.star.truffle.module.user.cache.RoleCache;
import com.star.truffle.module.user.cache.UserAccountCache;
import com.star.truffle.module.user.constant.UserConstants.UserType;
import com.star.truffle.module.user.domain.UserAccount;
import com.star.truffle.module.user.domain.UserAccountType;
import com.star.truffle.module.user.dto.UserAccountDto;

@Service
public class UserAccountService {

  @Autowired
  private StarJson starJson;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserAccountCache userAccountCache;
  @Autowired
  private RoleCache roleCache;

  public StarUserInfo getLoginInfo() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (null == auth) {
      throw new StarServiceException(ApiCode.SECURITY_TOKEN_NOT_ACTIVE);
    }
    StarUserInfo starUserInfo = (StarUserInfo) auth.getPrincipal();
    return starUserInfo;
  }

  public Long saveUserAccount(UserAccountDto userAccount) {
    if (null == userAccount || !userAccount.checkSaveData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    UserAccountDto temp = getUserAccount(userAccount.getAccount());
    if (null != temp) {
      Long userId = temp.getUserId();
      UserAccountType userAccountType = userAccountCache.getUserAccountType(userId, UserType.userInfoService.name());
      if (null != userAccountType) {
        throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "帐号已经存在");
      }else {
        UserAccountType uat = new UserAccountType(userId, UserType.userInfoService.name(), UserType.userInfoService.typeName());
        userAccountCache.saveUserAccountType(userAccount, uat);
        return temp.getUserId();
      }
    } else {
      UserAccount ua = starJson.str2obj(starJson.obj2string(userAccount), UserAccount.class);
      ua.setPassword(passwordEncoder.encode(userAccount.getPassword()));
      userAccountCache.saveUserAccount(ua);
      Long userId = ua.getUserId();
      UserAccountType userAccountType = new UserAccountType(userId, userAccount.getUserType(), userAccount.getTypeName());
      userAccountCache.saveUserAccountType(userAccount, userAccountType);
      return userId;
    }
  }

  public Long updateUserAccount(UserAccount userAccount) {
    if (null == userAccount || null == userAccount.getUserId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    userAccountCache.updateUserAccount(userAccount);
    return userAccount.getUserId();
  }

  public void updateUserAccountPassword(UserAccount userAccount) {
    if (null == userAccount || null == userAccount.getUserId() || StringUtils.isBlank(userAccount.getPassword())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
    userAccountCache.updateUserAccount(userAccount);
  }

  public UserAccountDto getUserAccount(Long userId) {
    return userAccountCache.getUserAccount(userId);
  }

  public UserAccountDto getUserAccount(String account) {
    return userAccountCache.getUserAccount(account);
  }
  
  public List<UserAccountDto> queryUserAccount(UserAccountDto userAccountDto) {
    Map<String, Object> conditions = starJson.bean2Map(userAccountDto);
    List<UserAccountDto> userAccounts = this.userAccountCache.queryUserAccount(conditions);
    return userAccounts;
  }

  public Integer queryUserAccountCount(UserAccountDto userAccountDto) {
    Map<String, Object> conditions = starJson.bean2Map(userAccountDto);
    Integer count = this.userAccountCache.queryUserAccountCount(conditions);
    return count;
  }

  public void enabledUserAccount(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      UserAccount dto = new UserAccount();
      dto.setEnabled(EnabledEnum.enabled.val());
      dto.setEnabledTime(new Date());
      dto.setUserId(Long.parseLong(id));
      userAccountCache.updateUserAccount(dto);
    }
  }

  public void disabledUserAccount(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      UserAccount dto = new UserAccount();
      dto.setEnabled(EnabledEnum.disabled.val());
      dto.setEnabledTime(new Date());
      dto.setUserId(Long.parseLong(id));
      userAccountCache.updateUserAccount(dto);
    }
  }

  public void deleteUserAccount(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      Long userId = Long.parseLong(id);
      UserAccountDto userAccount = userAccountCache.getUserAccount(userId);
      if (null != userAccount) {
        boolean hasUserInfoService = false;
        List<UserAccountType> types = userAccountCache.queryUserAccountType(userId);
        if (null != types && ! types.isEmpty()) {
          for (UserAccountType userAccountType : types) {
            if (userAccountType.getUserType().equals(UserType.userInfoService.name())) {
              UserTypeIntf<?> userTypeIntf = SpringContextConfig.getBean(UserType.userInfoService.name(), UserTypeIntf.class);
              if (null != userTypeIntf) {
                userTypeIntf.deleteByUserId(userId);
                userAccountCache.deleteUserAccountType(userAccount.getAccount(), userId, UserType.userInfoService.name());
              }
              types.remove(userAccountType);
              hasUserInfoService = true;
              break;
            }
          }
        }
        if (! hasUserInfoService) {
          throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "该账号已经不是后台用户");
        }
        boolean deleteAccount = true;
        if (null != types && ! types.isEmpty()) {
          for (UserAccountType userAccountType : types) {
            UserTypeIntf<?> userTypeIntf = SpringContextConfig.getBean(userAccountType.getUserType(), UserTypeIntf.class);
            if (null != userTypeIntf) {
              Object object = userTypeIntf.getByUserId(userId);
              if (null != object) {
                deleteAccount = false;
              }
            }
          }
        }
        if (deleteAccount) {
          //删除 账号 用户信息 角色关系
          userAccountCache.deleteUserAccount(userId, userAccount.getAccount());
          roleCache.deleteUserRoleRelationByUserId(userId);
          if (! types.isEmpty()) {
            for (UserAccountType userAccountType : types) {
              userAccountCache.deleteUserAccountType(userAccount.getAccount(), userId, userAccountType.getUserType());
            }
          }
        }
      }
    }
  }
  
  public void lockedUserAccount(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      UserAccount dto = new UserAccount();
      dto.setNonLocked(LockedEnum.locked.val());
      dto.setLockedTime(new Date());
      dto.setUserId(Long.parseLong(id));
      userAccountCache.updateUserAccount(dto);
    }
  }

  public void unlockUserAccount(String ids) {
    if (StringUtils.isBlank(ids)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] pids = ids.split(",");
    for (String id : pids) {
      UserAccount dto = new UserAccount();
      dto.setNonLocked(LockedEnum.unlock.val());
      dto.setLockedTime(new Date());
      dto.setUserId(Long.parseLong(id));
      userAccountCache.updateUserAccount(dto);
    }
  }

  public void changeMinePass(String oldpass, String newpass) {
    if (StringUtils.isBlank(oldpass) || StringUtils.isBlank(newpass)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR); 
    }
    StarUserInfo userInfo = getLoginInfo();
    if (null != userInfo && null != userInfo.getUserId()) {
      UserAccountDto userAccount = userAccountCache.getUserAccount(userInfo.getUserId());
      if (passwordEncoder.matches(oldpass, userAccount.getPassword())) {
        UserAccount ua = new UserAccount();
        ua.setUserId(userAccount.getUserId());
        ua.setPassword(passwordEncoder.encode(newpass));
        ua.setLastModifyTime(new Date());
        this.userAccountCache.updateUserAccount(ua);
      } else {
        throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "密码错误"); 
      }
    }else{
      throw new StarServiceException(ApiCode.NO_LOGIN);
    }
  }

  public List<UserAccountType> queryUserAccountType(Long userId) {
    return this.userAccountCache.queryUserAccountType(userId);
  }
}
