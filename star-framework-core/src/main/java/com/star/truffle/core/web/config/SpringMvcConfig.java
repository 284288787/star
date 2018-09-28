package com.star.truffle.core.web.config;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.star.truffle.core.jackson.JsonConfig;
import com.star.truffle.core.jackson.StarJson;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnBean(StarJson.class)
@AutoConfigureAfter({ JsonConfig.class, SpringContextConfig.class, MvcInterceptorsConfig.class })
@EnableConfigurationProperties(StarSpringMvcProperties.class)
public class SpringMvcConfig {

//  @Bean
//  public MultipartConfigElement multipartConfigElement(StarSpringMvcProperties properties) {
//    MultipartConfigFactory factory = new MultipartConfigFactory();
//    // 单个文件最大
//    factory.setMaxFileSize(StringUtils.isBlank(properties.getMaxFileSize()) ? "8MB": properties.getMaxFileSize()); // KB,MB
//    /// 设置总上传数据总大小
//    factory.setMaxRequestSize(StringUtils.isBlank(properties.getMaxRequestSize()) ? "80MB": properties.getMaxRequestSize());
//    return factory.createMultipartConfig();
//  }

  private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    return corsConfiguration;
  }

  @Bean
  @ConditionalOnClass(CorsFilter.class)
  @ConditionalOnProperty(value = "starWeb.cors.enabled", matchIfMissing = true)
  public Filter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", buildConfig());
    return new CorsFilter(source);
  }

  // @Bean
  // @ConditionalOnClass(OncePerRequestFilter.class)
  // @ConditionalOnProperty(value = "hnWeb.log.enabled", matchIfMissing = true)
  // public Filter accessLogFilter(HnJson hnJson, HnSpringMvcProperties
  // hnSpringMvcProperties) {
  // return AccessLogFilter.of(hnJson, hnSpringMvcProperties);
  // }
}
