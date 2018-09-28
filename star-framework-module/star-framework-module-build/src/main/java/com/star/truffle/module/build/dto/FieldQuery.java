/**create by liuhua at 2018年8月1日 上午11:46:01**/
package com.star.truffle.module.build.dto;

import com.star.truffle.module.build.constant.InputType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldQuery {
  
  private InputType inputType;       //类型 InputType
  private Select select;            //inputType=select，必有值，格式：{value:'1:可用;0:禁用'}
  private String pattern;            //inputType=date，必有值，
  private boolean hidden;            //是否隐藏
  private boolean substitute;        //若为隐藏，是否有替身，例如选择地区，areaId隐藏，areaName则为替身
  private String substituteName;     //替身名称
  
  private FieldQuery(InputType inputType, Select select, String pattern) {
    super();
    this.inputType = inputType;
    this.select = select;
    this.pattern = pattern;
  }

  private FieldQuery(InputType inputType, boolean hidden, boolean substitute, String substituteName) {
    super();
    this.inputType = inputType;
    this.hidden = hidden;
    this.substitute = substitute;
    this.substituteName = substituteName;
  }
  
  public static FieldQuery ofText(){
    return new FieldQuery(InputType.text, null, null);
  }

  public static FieldQuery ofSelect(Select select){
    return new FieldQuery(InputType.select, select, null);
  }
  
  public static FieldQuery ofDate(String pattern){
    return new FieldQuery(InputType.date, null, pattern);
  }
  
  public static FieldQuery ofArea(){
    return new FieldQuery(InputType.area, false, false, null);
  }

  public static FieldQuery ofArea(String substituteName){
    return new FieldQuery(InputType.area, true, true, substituteName);
  }
}
