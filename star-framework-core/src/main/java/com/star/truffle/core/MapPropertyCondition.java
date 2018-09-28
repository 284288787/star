/**create by liuhua at 2018年6月8日 下午3:42:05**/
package com.star.truffle.core;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.FatalBeanException;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import com.star.truffle.core.mybatis.MybatisProperties;

public class MapPropertyCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    Binder binder = Binder.get(context.getEnvironment());
    String prefix = attribute(metadata, "prefix");
    Class<?> value = (Class<?>) attribute(metadata, "value");
    if (value.equals(MybatisProperties.class)) {
      binder.bind(prefix, value).orElseThrow(() -> new FatalBeanException("Could not bind DataSourceSettings properties"));
    }
    Map<String, Object> properties = binder.bind(prefix, Bindable.mapOf(String.class, Object.class)).orElse(new HashMap<>());
    return new ConditionOutcome(!properties.isEmpty(), String.format("Map property [%s] is empty", prefix));
  }

  @SuppressWarnings("unchecked")
  private static <T> T attribute(AnnotatedTypeMetadata metadata, String name) {
    return (T) metadata.getAnnotationAttributes(ConditionalOnMapProperty.class.getName()).get(name);
  }
}
