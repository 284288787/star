/**create by liuhua at 2018年7月12日 上午10:41:40**/
package com.star.truffle.module.user.dto;

import java.util.Date;
import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto extends Page {

  private Long userId;            //用户Id 来自账号表
  private String name;            //姓名
  private String mobile;          //电话
  private Date lastModifyTime;    //最后修改时间
  
  private String account;
}
