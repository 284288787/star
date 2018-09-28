/**create by liuhua at 2018年7月14日 下午4:48:44**/
package com.star.truffle.common.constants;

public enum DeletedEnum {

  delete(1), notdelete(0);
  
  private int value;
  
  private DeletedEnum(int val){
    this.value = val;
  }
  
  public int val(){
    return value;
  }
}
