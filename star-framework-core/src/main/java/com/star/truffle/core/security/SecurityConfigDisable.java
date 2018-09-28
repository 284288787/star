/** create by liuhua at 2017年5月19日 上午11:51:49 **/
package com.star.truffle.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigDisable extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder(){
    PasswordEncoder encode = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    return encode;
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().headers().frameOptions().sameOrigin().and().authorizeRequests().anyRequest().permitAll();
  }

}
