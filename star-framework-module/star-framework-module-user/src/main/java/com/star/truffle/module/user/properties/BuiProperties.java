/**create by liuhua at 2018年7月30日 下午2:33:20**/
package com.star.truffle.module.user.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "bui")
public class BuiProperties {

  private String systemTitle;     //系统标题
}
