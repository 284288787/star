/**create by liuhua at 2018年6月5日 上午11:48:40**/
package com.star.truffle.core;

import com.star.truffle.core.web.ApiCodeIntf;
import lombok.Getter;

@Getter
public class StarServiceException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  private String msg;
  private int code = -1;
  
  public StarServiceException(String msg) {
    super(msg);
    this.msg = msg;
  }
  
  public StarServiceException(ApiCodeIntf code) {
    this(code.getCode(), code.getMsg());
  }

  public StarServiceException(ApiCodeIntf code, String msg) {
    this(code.getCode(), msg);
  }

  public StarServiceException(String msg, Throwable e) {
    super(msg, e);
    this.msg = msg;
  }

  public StarServiceException(int code, String msg) {
    super(msg);
    this.msg = msg;
    this.code = code;
  }

  public StarServiceException(int code, String msg, Throwable e) {
    super(msg, e);
    this.msg = msg;
    this.code = code;
  }
}
