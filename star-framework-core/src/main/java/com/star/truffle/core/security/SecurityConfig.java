/** create by liuhua at 2017年5月19日 上午11:51:49 **/
package com.star.truffle.core.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.star.truffle.core.ConditionalOnMapProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@ConditionalOnMapProperty(value = StarSecurityProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(StarSecurityProperties.class)
@Order(100)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private StarSecurityProperties securityProperties;
  
//  @Bean(name = "securtityUserServiceDetails")
//  public SecurtityUserServiceDetails securtityUserServiceDetails() {
//    return new SecurtityUserServiceDetails();
//  }

//  @Bean(name = "starFilterInvocationSecurityMetadataSource")
//  public SourceMap getSourceMap() {
//    return new SourceMap();
//  }

  @Bean(name = "starAuthenticationProvider")
  public StarAuthenticationProvider starAuthenticationProvider() {
    return new StarAuthenticationProvider();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    PasswordEncoder encode = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    return encode;
  }
  
//  /*
//   * 
//   * 这里可以增加自定义的投票器
//   */
//  @Bean(name = "starAccessDecisionManager")
//  public AccessDecisionManager accessDecisionManager() {
//    List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
//    decisionVoters.add(new RoleVoter());
//    decisionVoters.add(new AuthenticatedVoter());
//    decisionVoters.add(webExpressionVoter());// 启用表达式投票器
//    StarAccessDecisionManager accessDecisionManager = new StarAccessDecisionManager(decisionVoters);
//    return accessDecisionManager;
//  }

  public AffirmativeBased accessDecisionManager2() {
    List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
    decisionVoters.add(new RoleVoter());
    decisionVoters.add(new AuthenticatedVoter());
    decisionVoters.add(webExpressionVoter());// 启用表达式投票器
    AffirmativeBased accessDecisionManager = new AffirmativeBased(decisionVoters);
    return accessDecisionManager;
  }

  /*
   * 表达式控制器
   */
  @Bean(name = "expressionHandler")
  public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
    DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
    return webSecurityExpressionHandler;
  }

  /*
   * 表达式投票器
   */
  @Bean(name = "expressionVoter")
  public WebExpressionVoter webExpressionVoter() {
    WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
    webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
    return webExpressionVoter;
  }

  public StarAuthenticationEntryPoint starAuthenticationEntryPoint(String url) {
    StarAuthenticationEntryPoint successHandler = new StarAuthenticationEntryPoint(url);
    return successHandler;
  }
  
  public StarAccessDeniedHandler starAccessDeniedHandler() {
    StarAccessDeniedHandler successHandler = new StarAccessDeniedHandler(null);
    return successHandler;
  }

  @Bean(name = "starSuccessHandler")
  public StarSuccessHandler starSuccessHandler() {
    StarSuccessHandler successHandler = new StarSuccessHandler();
    return successHandler;
  }

  public StarFailureHandler starFailureHandler() {
    StarFailureHandler failureHandler = new StarFailureHandler();
    return failureHandler;
  }
  
  public StarLoginFilter starLoginFilter(ApplicationContext applicationContext) throws Exception {
    AuthenticationSuccessHandler successHandler = applicationContext.getBean("starSuccessHandler", AuthenticationSuccessHandler.class);
    StarLoginFilter filter = new StarLoginFilter();
    filter.setAuthenticationSuccessHandler(successHandler);
    filter.setAuthenticationFailureHandler(starFailureHandler());
    filter.setFilterProcessesUrl("/api/login");
    
    // 这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;
  }

  public StarAuthenticationFilter starAuthenticationFilter() throws Exception {
    StarAuthenticationFilter filter = new StarAuthenticationFilter();
    return filter;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // 设置不拦截规则
    // web.ignoring().antMatchers("/static/**", "/swagger/**", "/**/*.jsp", "/bui/*.css",
    // "/bui/*.js", "/bui/**/*.css", "/bui/**/*.js");
    // web.ignoring().antMatchers("/swagger-ui.html", "/webjars/**", "/common/**", "/v2/api-docs",
    // "/favicon.ico");
    
//    StarSecurityProperties mybatisProperties = new Binder(ConfigurationPropertySources.from(this.environment.getPropertySources())).bind("", Bindable.of(StarSecurityProperties.class)).orElse(new StarSecurityProperties());
    List<String> uri = securityProperties.getIgnore();
    if (null == uri) {
      uri = new ArrayList<>();
    }
    uri.addAll(Arrays.asList("/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/v2/api-docs", "/favicon.ico", "/accessDenied", "/css/**", "/js/**", "/images/**", "/photo/**"));
    log.info("00000000000000000 " + uri.size());
    web.ignoring().antMatchers(uri.toArray(new String[0]));
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    StarFilterInvocationSecurityMetadataSource sourceMap = getApplicationContext().getBean("starFilterInvocationSecurityMetadataSource", StarFilterInvocationSecurityMetadataSource.class);
    
    http.anonymous().disable().authorizeRequests()
    .anyRequest().fullyAuthenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
      public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
        fsi.setSecurityMetadataSource(sourceMap);
        fsi.setAccessDecisionManager(accessDecisionManager2());
        try {
          fsi.setAuthenticationManager(authenticationManagerBean());
        } catch (Exception e) {
          e.printStackTrace();
        }
        return fsi;
      }
    }).and().headers().frameOptions().sameOrigin() // 允许来自同一来源的请求
//    .and().csrf().ignoringAntMatchers("/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/v2/api-docs").and() // 禁用index 的 CSRF 防护 登出需要post提交
    .and().csrf().disable()
    .formLogin().loginPage("/login")
    .loginProcessingUrl("/j_spring_security_check").usernameParameter("j_username").passwordParameter("j_password").permitAll()//.defaultSuccessUrl("/index",
    .failureHandler(new RestAuthenticationFailureHandler("/login?error=true"))
    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
    .and()
    .addFilterBefore(starLoginFilter(getApplicationContext()), UsernamePasswordAuthenticationFilter.class)
    .addFilterBefore(starAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
    .exceptionHandling().accessDeniedHandler(starAccessDeniedHandler())//.accessDeniedPage("/accessDenied")
    .authenticationEntryPoint(starAuthenticationEntryPoint("/login"))

    // session管理
    .and().sessionManagement().sessionFixation().changeSessionId().maximumSessions(1).expiredUrl("/");
    // RemeberMe
    http.rememberMe().rememberMeParameter("remember-me").rememberMeCookieName("remember-me").key("webmvc#FD637E6D9C0F1A5A67082AF56CE32485");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    super.configure(auth);
    // 设置内存用户角色
    // auth.inMemoryAuthentication().withUser("user").password("123456")
    // .roles("USER").and().withUser("admin").password("654321")
    // .roles("USER", "ADMIN");

    // 自定义UserDetailsService
    UserDetailsService userDetailsService = getApplicationContext().getBean("securtityUserServiceDetails", UserDetailsService.class);
    PasswordEncoder passwordEncoder = getApplicationContext().getBean("passwordEncoder", PasswordEncoder.class);
    StarAuthenticationProvider starAuthenticationProvider = starAuthenticationProvider();
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    auth.authenticationProvider(starAuthenticationProvider);

  }
}
