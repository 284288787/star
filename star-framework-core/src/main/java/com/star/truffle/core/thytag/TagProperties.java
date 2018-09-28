/**create by liuhua at 2018年8月6日 下午5:04:52**/
package com.star.truffle.core.thytag;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "star-thymeleaf-tag")
public class TagProperties {

  private boolean usePageAuth = true;
}
