/**create by liuhua at 2018年9月19日 下午5:10:55**/
package com.star.truffle.module.alibaba.domain;

import java.util.Date;

import lombok.Data;

@Data
public class SmsIdentity {

  private Long id;         // id
  private String mobile;    // 接收短信号码
  private Integer tag;     // 本次短信用途 SmsTagEnum
  private String code;     // 验证码
  private String content;  // 短信内容
  private Date createTime; // 发送时间

  private long time = 60 * 1000;

  /**
   * @return true 有效 false无效
   */
  public boolean isValid() {
    if (null != createTime && createTime.getTime() + time > System.currentTimeMillis()) {
      return true;
    }
    return false;
  }
}
