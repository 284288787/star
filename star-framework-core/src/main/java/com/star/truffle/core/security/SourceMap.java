///**create by liuhua at 2018年7月4日 下午4:58:54**/
//package com.star.truffle.core.security;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//public class SourceMap extends StarFilterInvocationSecurityMetadataSource{
//
//  @Override
//  public Map<RequestMatcher, Collection<ConfigAttribute>> initResourceMap() {
//    Map<RequestMatcher, Collection<ConfigAttribute>> map = new HashMap<>();
//    RequestMatcher requestMatcher1 = new AntPathRequestMatcher("/thymeleaf/hello");
//    RequestMatcher requestMatcher4 = new AntPathRequestMatcher("/api/getLoginInfo");
//    RequestMatcher requestMatcher6 = new AntPathRequestMatcher("/api/logout");
//    RequestMatcher requestMatcher5 = new AntPathRequestMatcher("/sampleWebCors/cors");
//    RequestMatcher requestMatcher3 = new AntPathRequestMatcher("/");
//    RequestMatcher requestMatcher7 = new AntPathRequestMatcher("/userAccount/**");
//    ConfigAttribute ca = new SecurityConfig("ROLE_TEST");
//    map.put(requestMatcher1, Arrays.asList(ca));
//    map.put(requestMatcher3, Arrays.asList(ca));
//    map.put(requestMatcher4, Arrays.asList(ca));
//    map.put(requestMatcher5, Arrays.asList(ca));
//    map.put(requestMatcher6, Arrays.asList(ca));
//    map.put(requestMatcher7, Arrays.asList(ca));
//    return map;
//  }
//
//}
