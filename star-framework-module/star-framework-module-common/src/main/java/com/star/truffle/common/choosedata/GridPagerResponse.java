/**create by liuhua at 2018年9月18日 下午3:28:48**/
package com.star.truffle.common.choosedata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GridPagerResponse {

  private Integer page;
  private long total;
  private long records;
  private List<?> rows;
}
