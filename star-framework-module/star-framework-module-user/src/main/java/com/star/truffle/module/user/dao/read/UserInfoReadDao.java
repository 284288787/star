/** create by liuhua at 2018年7月13日 下午5:51:50 **/
package com.star.truffle.module.user.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.user.domain.UserInfo;

public interface UserInfoReadDao {

  public UserInfo getUserInfoByUserId(Long userId);

  public List<UserInfo> queryUserInfo(Map<String, Object> conditions);

  public Integer queryUserInfoCount(Map<String, Object> conditions);
}
