/**create by liuhua at 2018年9月30日 下午3:39:02**/
package com.star.truffle.core.web.response;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.util.DateUtils;
import com.star.truffle.core.web.ApiResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration
public class RequestInfoAspect {

  @Autowired
  private StarJson starJson;

  @Around(value = "execution(* com.star.truffle..controller.api.*ApiController.*(..))")
  public Object cacheable(ProceedingJoinPoint joinPoint) {
    Map<String, Object> args = getFieldsNameValue(joinPoint);
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    HttpServletRequest request = sra.getRequest();
    String uri = request.getRequestURI();
    Map<String, Object> params = new LinkedHashMap<>();
    params.put("uri", uri);
    params.put("args", args);
    
    Object object = null;
    try {
      object = joinPoint.proceed();
      if (object instanceof ApiResult) {
        ApiResult<?> response = (ApiResult<?>) object;
        String traceId = UUID.randomUUID().toString().replace("-", "");
        response.setTraceId(traceId);
        object = response;
      }
    } catch (Throwable e) {
      e.printStackTrace();
      object = ApiResult.fail();
    }
    params.put("result", object);
    
    log.info(DateUtils.formatTodayDateTime() + " -> " + starJson.obj2string(params));
    return object;
  }
  
  ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
  Map<Method, String[]> cache = new HashMap<>();
  
  private Map<String, Object> getFieldsNameValue(JoinPoint joinPoint) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Object[] args = joinPoint.getArgs();
    String[] names = cache.get(methodSignature.getMethod());
    if (null == names) {
      names = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
      cache.put(methodSignature.getMethod(), names);
    }
    Map<String, Object> context = new LinkedHashMap<>();
    for (int i = 0; i < names.length; i++) {
      if ("binder".equals(names[i]) || args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse) {
        continue;
      }
      context.put(names[i], args[i]);// paramNames即参数名
    }
    return context;
  }
}
