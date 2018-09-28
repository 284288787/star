/**create by framework at 2018年09月18日 11:52:25**/
package com.star.truffle.module.member.dto.req;

import org.apache.commons.lang3.StringUtils;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.member.domain.Member;

@Getter
@Setter
public class MemberRequestDto extends Member {

  private Page pager;
  
  private String code;
  private Integer tag;
  
  public boolean checkSaveData() {
    if (null != getMemberId() || StringUtils.isBlank(getHead()) || StringUtils.isBlank(getName())
        || StringUtils.isBlank(getMobile()) || StringUtils.isBlank(getOpenId()) || null == tag
        || StringUtils.isBlank(code)) {
      return false;
    }
    return true;
  }
  
  public boolean checkUpdateData() {
    if (null == getMemberId() || StringUtils.isBlank(getHead()) || StringUtils.isBlank(getName())
        || StringUtils.isBlank(getMobile()) || StringUtils.isBlank(getOpenId())) {
      return false;
    }
    return true;
  }
}