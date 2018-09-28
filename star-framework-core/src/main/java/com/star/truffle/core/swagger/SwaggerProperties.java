/**create by liuhua at 2018年6月8日 下午3:16:16**/
package com.star.truffle.core.swagger;

import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import lombok.Data;

@Data
@Validated
@ConfigurationProperties(prefix = "star-swagger")
public class SwaggerProperties {

  private String title = "star software restful apis";
  private String description = "star software API";
  private String version = "v1";
  @NotNull
  private String basePackage;
}
