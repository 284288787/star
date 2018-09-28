/**create by liuhua at 2018年9月19日 下午5:13:29**/
package com.star.truffle.module.alibaba.constant;

public enum SmsTagEnum {

  member_login(1), distrbutor_login(2);
  
  private int tag;
  
  private SmsTagEnum(int tag) {
    this.tag = tag;
  }
  
  public int getTag() {
    return tag;
  }
}
