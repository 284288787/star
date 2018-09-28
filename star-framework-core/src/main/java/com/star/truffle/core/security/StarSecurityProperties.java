/**create by liuhua at 2018年7月12日 下午5:16:36**/
package com.star.truffle.core.security;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "star-security")
public class StarSecurityProperties {

  private List<String> ignore;
}
