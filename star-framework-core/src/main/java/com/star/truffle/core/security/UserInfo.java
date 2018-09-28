/**create by liuhua at 2018年8月6日 上午9:20:36**/
package com.star.truffle.core.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

  private Long userId;
  private String userType;
  private String typeName;
  private Object userInfo;
}
