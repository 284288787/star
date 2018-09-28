/**create by liuhua at 2018年7月10日 上午9:24:20**/
package com.star.truffle.core.security;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.util.MemoryCacheUtil;
import com.star.truffle.core.web.ApiResult;

public class StarSuccessHandler implements AuthenticationSuccessHandler {
  
  @Autowired
  private StarJson starJson;
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//    String token = Jwts.builder()
//        .setSubject(authentication.getName())
//        // 有效期两小时
//        .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 2 * 1000))
//        .signWith(SignatureAlgorithm.HS512, "StarJwtSecret")
//        .compact();
//    response.addHeader("Authorization", "Bearer " + token);
    String token = UUID.randomUUID().toString().replace("-", "");
    StarUserInfo userInfo = (StarUserInfo) authentication.getPrincipal();
    userInfo.setToken(token);
    MemoryCacheUtil.put(token, userInfo);
    
    ApiResult<Object> apiResult = ApiResult.success(userInfo);
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    response.setCharacterEncoding("UTF-8");
    ServletOutputStream outputStream = response.getOutputStream();
    outputStream.write(starJson.obj2string(apiResult).getBytes("UTF-8"));
    outputStream.flush();
    outputStream.close();
  }

}
