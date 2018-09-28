/**create by liuhua at 2018年8月15日 上午9:58:38**/
package com.star.truffle.common.properties;

import lombok.Data;

@Data
public class Excel {

  private String fileName;
  private String sheetName;
  private String[] fields;
}
