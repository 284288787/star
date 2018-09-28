/** create by liuhua at 2018年7月10日 上午10:58:24 **/
package com.star.truffle.core.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.util.MemoryCacheUtil;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;

public class StarAuthenticationFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    String token = ((HttpServletRequest)request).getHeader("token");
    // 判断是否有token
    if (StringUtils.isBlank(token)) {
      chain.doFilter(request, response);
      return;
    }
    
    UsernamePasswordAuthenticationToken authenticationToken = null;
    try {
      authenticationToken = getAuthentication(token);
    } catch (Exception e) {
      WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
      StarJson starJson = context.getBean(StarJson.class);
      
      ApiResult<Void> apiResult = ApiResult.fail(ApiCode.NO_LOGIN.getCode(), e.getMessage());
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
      response.setCharacterEncoding("UTF-8");
      ServletOutputStream outputStream = response.getOutputStream();
      outputStream.write(starJson.obj2string(apiResult).getBytes("UTF-8"));
      outputStream.flush();
      outputStream.close();
      return;
    }

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    // 放行
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
//    Claims claims = Jwts.parser().setSigningKey("StarJwtSecret").parseClaimsJws(token.replace("Bearer ", "")).getBody();
//    // 得到用户名
//    String username = claims.getSubject();
//    // 得到过期时间
//    Date expiration = claims.getExpiration();
//    // 判断是否过期
//    Date now = new Date();
//    if (now.getTime() > expiration.getTime()) {
//      throw new LockedException("该账号已过期,请重新登陆");
//    }
    StarUserInfo userInfo = MemoryCacheUtil.get(token).orElseThrow(() -> new BadCredentialsException("该账号已过期,请重新登陆"));
    return new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
  }
}
