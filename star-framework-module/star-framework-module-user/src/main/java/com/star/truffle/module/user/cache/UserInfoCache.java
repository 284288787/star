/**create by liuhua at 2018年7月14日 上午8:56:00**/
package com.star.truffle.module.user.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.star.truffle.module.user.dao.read.UserInfoReadDao;
import com.star.truffle.module.user.dao.write.UserInfoWriteDao;
import com.star.truffle.module.user.domain.UserInfo;
import com.star.truffle.module.user.dto.UserInfoDto;

@Service
public class UserInfoCache {

  @Autowired
  private UserInfoWriteDao userInfoWriteDao;
  @Autowired
  private UserInfoReadDao userInfoReadDao;
  
  @Caching(
    put = @CachePut(value = "module-user-info", key = "'user_info_userId_'+#userInfo.userId", condition = "#userInfo != null and #userInfo.userId != null"),
    evict = {
      @CacheEvict(value = "module-user-account", key = "'user_account_userId_'+#userInfo.userId", condition = "#userInfo != null and #userInfo.userId != null"),
      @CacheEvict(value = "module-user-account", key = "'user_account_account_'+#userInfo.account", condition = "#userInfo != null and #userInfo.account != null")
    }
  )
  public UserInfo saveUserInfo(UserInfoDto userInfo){
    userInfo.setLastModifyTime(new Date());
    this.userInfoWriteDao.saveUserInfo(userInfo);
    UserInfo info = userInfoWriteDao.getUserInfoByUserId(userInfo.getUserId());
    return info;
  }
  
  @Caching(
    put = @CachePut(value = "module-user-info", key = "'user_info_userId_'+#userInfo.userId", condition = "#userInfo != null and #userInfo.userId != null"),
    evict = {
      @CacheEvict(value = "module-user-account", key = "'user_account_userId_'+#userInfo.userId", condition = "#userInfo != null and #userInfo.userId != null"),
      @CacheEvict(value = "module-user-account", key = "'user_account_account_'+#userInfo.account", condition = "#userInfo != null and #userInfo.account != null")
    }
  )
  public UserInfo updateUserInfo(UserInfoDto userInfo){
    userInfo.setLastModifyTime(new Date());
    this.userInfoWriteDao.updateUserInfo(userInfo);
    UserInfo newUserInfo = userInfoWriteDao.getUserInfoByUserId(userInfo.getUserId());
    return newUserInfo;
  }
  
  @Cacheable(value = "module-user-info", key = "'user_info_userId_'+#userId", condition = "#result != null")
  public UserInfo getUserInfo(Long userId){
    UserInfo userInfo = userInfoReadDao.getUserInfoByUserId(userId);
    return userInfo;
  }
  
  public List<UserInfo> queryUserInfo(Map<String, Object> conditions){
    List<UserInfo> userAccounts = this.userInfoReadDao.queryUserInfo(conditions);
    return userAccounts;
  }
  
  public Integer queryUserInfoCount(Map<String, Object> conditions){
    Integer count = this.userInfoReadDao.queryUserInfoCount(conditions);
    return count;
  }

  public void deleteByUserId(Long userId) {
    userInfoWriteDao.deleteByUserId(userId);
  }
}
