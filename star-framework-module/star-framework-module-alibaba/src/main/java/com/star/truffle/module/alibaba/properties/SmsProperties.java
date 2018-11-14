/**create by liuhua at 2018年9月20日 上午9:38:56**/
package com.star.truffle.module.alibaba.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "template")
public class SmsProperties {

  private String signName;
  private Map<Integer, PushInfo> sms;
}
