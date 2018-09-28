/**create by liuhua at 2018年7月12日 上午9:49:49**/
package com.star.truffle.module.user.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserAccount {

  private Long userId;               // 主键
  private String account;            // 帐号 默认手机号
  private String password;           // 密码加密后的字符串
  private Integer nonExpired;        // 是否过期 1未过期 0已过期
  private Integer nonLocked;         // 是否锁定 1未锁定 0已锁定
  private Integer enabled;           // 是否有效 1有效 0无效
  private Integer deleted;           // 是否删除 1已删除 0未删除
  private Date expiredTime;          // 是否过期最后的操作时间
  private Date lockedTime;           // 是否锁定最后的操作时间
  private Date enabledTime;          // 是否有效最后的操作时间
  private Date deletedTime;          // 是否删除最后的操作时间
  private Date createTime;           // 创建时间
  private Date lastModifyTime;       // 最后修改的时间
  //private String userType;           // 填写用户类型的Service名称，Service类必须实现UserType接口
}
