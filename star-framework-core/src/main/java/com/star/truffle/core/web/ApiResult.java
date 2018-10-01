/**create by liuhua at 2018年6月4日 上午10:41:02**/
package com.star.truffle.core.web;

import com.star.truffle.core.StarServiceException;

import lombok.Data;

@Data
public class ApiResult<T> {

  private int code;
  private String msg;
  private T data;
  
  private String traceId;
  
  public ApiResult(int code, String msg){
    this.setCode(code);
    this.setMsg(msg);
  }
  
  public ApiResult(){
    this(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg());
  }
  
  public ApiResult(T data){
    this();
    this.setData(data);
  }
  
  public ApiResult(ApiCodeIntf code){
    this(code.getCode(), code.getMsg());
  }
  
  public static <T> ApiResult<T> success(){
    return new ApiResult<T>();
  }
  
  public static <T> ApiResult<T> success(T data){
    return new ApiResult<T>(data);
  }
  
  public static <T> ApiResult<T> fail(){
    return new ApiResult<T>(ApiCode.SYSTEM_ERROR.getCode(), ApiCode.SYSTEM_ERROR.getMsg());
  }
  
  public static <T> ApiResult<T> fail(int code, String msg){
    return new ApiResult<T>(code, msg);
  }
  
  public static <T> ApiResult<T> fail(ApiCodeIntf code){
    return new ApiResult<T>(code);
  }
  
  public static <T> ApiResult<T> fail(StarServiceException e){
    return new ApiResult<T>(e.getCode(), e.getMsg());
  }
}
