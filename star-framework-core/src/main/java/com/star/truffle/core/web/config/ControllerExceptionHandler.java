/**create by liuhua at 2018年7月4日 下午2:28:32**/
package com.star.truffle.core.web.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @Autowired
  private StarJson starJson;

  @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  @ResponseBody
  ResponseEntity<String> handleMaxUploadSizeExceededException(HttpServletRequest request, MaxUploadSizeExceededException e) {
    String msg = "文件不得大于 " + (e.getMaxUploadSize()/8/1024) + "KB";
    return toJsonResponse(HttpStatus.PAYLOAD_TOO_LARGE, request, msg);
  }

  private ResponseEntity<String> toJsonResponse(HttpStatus status, HttpServletRequest request, String exceptionMessage) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "application/json");
    return ResponseEntity.status(status).headers(responseHeaders).body(starJson.obj2string(ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), exceptionMessage)));
  }

}
