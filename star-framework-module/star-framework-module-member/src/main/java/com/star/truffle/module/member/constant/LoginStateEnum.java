/**create by liuhua at 2018年9月20日 上午10:32:20**/
package com.star.truffle.module.member.constant;

public enum LoginStateEnum {

  logout(1), login(2);
  
  private int state;
  
  private LoginStateEnum(int state) {
    this.state = state;
  }
  
  public int getState() {
    return state;
  }
}
