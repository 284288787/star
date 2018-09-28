/** create by liuhua at 2018年6月8日 下午3:38:05 **/
package com.star.truffle.core.swagger;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.star.truffle.core.ConditionalOnMapProperty;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@ConditionalOnClass(Docket.class)
@ConditionalOnMapProperty(prefix = "star-swagger")
@ConditionalOnProperty(value = "star-swagger.enable", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {

  @Bean
  @DependsOn("starSwaggerApiInfo")
  public Docket createRestApi(SwaggerProperties swaggerProperties, ApiInfo apiInfo) {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo)
        .select()
        .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())) // 扫描API的包路径
        .paths(PathSelectors.ant("/api/**"))
        .build().apiDescriptionOrdering(new Ordering<ApiDescription>() {
          @Override
          public int compare(ApiDescription left, ApiDescription right) {
              int leftPos = left.getOperations().size() == 1 ? left.getOperations().get(0).getPosition() : 0;
              int rightPos = right.getOperations().size() == 1 ? right.getOperations().get(0).getPosition() : 0;

              int position = Integer.compare(leftPos, rightPos);

              if(position == 0) {
                  position = left.getPath().compareTo(right.getPath());
              }

              return position;
          }
        })
        .securityContexts(Lists.newArrayList(securityContext()))
        .securitySchemes(Lists.newArrayList(apiKey()));
  }

  @Bean("starSwaggerApiInfo")
  public ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
    return new ApiInfoBuilder().title(swaggerProperties.getTitle()) // 标题
        .description(swaggerProperties.getDescription()) // 描述
        .termsOfServiceUrl("") // 网址
        .version(swaggerProperties.getVersion()) // 版本号
        .build();
  }


  private ApiKey apiKey() {
    return new ApiKey("AUTHORIZATION", "Authorization", "header");
  }


  @Bean
  SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder().scopeSeparator(",").useBasicAuthenticationWithAccessCodeGrant(true).build();
//    return new SecurityConfiguration(null, null, null, null, "", ApiKeyVehicle.HEADER, "Authorization", ",");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/api.*")).build();
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(new SecurityReference("AUTHORIZATION", authorizationScopes));
  }

}
