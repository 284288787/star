/**create by liuhua at 2018年7月4日 下午2:28:32**/
package com.star.truffle.core.web.config;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.keycloak.adapters.springsecurity.KeycloakAuthenticationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.star.truffle.core.EmptyException;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RestControllerAdvice(annotations = RestController.class)
@ConditionalOnWebApplication
public class RestControllerExceptionHandler {

  @ConditionalOnClass(EmptyException.class)
  @Configuration
  @RestControllerAdvice
  static class EmptyExceptionConfiguration {

    @ExceptionHandler(EmptyException.class)
    public ApiResult<Void> handleEmptyException(EmptyException e) {
      logWarn("EmptyException", e);
      return ApiResult.fail(e.getCode(), e.getMsg());
    }
  }

  @ConditionalOnClass(StarServiceException.class)
  @Configuration
  @RestControllerAdvice
  static class StarServiceExceptionConfiguration {

    @ExceptionHandler(StarServiceException.class)
    public ApiResult<Void> handleStarServiceException(StarServiceException e) {
      logWarn("StarServiceException", e);
      return ApiResult.fail(e.getCode(), e.getMsg());
    }
  }

  @ConditionalOnClass(MethodArgumentNotValidException.class)
  @Configuration
  @RestControllerAdvice
  static class MethodArgumentNotValidExceptionConfiguration {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<Void> handMethodArgumentNotValidException(MethodArgumentNotValidException e) {
      logWarn("MethodArgumentNotValidException", e);
      return ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), MethodArgumentNotValidExceptionHelper.firstErrorMessage(e.getBindingResult()));
    }

    public static class MethodArgumentNotValidExceptionHelper {

      static String firstErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().findFirst().map(ObjectError::getDefaultMessage).orElse("");
      }
    }
  }

  @ConditionalOnClass(BindException.class)
  @Configuration
  @RestControllerAdvice
  static class BindExceptionConfiguration {

    @ExceptionHandler(BindException.class)
    public ApiResult<Void> handBindException(BindException e) {
      logWarn("BindException", e);
      return ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), BindExceptionHelper.firstErrorMessage(e.getBindingResult()));
    }

    public static class BindExceptionHelper {

      static String firstErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().findFirst().map(ObjectError::getDefaultMessage).orElse("");
      }
    }
  }

  @ConditionalOnClass(HttpRequestMethodNotSupportedException.class)
  @Configuration
  @RestControllerAdvice
  static class HttpRequestMethodNotSupportedExceptionConfiguration {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
      logWarn("HttpRequestMethodNotSupportedException", e);
      return ApiResult.fail(ApiCode.HTTP_METHOD_NOT_ALLOW_ERROR.getCode(), ApiCode.HTTP_METHOD_NOT_ALLOW_ERROR.getMsg());
    }
  }

  @ConditionalOnClass(MissingServletRequestParameterException.class)
  @Configuration
  @RestControllerAdvice
  static class MissingServletRequestParameterExceptionConfiguration {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
      logWarn("MissingServletRequestParameterException", e);
      return ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), String.format("参数%s未传", e.getParameterName()));
    }
  }

  @ConditionalOnClass(ConstraintViolationException.class)
  @Configuration
  @RestControllerAdvice
  static class ConstraintViolationExceptionConfiguration {

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult<Void> handleConstraintViolationException(ConstraintViolationException e) {
      logWarn("ConstraintViolationException", e);
      return ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), ConstraintViolationExceptionHelper.firstErrorMessage(e.getConstraintViolations()));
    }

    public static class ConstraintViolationExceptionHelper {

      static String firstErrorMessage(Set<ConstraintViolation<?>> constraintViolations) {
        return Optional.ofNullable(constraintViolations).orElseGet(HashSet::new).stream().findFirst().map(ConstraintViolation::getMessage).orElse("");
      }
    }
  }

  @ConditionalOnClass(HttpMediaTypeNotSupportedException.class)
  @Configuration
  @RestControllerAdvice
  static class HttpMediaTypeNotSupportedExceptionConfiguration {

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ApiResult<Void> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
      logWarn("HttpMediaTypeNotSupportedException", e);
      return ApiResult.fail(ApiCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR.getCode(), ApiCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR.getMsg());
    }
  }

  @ConditionalOnClass(SQLException.class)
  @Configuration
  @RestControllerAdvice
  static class SqlExceptionConfiguration {

    @ExceptionHandler(SQLException.class)
    public ApiResult<Void> handleSqlException(SQLException e) {
      logError("SQLException", e);
      return ApiResult.fail(ApiCode.SQL_ERROR.getCode(), ApiCode.SQL_ERROR.getMsg());
    }
  }

  @ConditionalOnClass(RedisException.class)
  @Configuration
  @RestControllerAdvice
  static class RedisExceptionConfiguration {

    @ExceptionHandler(RedisException.class)
    public ApiResult<Void> handleRedisException(RedisException e) {
      logError("RedisException", e);
      return ApiResult.fail(ApiCode.REDIS_ERROR.getCode(), ApiCode.REDIS_ERROR.getMsg());
    }
  }

  /*
   * @ConditionalOnClass(OkHttpException.class)
   * 
   * @Configuration
   * 
   * @RestControllerAdvice static class OkHttpExceptionCofiguration {
   * 
   * private static final String CONNECTION_TIME_OUT = "connect timed out";
   * private static final String READ_TIME_OUT = "timeout";
   * 
   * @ExceptionHandler(OkHttpException.class) public ApiResult
   * handlerOkHttpException(OkHttpException e) { logError("OkHttpException", e);
   * String message = e.getCause().getLocalizedMessage(); if
   * (CONNECTION_TIME_OUT.equals(message)) { return
   * ApiResult.fail(ApiCode.HTTP_CONNECTION_TIME_OUT.getCode(),
   * ApiCode.HTTP_CONNECTION_TIME_OUT.getMsg()); } else if
   * (READ_TIME_OUT.equals(message)) { return
   * ApiResult.fail(ApiCode.HTTP_READ_TIME_OUT.getCode(),
   * ApiCode.HTTP_READ_TIME_OUT.getMsg()); } return
   * ApiResult.fail(ApiCode.HTTP_ERROR.getCode(), ApiCode.HTTP_ERROR.getMsg());
   * } }
   */

  @ConditionalOnClass(AccessDeniedException.class)
  @Configuration
  @RestControllerAdvice
  static class AccessDeniedExceptionConfiguration {

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResult<Void> handAccessDeniedException(AccessDeniedException e) {
      logWarn("AccessDeniedException", e);
      return ApiResult.fail(ApiCode.SECURITY_ACCESS_DENIED.getCode(), ApiCode.SECURITY_ACCESS_DENIED.getMsg());
    }
  }

  @ConditionalOnClass(BadCredentialsException.class)
  @Configuration
  @RestControllerAdvice
  static class BadCredentialsExceptionConfiguration {

    @ExceptionHandler(BadCredentialsException.class)
    public ApiResult<Void> handAccessDeniedException(BadCredentialsException e) {
      logWarn("BadCredentialsException", e);
      return ApiResult.fail(ApiCode.NO_LOGIN.getCode(), e.getMessage());
    }
  }

  @ConditionalOnClass(KeycloakAuthenticationException.class)
  @Configuration
  @RestControllerAdvice
  static class KeycloakAuthenticationExceptionConfiguration {

    @ExceptionHandler(KeycloakAuthenticationException.class)
    public ApiResult<Void> handKeycloakAuthenticationException(KeycloakAuthenticationException e) {
      logWarn("KeycloakAuthenticationException", e);
      return ApiResult.fail(ApiCode.SECURITY_AUTHENTICATION.getCode(), ApiCode.SECURITY_AUTHENTICATION.getMsg());
    }
  }

  @ExceptionHandler(Throwable.class)
  public ApiResult<Void> handleThrowable(Throwable e) {
    logError("Throwable", e);
    return ApiResult.fail();
  }

  private static void logError(String name, Throwable e) {
    log.error("{}", name, e);
  }

  private static void logWarn(String name, Throwable e) {
    log.warn("{}", name, e);
  }
}
