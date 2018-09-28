/**create by liuhua at 2018年7月6日 下午4:49:37**/
package com.star.truffle.core.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;

public class StarAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

  public StarAuthenticationEntryPoint(String loginFormUrl) {
    super(loginFormUrl);
  }
  
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    StarJson starJson = context.getBean(StarJson.class);
    
    String token = request.getHeader("token");
    if (StringUtils.isNotBlank(token)) {
      ApiResult<Void> apiResult = ApiResult.fail(ApiCode.SECURITY_TOKEN_NOT_ACTIVE);
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
      PrintWriter printWriter = response.getWriter();
      printWriter.println(starJson.obj2string(apiResult));
    }else{
      super.commence(request, response, authException);
//      response.sendRedirect(getLoginFormUrl());
    }
  }

}
