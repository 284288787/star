/**create by liuhua at 2018年9月20日 上午9:33:37**/
package com.star.truffle.module.alibaba.properties;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PushInfo {

  private String templateCode;     //阿里短信模板编号
  private String templateParam;    //阿里短信模板参数格式json
  
  public String fillTemplateParam(Object ... params) {
    String param = this.templateParam;
    if (StringUtils.isNotBlank(param)) {
      param = String.format(param, params);
    }
    return param;
  }
  
}
