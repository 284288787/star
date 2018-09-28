/**create by framework at 2018年09月18日 11:52:25**/
package com.star.truffle.module.member.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.member.dto.res.MemberResponseDto;

public interface MemberReadDao {

  public MemberResponseDto getMember(Long memberId);

  public List<MemberResponseDto> queryMember(Map<String, Object> conditions);

  public Long queryMemberCount(Map<String, Object> conditions);

  public MemberResponseDto getMemberByOpenId(String openId);

}