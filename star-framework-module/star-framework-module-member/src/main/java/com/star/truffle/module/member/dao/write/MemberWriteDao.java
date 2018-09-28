/**create by framework at 2018年09月18日 11:52:25**/
package com.star.truffle.module.member.dao.write;

import java.util.List;
import com.star.truffle.module.member.domain.Member;
import com.star.truffle.module.member.dto.req.MemberRequestDto;
import com.star.truffle.module.member.dto.res.MemberResponseDto;

public interface MemberWriteDao {

  public int saveMember(Member member);

  public int batchSaveMember(List<Member> members);

  public int updateMember(MemberRequestDto memberDto);

  public int deleteMember(Long memberId);

  public MemberResponseDto getMember(Long memberId);

}