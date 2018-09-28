///**create by liuhua at 2018年7月10日 下午5:48:19**/
//package com.star.truffle.core.security;
//
//import java.lang.reflect.Method;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//import com.star.truffle.core.web.ApiResult;
//
//@Configuration
//@RestControllerAdvice
//@ConditionalOnClass(ResponseBodyAdvice.class)
//@ConditionalOnWebApplication
//public class StarResponseBodyAdvice implements ResponseBodyAdvice {
//  
//  @Override
//  public boolean supports(MethodParameter methodParameter, Class converterType) {
//    StarAuthenticationEntryPoint.isJsonRequest = false;
//    Method method = methodParameter.getMethod();
//    if (method == null) {
//      return false;
//    }
//
//    Class<?> clazz = method.getReturnType();
//    if(clazz == null || !ApiResult.class.isAssignableFrom(clazz)) {
//      return false;
//    }
//    StarAuthenticationEntryPoint.isJsonRequest = true;
//    return true;
//  }
//
//  @Override
//  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//    return body;
//  }
//
//}
