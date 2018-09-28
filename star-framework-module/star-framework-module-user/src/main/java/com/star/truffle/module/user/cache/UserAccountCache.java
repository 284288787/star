/**create by liuhua at 2018年7月12日 上午10:07:25**/
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
import com.star.truffle.common.constants.DeletedEnum;
import com.star.truffle.common.constants.EnabledEnum;
import com.star.truffle.common.constants.LockedEnum;
import com.star.truffle.module.user.constant.UserConstants;
import com.star.truffle.module.user.dao.read.UserAccountReadDao;
import com.star.truffle.module.user.dao.write.UserAccountWriteDao;
import com.star.truffle.module.user.domain.UserAccount;
import com.star.truffle.module.user.domain.UserAccountType;
import com.star.truffle.module.user.dto.UserAccountDto;

@Service
public class UserAccountCache {

  @Autowired
  private UserAccountWriteDao userAccountWriteDao;
  @Autowired
  private UserAccountReadDao userAccountReadDao;
  
  @Caching(
    put = {
      @CachePut(value = "module-user-account", key = "'user_account_userId_'+#result.userId", condition = "#result != null and #result.userId != null"),
      @CachePut(value = "module-user-account", key = "'user_account_account_'+#result.account", condition = "#result != null and #result.account != null")
    }
  )
  public UserAccountDto saveUserAccount(UserAccount userAccount){
    userAccount.setUserId(UserConstants.idWorker.nextId());
    userAccount.setCreateTime(new Date());
    userAccount.setDeleted(DeletedEnum.notdelete.val());
    userAccount.setEnabled(EnabledEnum.enabled.val());
    userAccount.setNonExpired(1);
    userAccount.setNonLocked(LockedEnum.unlock.val());
    this.userAccountWriteDao.saveUserAccount(userAccount);
    UserAccountDto newUserAccount = this.userAccountWriteDao.getUserAccountByUserId(userAccount.getUserId());
    return newUserAccount;
  }
  
  @Caching(
    put = {
      @CachePut(value = "module-user-account", key = "'user_account_userId_'+#result.userId", condition = "#result != null and #result.userId != null"),
      @CachePut(value = "module-user-account", key = "'user_account_account_'+#result.account", condition = "#result != null and #result.account != null")
    }
  )
  public UserAccountDto updateUserAccount(UserAccount userAccount){
    this.userAccountWriteDao.updateUserAccount(userAccount);
    UserAccountDto newUserAccount = this.userAccountWriteDao.getUserAccountByUserId(userAccount.getUserId());
    return newUserAccount;
  }
  
  @Cacheable(value = "module-user-account", key = "'user_account_userId_'+#userId", condition = "#userId != null")
  public UserAccountDto getUserAccount(Long userId){
    UserAccountDto userAccount = this.userAccountReadDao.getUserAccountByUserId(userId);
    return userAccount;
  }
  
  @Cacheable(value = "module-user-account", key = "'user_account_account_'+#account", condition = "#account != null")
  public UserAccountDto getUserAccount(String account){
    UserAccountDto userAccount = this.userAccountReadDao.getUserAccountByAccount(account);
    return userAccount;
  }
  
  public List<UserAccountDto> queryUserAccount(Map<String, Object> conditions){
    List<UserAccountDto> userAccounts = this.userAccountReadDao.queryUserAccount(conditions);
    return userAccounts;
  }
  
  public Integer queryUserAccountCount(Map<String, Object> conditions){
    Integer count = this.userAccountReadDao.queryUserAccountCount(conditions);
    return count;
  }

  @Caching(
    evict = {
      @CacheEvict(value = "module-user-account", key = "'user_account_userId_'+#userId", condition = "#userId != null"),
      @CacheEvict(value = "module-user-account", key = "'user_account_account_'+#account", condition = "#account != null")
    }
  )
  public void deleteUserAccount(Long userId, String account) {
    this.userAccountWriteDao.deleteUserAccount(userId);
  }

  @Caching(
    evict = {
      @CacheEvict(value = "module-user-account", key = "'user_account_userId_'+#userAccount.userId", condition = "#userAccount.userId != null"),
      @CacheEvict(value = "module-user-account", key = "'user_account_account_'+#userAccount.account", condition = "#userAccount.account != null")
    }
  )
  public void saveUserAccountType(UserAccountDto userAccount, UserAccountType userAccountType) {
    this.userAccountWriteDao.saveUserAccountType(userAccountType);
  }
  
  public List<UserAccountType> queryUserAccountType(Long userId) {
    return userAccountReadDao.queryUserAccountType(userId);
  }

  public UserAccountType getUserAccountType(Long userId, String userType) {
    return userAccountReadDao.getUserAccountType(userId, userType);
  }

  @Caching(
    evict = {
      @CacheEvict(value = "module-user-account", key = "'user_account_userId_'+#userId", condition = "#userId != null"),
      @CacheEvict(value = "module-user-account", key = "'user_account_account_'+#account", condition = "#account != null")
    }
  )
  public void deleteUserAccountType(String account, Long userId, String userType) {
    userAccountWriteDao.deleteUserAccountType(userId, userType);
  }
}
