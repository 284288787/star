package com.star.truffle.core.util;

import org.springframework.beans.FatalBeanException;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnvironmentUtil {

  // 读取配置并转换成对象
  public static <T> T resolverSetting(Class<T> clazz, String targetName,
      ConfigurableEnvironment environment) {
    try {
      return Binder.get(environment)
          .bind(targetName, Bindable.of(clazz))
          .orElseThrow(
              () -> new FatalBeanException("Could not bind properties"));
    } catch (Exception e) {
      log.error("Could not bind properties: " + e.getMessage(), e);
      throw new FatalBeanException("Could not bind properties", e);
    }
  }
}
