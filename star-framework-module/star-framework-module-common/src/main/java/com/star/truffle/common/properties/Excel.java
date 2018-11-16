/**create by liuhua at 2018年8月15日 上午9:58:38**/
package com.star.truffle.common.properties;

import java.util.List;

import lombok.Data;

@Data
public class Excel {

  private String fileName;
  private String sheetName;
  private String tableCaption;      //表格名称
  private List<RowData> rowDatas;   //指定行的内容
  private Integer fieldsRowNum = 0;        //表头所在行
  private String[] fields;
}
