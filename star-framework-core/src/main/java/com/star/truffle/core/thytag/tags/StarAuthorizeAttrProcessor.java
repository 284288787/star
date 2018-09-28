/** create by liuhua at 2018年7月30日 上午9:37:59 **/
package com.star.truffle.core.thytag.tags;

import java.util.Collection;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.standard.processor.AbstractStandardConditionalVisibilityTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;
import com.star.truffle.core.security.StarFilterInvocationSecurityMetadataSource;

public final class StarAuthorizeAttrProcessor extends AbstractStandardConditionalVisibilityTagProcessor {

  public static final int ATTR_PRECEDENCE = 300;
  public static final String ATTR_NAME = "authorize-uri";
  private boolean useAuth;

  public StarAuthorizeAttrProcessor(final TemplateMode templateMode, final String dialectPrefix, boolean useAuth) {
    super(templateMode, dialectPrefix, ATTR_NAME, ATTR_PRECEDENCE);
    this.useAuth = useAuth;
  }

  @Override
  protected boolean isVisible(final ITemplateContext context, final IProcessableElementTag tag, final AttributeName attributeName, final String attributeValue) {
    if (! useAuth) {
      return true;
    }
    final String attrValue = (attributeValue == null ? null : attributeValue.trim());
    if (attrValue == null || attrValue.length() == 0) {
      return false;
    }
    if (!(context instanceof IWebContext)) {
      throw new ConfigurationException("Thymeleaf execution context is not a web context (implementation of " + IWebContext.class.getName() + "). Spring Security integration can only be used in " + "web environments.");
    }
    final IWebContext webContext = (IWebContext) context;
    final HttpServletRequest request = webContext.getRequest();
    final HttpServletResponse response = webContext.getResponse();
    final ServletContext servletContext = webContext.getServletContext();

    final Authentication authentication = AuthUtils.getAuthenticationObject();

    if (authentication == null) {
      return false;
    }
    ApplicationContext appCtx = SpringContextUtils.getApplicationContext(context);
    StringBuilder exp = new StringBuilder();
    try {
      StarFilterInvocationSecurityMetadataSource sourceMap = appCtx.getBean("starFilterInvocationSecurityMetadataSource", StarFilterInvocationSecurityMetadataSource.class);
      for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : sourceMap.resourceMap.entrySet()) {
        AntPathRequestMatcher aprm = (AntPathRequestMatcher) entry.getKey();
        if (aprm.getPattern().equals(attributeValue)) {
          Collection<ConfigAttribute> coll = entry.getValue();
          if (null != coll && !coll.isEmpty()) {
            for (ConfigAttribute configAttribute : coll) {
              String arrt = configAttribute.getAttribute();
              exp.append("||").append("hasRole('" + arrt + "')");
            }
            break;
          }
        }
      }
    } catch (NoSuchBeanDefinitionException e) {
      
    }
    if (exp.length() == 0) {
      return false;
    }
    return AuthUtils.authorizeUsingAccessExpression(context, exp.substring(2), authentication, request, response, servletContext);
  }
}

