/** create by liuhua at 2018年7月10日 上午9:15:49 **/
package com.star.truffle.core.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class StarLoginFilter extends UsernamePasswordAuthenticationFilter {

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    String username = request.getParameter("username");
    String password = request.getParameter("password");
//    if (response.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) || response.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

    if (StringUtils.isNotBlank(username)) {
      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
      setDetails(request, authRequest);
      return this.getAuthenticationManager().authenticate(authRequest);
    }else{
      return super.attemptAuthentication(request, response);
    }
  }

//  @Override
//  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//
//    String token = Jwts.builder()
//        .setSubject(authResult.getName())
//        // 有效期两小时
//        .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 2 * 1000))
//        .signWith(SignatureAlgorithm.HS512, "StarJwtSecret")
//        .compact();
//
//    response.addHeader("token", "Bearer " + token);
//
//    chain.doFilter(request, response);
//  }
}
