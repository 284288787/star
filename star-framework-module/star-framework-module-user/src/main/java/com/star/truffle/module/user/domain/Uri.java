/**create by liuhua at 2018年7月22日 下午3:32:32**/
package com.star.truffle.module.user.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Uri {

  private String uri;
  private Long sourceId;
  private String intro;
  private Date createTime;

  public Uri() {
  }
  
  public Uri(String path) {
    this.uri = path;
  }
}
