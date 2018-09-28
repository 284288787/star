/**create by liuhua at 2018年8月6日 上午11:39:36**/
package com.star.truffle.module.build.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Select {

  private String typeName;
  private List<Map<String, String>> options = new ArrayList<>();
  
  public String getTypeName(){
    return this.typeName;
  }
  
  public List<Map<String, String>> getOptions(){
    return this.options;
  }
  
  private Select(){
    typeName = "select";
  }
  
  public static Select build(){
    return new Select();
  }
  
  public Select addOption(String value, String caption){
    Map<String, String> map = new HashMap<>();
    map.put("value", value);
    map.put("caption", caption);
    options.add(map);
    return this;
  }

  public Select addMap(Map<String, String> map) {
    for (Map.Entry<String, String> entry : map.entrySet()) {
      Map<String, String> temp = new HashMap<>();
      temp.put("value", entry.getKey());
      temp.put("caption", entry.getValue());
      options.add(temp);
    }
    return this;
  }
}
