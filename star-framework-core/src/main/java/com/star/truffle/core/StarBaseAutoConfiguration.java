/** create by liuhua at 2018年7月2日 上午11:42:19 **/
package com.star.truffle.core;

import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.MutablePropertySources;

public class StarBaseAutoConfiguration {
  
  protected void register(ConfigurableListableBeanFactory beanFactory, Object bean, String name, String alias) {
    beanFactory.registerSingleton(name, bean);
    if (!beanFactory.containsSingleton(alias)) {
      beanFactory.registerAlias(name, alias);
    }
  }

  // 读取配置并转换成对象
  protected <T> T resolverSetting(Class<T> clazz, MutablePropertySources propertySources) {
    return new Binder(ConfigurationPropertySources.from(propertySources)).bind("", Bindable.of(clazz)).orElseThrow(() -> new FatalBeanException("Could not bind DataSourceSettings properties"));
  }
}
