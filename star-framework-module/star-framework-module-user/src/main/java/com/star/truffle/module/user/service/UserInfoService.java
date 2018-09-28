/**create by liuhua at 2018年7月12日 上午10:02:42**/
package com.star.truffle.module.user.service;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.user.cache.UserInfoCache;
import com.star.truffle.module.user.domain.UserInfo;
import com.star.truffle.module.user.dto.UserInfoDto;

@Service
public class UserInfoService implements UserTypeIntf<UserInfo> {

  @Autowired
  private StarJson starJson;
  @Autowired
  private UserInfoCache userInfoCache;
  
  public Long updateUserInfo(UserInfoDto userInfo){
    if (null == userInfo || null == userInfo.getUserId() || StringUtils.isBlank(userInfo.getName()) || StringUtils.isBlank(userInfo.getMobile())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    UserInfo temp = userInfoCache.getUserInfo(userInfo.getUserId());
    if (null == temp) {
      userInfoCache.saveUserInfo(userInfo);
    }else{
      userInfoCache.updateUserInfo(userInfo);
    }
    return userInfo.getUserId();
  }
  
  public UserInfo getByUserId(Long userId){
    return userInfoCache.getUserInfo(userId);
  }
  
  public List<UserInfo> queryUserInfos(UserInfoDto userInfoDto){
    Map<String, Object> conditions = starJson.bean2Map(userInfoDto);
    List<UserInfo> userInfos = this.userInfoCache.queryUserInfo(conditions);
    return userInfos;
  }
  
  public Integer queryUserInfoCount(UserInfoDto userInfoDto){
    Map<String, Object> conditions = starJson.bean2Map(userInfoDto);
    Integer count = this.userInfoCache.queryUserInfoCount(conditions);
    return count;
  }

  @Override
  public void deleteByUserId(Long userId) {
    this.userInfoCache.deleteByUserId(userId);
  }
}
