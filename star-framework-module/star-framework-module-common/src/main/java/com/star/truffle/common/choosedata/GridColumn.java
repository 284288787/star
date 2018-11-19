/**create by liuhua at 2018年9月19日 上午10:51:26**/
package com.star.truffle.common.choosedata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GridColumn {

  private String caption;
  private String javaName;
  private String dsName;
  private String width;
  private String align;
  private String formatter;
  private String editoptions;
  private String formatoptions;
  private boolean hidden;        //隐藏的列表
  private boolean notInList;     //不在列表展示
  
  private boolean query;
  private String type;           //查询字段的类型， text date datetime area choose
  private String service;        //type=choose时，service必填
  private String viewName;       //type=choose时，viewName必填，javaName必填 
  private String jsName;         //type=choose时，jsName必填，为返回的选择对象的id名称
  private String jsViewName;     //type=choose时，jsViewName必填，为返回的选择对象的name名称
  private String placeholder;
}
