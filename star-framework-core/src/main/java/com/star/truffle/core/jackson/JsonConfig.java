/**create by liuhua at 2018年6月5日 下午2:22:33**/
package com.star.truffle.core.jackson;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ConditionalOnBean(ObjectMapper.class)
@AutoConfigureAfter(name = "org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration")
public class JsonConfig {
  
  @Bean
  public StarJson json(ObjectMapper objectMapper) {
    return new StarJson(objectMapper);
  }
}
