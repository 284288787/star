/** create by liuhua at 2018年7月16日 下午3:41:46 **/
package com.star.truffle.common.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

@Controller
public class CommonJumpController {

  @RequestMapping(value = "/common/**", method = RequestMethod.GET)
  public String common(HttpServletRequest request) {
    String uriPath = extractPathFromPattern(request);
    return uriPath;
  }

  private static String extractPathFromPattern(final HttpServletRequest request) {
    String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
  }
}
