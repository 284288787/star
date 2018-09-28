/**create by liuhua at 2017年5月26日 上午9:46:57**/
package com.star.truffle.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public abstract class StarFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

//	private PathMatcher matcher = new AntPathMatcher();
	
	public Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = null;
	
	public abstract void initResourceMap();
	
	public void loadResourceDefine() {
		initResourceMap();
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		RequestMatcher requestMatcher = new AntPathRequestMatcher("/login");
		if (requestMatcher.matches(request)) {
		  return null;
		}
		requestMatcher = new AntPathRequestMatcher("/api/login");
        if (requestMatcher.matches(request)) {
            return null;
        }
		requestMatcher = new AntPathRequestMatcher("/getLoginInfo");
		if (requestMatcher.matches(request)) {
		  return null;
		}
		requestMatcher = new AntPathRequestMatcher("/api/getLoginInfo");
		if (requestMatcher.matches(request)) {
			return null;
		}
		requestMatcher = new AntPathRequestMatcher("/logout");
        if (requestMatcher.matches(request)) {
            return null;
        }
		requestMatcher = new AntPathRequestMatcher("/api/logout");
		if (requestMatcher.matches(request)) {
			return null;
		}
		if (null == resourceMap) {
			loadResourceDefine();
		}
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			if (entry.getKey().matches(request)) {
				return entry.getValue();
			}
		}
		Collection<ConfigAttribute> list = new ArrayList<>();
		list.add(new SecurityConfig("ROLE_suoyourendoumeiyouzhegequanxian"));
		return list;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		if (null == resourceMap) {
			loadResourceDefine();
		}
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
