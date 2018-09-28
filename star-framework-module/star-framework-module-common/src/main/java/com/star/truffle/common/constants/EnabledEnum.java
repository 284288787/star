/**create by liuhua at 2018年7月14日 下午3:54:27**/
package com.star.truffle.common.constants;

public enum EnabledEnum {

  enabled(1), disabled(0);
  
  private int value;
  
  private EnabledEnum(int value){
    this.value = value;
  }
  
  public int val(){
    return value;
  }
}
