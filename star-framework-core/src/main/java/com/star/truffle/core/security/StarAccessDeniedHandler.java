/**create by liuhua at 2018年7月10日 下午3:46:50**/
package com.star.truffle.core.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;

public class StarAccessDeniedHandler implements AccessDeniedHandler {

  private String accessDeniedPage;
  
  public StarAccessDeniedHandler(String accessDeniedPage) {
    this.accessDeniedPage = accessDeniedPage;
  }
  
  public String getAccessDeniedPage(){
    return accessDeniedPage;
  }
  
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    String token = request.getHeader("token");
    if (StringUtils.isNotBlank(token)) {
      WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
      StarJson starJson = context.getBean(StarJson.class);
      
      ApiResult<Void> apiResult = ApiResult.fail(ApiCode.SECURITY_ACCESS_DENIED.getCode(), ApiCode.SECURITY_ACCESS_DENIED.getMsg());
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
      response.setCharacterEncoding("UTF-8");
      ServletOutputStream outputStream = response.getOutputStream();
      outputStream.write(starJson.obj2string(apiResult).getBytes("UTF-8"));
      outputStream.flush();
      outputStream.close();
    } else {
      if (StringUtils.isBlank(accessDeniedPage)) {
        accessDeniedPage = "/accessDenied";
      }
      response.sendRedirect(accessDeniedPage);
    }
  }

}
