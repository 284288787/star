/** create by liuhua at 2018年7月13日 下午5:56:06 **/
package com.star.truffle.module.user.dao.write;

import com.star.truffle.module.user.domain.UserInfo;
import com.star.truffle.module.user.dto.UserInfoDto;

public interface UserInfoWriteDao {

  public int saveUserInfo(UserInfoDto userInfo);

  public int updateUserInfo(UserInfoDto userInfo);

  public UserInfo getUserInfoByUserId(Long userId);

  public int deleteByUserId(Long userId);
}
