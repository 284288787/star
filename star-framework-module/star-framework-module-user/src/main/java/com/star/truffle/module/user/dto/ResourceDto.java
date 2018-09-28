/**create by liuhua at 2018年7月14日 上午10:10:22**/
package com.star.truffle.module.user.dto;

import org.apache.commons.lang3.StringUtils;
import com.star.truffle.core.jdbc.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceDto extends Page{

  private Long sourceId;             //资源id
  private String sourceName;         //资源名称
  private String sourceIcoCls;       //菜单图标class
  private Long parentId;             //父
  private Integer enabled;           //是否可用 1可用 0禁用
  private Integer idx;
  private Integer childNum;
  private Integer roleNum;
  private String idxstr;             //2_1 表示在原有顺序为2的前面
  
  private Long roleId;
  
  public boolean checkSaveData() {
    if (null != sourceId || StringUtils.isBlank(sourceName) || null == parentId || StringUtils.isBlank(idxstr)) {
      return false;
    }
    return true;
  }
}
