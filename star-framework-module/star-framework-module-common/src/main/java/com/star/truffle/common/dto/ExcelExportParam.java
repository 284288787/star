/**create by liuhua at 2018年8月15日 下午3:44:52**/
package com.star.truffle.common.dto;

import java.util.Map;

import com.star.truffle.common.properties.Excel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExcelExportParam {

  private String handle;
  private Excel excel;
  private Map<String, Object> params;
  
  public static ExcelExportParam of(String handle, Excel excel, Map<String, Object> params) {
    return new ExcelExportParam(handle, excel, params);
  }
}
