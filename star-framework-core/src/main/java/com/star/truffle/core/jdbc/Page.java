/**create by liuhua at 2018年7月12日 上午10:35:47**/
package com.star.truffle.core.jdbc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Page {

  private Integer pageNum;
  private Integer pageSize;
  
  private String orderBy;
  private OrderType orderType;
  
  public enum OrderType{
    asc, desc
  }
  
  public Integer getStartIndex(){
    if (null == pageSize) {
      pageSize = 10;
    }
    if (null != pageNum && pageNum > 0) {
      return (pageNum - 1) * pageSize;
    }
    return null;
  }
}
