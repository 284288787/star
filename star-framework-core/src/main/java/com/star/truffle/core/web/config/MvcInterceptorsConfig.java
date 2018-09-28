package com.star.truffle.core.web.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class MvcInterceptorsConfig implements WebMvcConfigurer {

  @Autowired
  private StarSpringMvcProperties starSpringMvcProperties;
  
  ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    VersionResourceResolver versionResourceResolver = new VersionResourceResolver().addVersionStrategy(new ContentVersionStrategy(), "/**");
    
    registry
    .addResourceHandler("/**", "/bui/**", "/webjars/**", "/swagger-ui.html", "/photo/**")
    .addResourceLocations("classpath:/static/", "classpath:/static/bui/", "classpath:/META-INF/resources/webjars/", "/swagger-ui.html", "file:" + starSpringMvcProperties.getPhotoPath())
    .setCachePeriod(60 * 60 * 24 * 365) //一年
    .resourceChain(true)
    .addResolver(versionResourceResolver);
    
  }

  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    lci.setParamName("lang");
    return lci;
  }

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver slr = new SessionLocaleResolver();
    slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
    return slr;
  }

  @Bean("messageSource")
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    List<String> list = new ArrayList<>();
    try {
      Resource[] resources = resourceResolver.getResources("classpath*:/static/message/**");
      for (Resource resource : resources) {
        String path = resource.getURL().getPath();
        Pattern pattern = Pattern.compile("/([^/_])*\\.", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
          String item = "static/message" + matcher.group(0).replace(".", "");
          list.add(item);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.addBasenames(list.toArray(new String[0]));
    return messageSource;
  }
}
