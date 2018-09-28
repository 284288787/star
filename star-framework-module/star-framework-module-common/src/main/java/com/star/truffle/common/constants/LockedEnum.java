/**create by liuhua at 2018年7月17日 下午3:37:56**/
package com.star.truffle.common.constants;

public enum LockedEnum {

  locked(0), unlock(1);
  
  private int value;
  
  private LockedEnum(int value){
    this.value = value;
  }
  
  public int val(){
    return value;
  }
}
