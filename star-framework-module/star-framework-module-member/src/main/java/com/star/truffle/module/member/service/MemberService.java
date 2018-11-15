/**create by framework at 2018年09月18日 11:52:25**/
package com.star.truffle.module.member.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.alibaba.service.SmsIdentityService;
import com.star.truffle.module.member.cache.MemberCache;
import com.star.truffle.module.member.constant.LoginStateEnum;
import com.star.truffle.module.member.dto.req.MemberRequestDto;
import com.star.truffle.module.member.dto.res.MemberResponseDto;

@Service
public class MemberService {

  @Autowired
  private MemberCache memberCache;
  @Autowired
  private SmsIdentityService smsIdentityService;

  public Long saveMember(MemberRequestDto member) {
    if (null == member || member.checkSaveData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    member.setCreateTime(new Date());
    member.setState(LoginStateEnum.login.getState());
    this.memberCache.saveMember(member);
    return member.getMemberId();
  }
  
  public MemberResponseDto login(MemberRequestDto member) {
    if (null == member || StringUtils.isBlank(member.getMobile()) || StringUtils.isBlank(member.getCode()) || null == member.getTag()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    smsIdentityService.verify(member.getMobile(), member.getTag(), member.getCode());
    MemberResponseDto memberResponseDto = this.memberCache.getMemberByOpenId(member.getOpenId());
    if (null == memberResponseDto) {
      if (! member.checkSaveData()) {
        throw new StarServiceException(ApiCode.PARAM_ERROR);
      }
      member.setCreateTime(new Date());
      member.setState(LoginStateEnum.login.getState());
      this.memberCache.saveMember(member);
      memberResponseDto = this.memberCache.getMemberByOpenId(member.getOpenId());
    } else {
      memberResponseDto.setState(LoginStateEnum.login.getState());
      MemberRequestDto memberRequestDto = new MemberRequestDto();
      memberRequestDto.setMemberId(memberResponseDto.getMemberId());
      memberRequestDto.setState(LoginStateEnum.login.getState());
      if (! memberResponseDto.getMobile().equals(member.getMobile())) {
        memberRequestDto.setMobile(member.getMobile());
      }
      this.memberCache.updateMember(memberRequestDto);
    }
    smsIdentityService.deleteSmsIdentity(member.getMobile(), member.getTag());
    return memberResponseDto;
  }

  public void updateMember(MemberRequestDto memberRequestDto) {
    if (null == memberRequestDto || ! memberRequestDto.checkUpdateData()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    this.memberCache.updateMember(memberRequestDto);
  }

  public void deleteMember(Long memberId) {
    MemberResponseDto member = this.memberCache.getMember(memberId);
    if (null == member) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "用户不存在");
    }
    this.memberCache.deleteMember(memberId, member.getOpenId());
  }

  public void deleteMember(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] memberIds = idstr.split(",");
    for (String str : memberIds) {
      Long memberId = Long.parseLong(str);
      MemberResponseDto member = this.memberCache.getMember(memberId);
      if (null != member) {
        this.memberCache.deleteMember(memberId, member.getOpenId());
      }
    }
  }

  public MemberResponseDto getMember(Long memberId) {
    MemberResponseDto memberResponseDto = this.memberCache.getMember(memberId);
    return memberResponseDto;
  }

  public MemberResponseDto getMemberByOpenId(String openId) {
    MemberResponseDto member = this.memberCache.getMemberByOpenId(openId);
    if (null == member) {
      throw new StarServiceException(ApiCode.NO_EXISTS);
    }
    if (member.getState() == LoginStateEnum.logout.getState()) {
      throw new StarServiceException(ApiCode.NO_LOGIN);
    }
    return member;
  }
  
  public List<MemberResponseDto> queryMember(MemberRequestDto memberRequestDto) {
    return this.memberCache.queryMember(memberRequestDto);
  }

  public Long queryMemberCount(MemberRequestDto memberRequestDto) {
    return this.memberCache.queryMemberCount(memberRequestDto);
  }

  public void logout(String openId) {
    MemberResponseDto memberResponseDto = this.memberCache.getMemberByOpenId(openId);
    if (null != memberResponseDto && memberResponseDto.getState() == LoginStateEnum.login.getState()) {
      MemberRequestDto memberRequestDto = new MemberRequestDto();
      memberRequestDto.setMemberId(memberResponseDto.getMemberId());
      memberRequestDto.setState(LoginStateEnum.logout.getState());
      this.memberCache.updateMember(memberRequestDto);
    }
  }
}