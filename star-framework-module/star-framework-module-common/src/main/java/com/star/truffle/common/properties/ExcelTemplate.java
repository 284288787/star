/**create by liuhua at 2018年8月15日 上午9:58:08**/
package com.star.truffle.common.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "template")
public class ExcelTemplate {

  private Map<String, Excel> excel;
}
