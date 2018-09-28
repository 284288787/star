/**create by liuhua at 2018年7月12日 上午9:52:50**/
package com.star.truffle.module.user.dao.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.user.domain.UserAccountType;
import com.star.truffle.module.user.dto.UserAccountDto;

public interface UserAccountReadDao {

  public UserAccountDto getUserAccountByUserId(Long userId);
  
  public UserAccountDto getUserAccountByAccount(String account);
  
  public List<UserAccountDto> queryUserAccount(Map<String, Object> conditions);
  
  public Integer queryUserAccountCount(Map<String, Object> conditions);
  
  public List<UserAccountType> queryUserAccountType(Long userId);

  public UserAccountType getUserAccountType(@Param("userId") Long userId, @Param("userType") String userType);
}
