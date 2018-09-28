/**create by liuhua at 2018年6月5日 上午11:47:13**/
package com.star.truffle.core;

import com.star.truffle.core.web.ApiCode;
import lombok.Getter;

@Getter
public class EmptyException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  private String msg;
  private int code = ApiCode.PARAM_ERROR.getCode();

  public EmptyException(String msg) {
    super(msg);
    this.msg = msg;
  }

}
