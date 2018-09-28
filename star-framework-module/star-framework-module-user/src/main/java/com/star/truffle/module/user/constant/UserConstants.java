/**create by liuhua at 2018年7月14日 上午9:16:28**/
package com.star.truffle.module.user.constant;

import com.star.truffle.core.util.IdWorker;
import com.star.truffle.core.util.IdWorkerFactory;

public class UserConstants {

  public static IdWorker idWorker = IdWorkerFactory.getIdWorker(0, 1);
  
  public enum UserType {
    userInfoService("后台用户"); //后台用户
    
    private String typeName;
    
    private UserType(String typeName) {
      this.typeName = typeName;
    }
    
    public String typeName() {
      return this.typeName;
    }
  }
}
