/**create by liuhua at 2018年9月18日 下午3:28:48**/
package com.star.truffle.common.choosedata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GridPagerRequest {

  private String service;
  private Integer page;
  private Integer rows;
  private String sord;
  private String sidx;
}
