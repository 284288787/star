/**create by liuhua at 2018年8月5日 上午10:33:13**/
package com.star.truffle.module.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountType {

  private Long userId;
  private String userType;           // 填写用户类型的Service名称，Service类必须实现UserType接口
  private String typeName;           // 类型名称
}
