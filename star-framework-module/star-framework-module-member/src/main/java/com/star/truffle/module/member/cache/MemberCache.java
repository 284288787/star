/**create by framework at 2018年09月18日 11:52:25**/
package com.star.truffle.module.member.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.member.dao.read.MemberReadDao;
import com.star.truffle.module.member.dao.write.MemberWriteDao;
import com.star.truffle.module.member.domain.Member;
import com.star.truffle.module.member.dto.req.MemberRequestDto;
import com.star.truffle.module.member.dto.res.MemberResponseDto;

@Service
public class MemberCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private MemberWriteDao memberWriteDao;
  @Autowired
  private MemberReadDao memberReadDao;

  @Caching(
    put = {
      @CachePut(value = "module-member-member", key = "'member_memberId_'+#result.memberId", condition = "#result != null and #result.memberId != null"),
      @CachePut(value = "module-member-member", key = "'member_openId_'+#result.openId", condition = "#result != null and #result.openId != null"),
    }
  )
  public MemberResponseDto saveMember(Member member){
    this.memberWriteDao.saveMember(member);
    MemberResponseDto memberResponseDto = this.memberWriteDao.getMember(member.getMemberId());
    return memberResponseDto;
  }

  @Caching(
    put = {
      @CachePut(value = "module-member-member", key = "'member_memberId_'+#result.memberId", condition = "#result != null and #result.memberId != null"),
      @CachePut(value = "module-member-member", key = "'member_openId_'+#result.openId", condition = "#result != null and #result.openId != null"),
    }
  )
  public MemberResponseDto updateMember(MemberRequestDto memberRequestDto){
    this.memberWriteDao.updateMember(memberRequestDto);
    MemberResponseDto memberResponseDto = this.memberWriteDao.getMember(memberRequestDto.getMemberId());
    return memberResponseDto;
  }

  @Caching(
    evict = {
      @CacheEvict(value = "module-member-member", key = "'member_memberId_'+#memberId", condition = "#memberId != null"),
      @CacheEvict(value = "module-member-member", key = "'member_openId_'+#openId", condition = "#openId != null"),
    }
  )
  public int deleteMember(Long memberId, String openId){
    return this.memberWriteDao.deleteMember(memberId);
  }

  @Cacheable(value = "module-member-member", key = "'member_memberId_'+#memberId", condition = "#memberId != null")
  public MemberResponseDto getMember(Long memberId){
    MemberResponseDto memberResponseDto = this.memberReadDao.getMember(memberId);
    return memberResponseDto;
  }

  @Cacheable(value = "module-member-member", key = "'member_openId_'+#openId", condition = "#openId != null")
  public MemberResponseDto getMemberByOpenId(String openId) {
    MemberResponseDto memberResponseDto = this.memberReadDao.getMemberByOpenId(openId);
    return memberResponseDto;
  }
  
  public List<MemberResponseDto> queryMember(MemberRequestDto memberRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(memberRequestDto);
    return this.memberReadDao.queryMember(conditions);
  }

  public Long queryMemberCount(MemberRequestDto memberRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(memberRequestDto);
    return this.memberReadDao.queryMemberCount(conditions);
  }
}