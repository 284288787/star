/**create by liuhua at 2018年7月12日 上午9:57:07**/
package com.star.truffle.module.user.dao.write;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.user.domain.UserAccount;
import com.star.truffle.module.user.domain.UserAccountType;
import com.star.truffle.module.user.dto.UserAccountDto;

public interface UserAccountWriteDao {

  public int saveUserAccount(UserAccount userAccount);
  
  public int saveUserAccountType(UserAccountType userAccountType);
  
  public int updateUserAccount(UserAccount userAccount);
  
  public UserAccountDto getUserAccountByUserId(Long userId);

  public int deleteUserAccount(Long userId);
  
  public int deleteUserAccountType(@Param("userId") Long userId, @Param("userType") String userType);
}
