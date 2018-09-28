/** create by liuhua at 2018年7月30日 上午9:17:30 **/
package com.star.truffle.core.thytag;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.extras.springsecurity4.dialect.expression.SpringSecurityExpressionObjectFactory;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.templatemode.TemplateMode;
import com.star.truffle.core.thytag.tags.StarAuthorizeAttrProcessor;

@EnableConfigurationProperties(TagProperties.class)
public class StarSpringSecurityDialect extends AbstractDialect implements IProcessorDialect, IExpressionObjectDialect {

  @Autowired
  private TagProperties tagProperties;
  
  public static final String NAME = "StarSpringSecurity";
  public static final String DEFAULT_PREFIX = "star";
  public static final int PROCESSOR_PRECEDENCE = 800;

  public static final IExpressionObjectFactory EXPRESSION_OBJECT_FACTORY = new SpringSecurityExpressionObjectFactory();

  public StarSpringSecurityDialect() {
    super(NAME);
  }

  public String getPrefix() {
    return DEFAULT_PREFIX;
  }

  public int getDialectProcessorPrecedence() {
    return PROCESSOR_PRECEDENCE;
  }

  public Set<IProcessor> getProcessors(final String dialectPrefix) {

    final Set<IProcessor> processors = new LinkedHashSet<IProcessor>();

    final TemplateMode[] templateModes = new TemplateMode[] {TemplateMode.HTML, TemplateMode.XML, TemplateMode.TEXT, TemplateMode.JAVASCRIPT, TemplateMode.CSS};

    for (final TemplateMode templateMode : templateModes) {
      processors.add(new StarAuthorizeAttrProcessor(templateMode, dialectPrefix, tagProperties.isUsePageAuth()));
    }

    return processors;
  }

  public IExpressionObjectFactory getExpressionObjectFactory() {
    return EXPRESSION_OBJECT_FACTORY;
  }

}
