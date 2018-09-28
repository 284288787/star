/**create by liuhua at 2018年7月12日 上午9:47:11**/
package com.star.truffle.module.user.domain;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import lombok.Data;

@Data
public class UserInfo {

  private Long userId;            //用户Id 来自账号表
  private String name;            //姓名
  private String mobile;          //电话
  private Date lastModifyTime;    //最后修改时间
  
  public boolean checkSaveData() {
    if (null == userId || StringUtils.isBlank(name) || StringUtils.isBlank(mobile) || null == lastModifyTime) {
      return false;
    }
    return true;
  }
  
}
