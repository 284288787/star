/**create by liuhua at 2018年7月22日 下午3:32:32**/
package com.star.truffle.module.user.dto;

import java.util.Date;
import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UriDto extends Page{

  private String uri;
  private Long sourceId;
  private String intro;
  private Date createTime;
  
  private Long roleId;
  private Integer roleNum;
  private Integer mainUri;
  private String sourceName;
}
