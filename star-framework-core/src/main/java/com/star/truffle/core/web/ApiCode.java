/**create by liuhua at 2018年6月4日 上午10:44:21**/
package com.star.truffle.core.web;

public enum ApiCode implements ApiCodeIntf {
  SUCCESS(0, "成功"),
  PARAM_ERROR(1, "参数错误"),
  NO_LOGIN(2, "未登录"),
  NO_EXISTS(3, "用户不存在"),
  SMS_SENDED(4, "验证码已经发送，请查看手机"),
  SMS_ERROR(5, "验证码错误"),
  SYSTEM_ERROR(-1, "系统错误"), 
  SQL_ERROR(-2, "SQL错误"),
  /**
   * http 错误 601-699
   **/
  HTTP_METHOD_NOT_ALLOW_ERROR(601, "http请求method不对"),
  HTTP_CONNECTION_TIME_OUT(602, "http请求连接超时"),
  HTTP_READ_TIME_OUT(603, "http读超时"),
  HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR(604, "media类型出错"),
  HTTP_INNTER_CONNECTION_TIME_OUT(652, "内部系统调用连接超时"),
  HTTP_INNTER_READ_TIME_OUT(653, "内部系统调用读取超时"),
  HTTP_INNTER_SERVICE_NOT_AVAILABLE(654, "内部系统调用服务不可用"),
  HTTP_INNTER_ERROR(689, "http内部调用服务错误"),
  HTTP_ERROR(699, "http请求错误"),

  /**
   * redis处理异常 800-899
   **/
  REDIS_ERROR(899, "redis操作异常"),
  /**
   * 文件处理异常  1100-1199
   **/
  FILE_ERROR(1199, "文件操作异常"),
  
  /**
   * 1400 1499 secrutiy异常
   */
  SECURITY_ACCESS_DENIED(1403,"未授权访问!"),
  SECURITY_AUTHENTICATION(1401,"身份验证失败,请输入正确信息!"),
  SECURITY_TOKEN_NOT_ACTIVE(1402,"TOKEN 已失效，刷新token或者重新获取!");
  
  private int code;
  private String msg;
  
  private ApiCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  @Override
  public int getCode() {
    return this.code;
  }

  @Override
  public String getMsg() {
    return this.msg;
  }

}
