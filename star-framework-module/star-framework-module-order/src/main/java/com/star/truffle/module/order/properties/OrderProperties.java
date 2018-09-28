/**create by liuhua at 2018年9月22日 上午10:29:10**/
package com.star.truffle.module.order.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "order")
public class OrderProperties {

  private Integer despatchMoney = 800;
}
