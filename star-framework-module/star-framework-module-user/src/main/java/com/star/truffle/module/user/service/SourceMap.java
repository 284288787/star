/** create by liuhua at 2018年7月4日 下午4:58:54 **/
package com.star.truffle.module.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import com.star.truffle.core.security.StarFilterInvocationSecurityMetadataSource;
import com.star.truffle.module.user.dto.RoleResourceRelationDto;

@Service("starFilterInvocationSecurityMetadataSource")
public class SourceMap extends StarFilterInvocationSecurityMetadataSource {

  @Autowired
  private ResourceService resourceService;
  
  @PostConstruct
  public void initResourceMap() {
    resourceMap = new HashMap<>();
    List<RoleResourceRelationDto> all = resourceService.queryAllRoleResource();
    if (null != all && !all.isEmpty()) {
      for (RoleResourceRelationDto resource : all) {
        if (StringUtils.isBlank(resource.getUri()) || "menu".equals(resource.getUri()) || StringUtils.isBlank(resource.getRoleName())) {
          continue;
        }
        String url = resource.getUri();
        String roleName = resource.getRoleName().toUpperCase();
        ConfigAttribute ca = new SecurityConfig("ROLE_" + roleName);
        String urls[] = url.split(",");
        for (String u : urls) {
          if (StringUtils.isBlank(u)) {
            continue;
          }
          RequestMatcher requestMatcher = new AntPathRequestMatcher(u);
          Collection<ConfigAttribute> value = resourceMap.get(requestMatcher);
          if (null == value) {
            value = new ArrayList<ConfigAttribute>();
            resourceMap.put(requestMatcher, value);
          }
          value.add(ca);
        }
      }
    }
  }

}
