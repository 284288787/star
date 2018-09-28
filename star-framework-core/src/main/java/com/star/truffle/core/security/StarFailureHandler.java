/**create by liuhua at 2018年7月10日 上午9:27:07**/
package com.star.truffle.core.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;

public class StarFailureHandler implements AuthenticationFailureHandler {
  
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    StarJson starJson = context.getBean(StarJson.class);
    
    ApiResult<Void> apiResult = ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), exception.getMessage());
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    response.setCharacterEncoding("UTF-8");
    ServletOutputStream outputStream = response.getOutputStream();
    outputStream.write(starJson.obj2string(apiResult).getBytes("UTF-8"));
    outputStream.flush();
    outputStream.close();
  }

}
