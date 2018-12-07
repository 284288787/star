/**create by liuhua at 2018年11月16日 下午2:10:28**/
package com.star.truffle.common.properties;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RowData {

  private Integer rowNum;
  private List<Map<String, String>> rows;
}
