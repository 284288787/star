/**create by liuhua at 2018年8月1日 上午11:46:01**/
package com.star.truffle.module.build.dto;

import com.star.truffle.module.build.constant.InputType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldList {
  
  private InputType inputType;       //类型 InputType
  private String options;            //inputType=select，必有值，格式：{value:'1:可用;0:禁用'}
  private String pattern;            //inputType=date，必有值， Y-m-d H:i:s
  private String width;              //宽度，没有单位表示百分比
  private boolean sort = true;       //是否可排序，默认可排序
  private boolean edit;              //是否可编辑，默认不可编辑
  private String align;              //对齐方式
  
  private boolean hidden;
  private String substituteName;
  
  private FieldList(InputType inputType, String options, String pattern, String width, boolean sort, boolean edit, String align) {
    super();
    this.inputType = inputType;
    this.options = options;
    this.pattern = pattern;
    this.width = width;
    this.sort = sort;
    this.edit = edit;
    this.align = align;
  }
  
  private FieldList(InputType inputType, String inputValue, String width, boolean sort, boolean edit, String align, boolean hidden, String substituteName) {
    super();
    this.inputType = inputType;
    if (inputType.equals(InputType.date)) {
      this.pattern = inputValue;
    }else {
      this.options = inputValue;
    }
    this.width = width;
    this.sort = sort;
    this.edit = edit;
    this.align = align;
    this.hidden = hidden;
    this.substituteName = substituteName;
  }
  
  public static FieldList ofText(String width){
    return new FieldList(InputType.text, null, null, width, true, false, "center");
  }
  public static FieldList ofTextEdit(String width, boolean sort, boolean edit){
    return new FieldList(InputType.text, null, null, width, sort, edit, "center");
  }

  public static FieldList ofSelect(String width, String options){
    return new FieldList(InputType.select, options, null, width, true, false, "center");
  }
  public static FieldList ofSelect(String width, String options, boolean sort, boolean edit){
    return new FieldList(InputType.select, options, null, width, sort, edit, "center");
  }
  
  public static FieldList ofDate(String width, String pattern){
    return new FieldList(InputType.date, null, pattern, width, true, false, "center");
  }
  public static FieldList ofDate(String width, String pattern, boolean sort, boolean edit){
    return new FieldList(InputType.select, null, pattern, width, sort, edit, "center");
  }
  
  public static FieldList of(InputType inputType, String options, String pattern, String width, boolean sort, boolean edit, String align) {
    return new FieldList(inputType, options, pattern, width, sort, edit, align);
  }
  
  public static FieldList of(InputType inputType, String inputValue, String width, boolean sort, boolean edit, String align, boolean hidden, String substituteName) {
    return new FieldList(inputType, inputValue, width, sort, edit, align, hidden, substituteName);
  }
}
