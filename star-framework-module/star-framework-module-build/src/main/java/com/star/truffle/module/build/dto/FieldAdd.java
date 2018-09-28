/**create by liuhua at 2018年8月1日 上午11:46:01**/
package com.star.truffle.module.build.dto;

import com.star.truffle.module.build.constant.InputType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldAdd {
  
  private InputType inputType;       //类型 InputType
  private Select select;            //inputType=select，必有值，格式：{value:'1:可用;0:禁用'}
  private String pattern;            //inputType=date，必有值，
  private String placeholder;        //为空则取caption
  private boolean required;          //是否必填
  private String requiredMsg;        //是否必填文本
  private String zhengze;            //值满足的规范 正则表达式
  private String zhengzeMsg;         //不满足规范的文本
  private boolean hidden;            //是否隐藏
  private boolean substitute;        //若为隐藏，是否有替身，例如选择地区，areaId隐藏，areaName则为替身
  private String substituteName;     //替身名称
  
  private FieldAdd(InputType inputType, Select select, String pattern, String placeholder, boolean required, String requiredMsg, String zhengze, String zhengzeMsg, boolean hidden, boolean substitute, String substituteName) {
    super();
    this.inputType = inputType;
    this.select = select;
    this.pattern = pattern;
    this.placeholder = placeholder;
    this.required = required;
    this.requiredMsg = requiredMsg;
    this.zhengze = zhengze;
    this.zhengzeMsg = zhengzeMsg;
    this.hidden = hidden;
    this.substitute = substitute;
    this.substituteName = substituteName;
  }
  
  private FieldAdd(InputType inputType) {
    super();
    this.inputType = inputType;
  }
  
  private FieldAdd(InputType inputType, String placeholder) {
    super();
    this.inputType = inputType;
    this.placeholder = placeholder;
  }
  
  private FieldAdd(InputType inputType, String placeholder, boolean required, String requiredMsg, String zhengze, String zhengzeMsg) {
    super();
    this.inputType = inputType;
    this.placeholder = placeholder;
    this.required = required;
    this.requiredMsg = requiredMsg;
    this.zhengze = zhengze;
    this.zhengzeMsg = zhengzeMsg;
  }

  private FieldAdd(InputType inputType, String placeholder, boolean required, String requiredMsg, boolean substitute, String substituteName) {
    super();
    this.inputType = inputType;
    this.placeholder = placeholder;
    this.required = required;
    this.requiredMsg = requiredMsg;
    this.hidden = true;
    this.substitute = substitute;
    this.substituteName = substituteName;
  }
  
  private FieldAdd(InputType inputType, String inputValue, String placeholder, boolean required, String requiredMsg, String zhengze, String zhengzeMsg, boolean hidden, boolean substitute, String substituteName) {
    super();
    this.inputType = inputType;
    this.pattern = inputValue;
    this.placeholder = placeholder;
    this.required = required;
    this.requiredMsg = requiredMsg;
    this.zhengze = zhengze;
    this.zhengzeMsg = zhengzeMsg;
    this.hidden = true;
    this.substitute = substitute;
    this.substituteName = substituteName;
  }
  
  private FieldAdd(InputType inputType, Select select, String placeholder, boolean required, String requiredMsg, String zhengze, String zhengzeMsg, boolean hidden, boolean substitute, String substituteName) {
    super();
    this.inputType = inputType;
    this.select = select;
    this.placeholder = placeholder;
    this.required = required;
    this.requiredMsg = requiredMsg;
    this.zhengze = zhengze;
    this.zhengzeMsg = zhengzeMsg;
    this.hidden = true;
    this.substitute = substitute;
    this.substituteName = substituteName;
  }

  public static FieldAdd ofText(){
    return new FieldAdd(InputType.text);
  }
  public static FieldAdd ofText(String zhengze, String zhengzeMsg){
    return new FieldAdd(InputType.text, null, false, null, zhengze, zhengzeMsg);
  }
  public static FieldAdd ofText(boolean substitute, String substituteName){
    return new FieldAdd(InputType.text, null, false, null, substitute, substituteName);
  }
  public static FieldAdd ofText(String placeholder){
    return new FieldAdd(InputType.text, placeholder);
  }
  public static FieldAdd ofText(String placeholder, String zhengze, String zhengzeMsg){
    return new FieldAdd(InputType.text, placeholder, false, null, zhengze, zhengzeMsg);
  }
  public static FieldAdd ofText(String placeholder, boolean substitute, String substituteName){
    return new FieldAdd(InputType.text, placeholder, false, null, substitute, substituteName);
  }
  public static FieldAdd ofTextRequired(String requiredMsg, String zhengze, String zhengzeMsg){
    return new FieldAdd(InputType.text, null, true, requiredMsg, zhengze, zhengzeMsg);
  }
  public static FieldAdd ofTextRequired(String placeholder, String requiredMsg, String zhengze, String zhengzeMsg){
    return new FieldAdd(InputType.text, placeholder, true, requiredMsg, zhengze, zhengzeMsg);
  }
  public static FieldAdd ofTextRequired(String placeholder, String requiredMsg, boolean substitute, String substituteName){
    return new FieldAdd(InputType.text, placeholder, true, requiredMsg, substitute, substituteName);
  }
  public static FieldAdd ofTextHidden(){
    return new FieldAdd(InputType.text, null, true, null, false, null);
  }
  
  public static FieldAdd ofSelect(Select select){
    return new FieldAdd(InputType.select, select, null, null, false, null, null, null, false, false, null);
  }
  public static FieldAdd ofSelectRequired(Select select, String requiredMsg){
    return new FieldAdd(InputType.select, select, null, null, true, requiredMsg, null, null, false, false, null);
  }
  
  public static FieldAdd ofDate(String pattern){
    return new FieldAdd(InputType.date, null, pattern, null, false, null, null, null, false, false, null);
  }
  public static FieldAdd ofDateRequired(String pattern, String requiredMsg){
    return new FieldAdd(InputType.date, null, pattern, null, true, requiredMsg, null, null, false, false, null);
  }
  
  public static FieldAdd ofArea(){
    return new FieldAdd(InputType.area, null, null, null, false, null, null, null, false, false, null);
  }
  public static FieldAdd ofAreaRequired(String requiredMsg){
    return new FieldAdd(InputType.area, null, null, null, true, requiredMsg, null, null, false, false, null);
  }
  
  public static FieldAdd ofFile(){
    return new FieldAdd(InputType.file, null, null, null, false, null, null, null, false, false, null);
  }
  public static FieldAdd ofFileRequired(String requiredMsg){
    return new FieldAdd(InputType.file, null, null, null, true, requiredMsg, null, null, false, false, null);
  }
  
  public static FieldAdd of(InputType inputType, Select select, String pattern, String placeholder, boolean required, String requiredMsg, String zhengze, String zhengzeMsg, boolean hidden, boolean substitute, String substituteName) {
    return new FieldAdd(inputType, select, pattern, placeholder, required, requiredMsg, zhengze, zhengzeMsg, hidden, substitute, substituteName);
  }
  
  public static FieldAdd of(InputType inputType, String inputValue, String placeholder, boolean required, String requiredMsg, String zhengze, String zhengzeMsg, boolean hidden, boolean substitute, String substituteName) {
    return new FieldAdd(inputType, inputValue, placeholder, required, requiredMsg, zhengze, zhengzeMsg, hidden, substitute, substituteName);
  }

  public static FieldAdd of(InputType inputType, Select select, String placeholder, boolean required, String requiredMsg, String zhengze, String zhengzeMsg, boolean hidden, boolean substitute, String substituteName) {
    return new FieldAdd(inputType, select, placeholder, required, requiredMsg, zhengze, zhengzeMsg, hidden, substitute, substituteName);
  }
}
