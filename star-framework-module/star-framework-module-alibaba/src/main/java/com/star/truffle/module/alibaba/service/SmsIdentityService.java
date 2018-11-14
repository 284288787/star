/**create by liuhua at 2018年9月19日 下午5:32:55**/
package com.star.truffle.module.alibaba.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.alibaba.cache.SmsIdentityCache;
import com.star.truffle.module.alibaba.domain.SmsIdentity;
import com.star.truffle.module.alibaba.properties.PushInfo;
import com.star.truffle.module.alibaba.properties.SmsProperties;

@Service
public class SmsIdentityService {

  @Autowired
  private SmsIdentityCache smsIdentityCache;
  @Autowired
  private SmsProperties smsProperties;

  public void send(String mobile, Integer tag) {
    if (StringUtils.isBlank(mobile) || null == tag) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    SmsIdentity smsIdentity = this.smsIdentityCache.getSmsIdentity(mobile, tag);
    if (null != smsIdentity) {
      if (smsIdentity.isValid()) {
        throw new StarServiceException(ApiCode.SMS_SENDED);
      }
      this.smsIdentityCache.deleteSmsIdentity(mobile, tag);
    }
    // 调用接口发短信
    PushInfo pushInfo = smsProperties.getSms().get(tag);
//    String code = "1234"; //SmsUtil.sendSms(mobile, pushInfo);
    String code = SmsUtil.sendSms(smsProperties.getSignName(), mobile, pushInfo);
    SmsIdentity entity = new SmsIdentity();
    entity.setCode(code);
    entity.setContent(code + "为本次短信验证码，清及时使用！");
    entity.setCreateTime(new Date());
    entity.setMobile(mobile);
    entity.setTag(tag);
    smsIdentityCache.saveSmsIdentity(entity);
  }

  public void verify(String mobile, Integer tag, String code) {
    if (StringUtils.isBlank(mobile) || null == tag || StringUtils.isBlank(code)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    SmsIdentity smsIdentity = this.smsIdentityCache.getSmsIdentity(mobile, tag);
    if (null == smsIdentity/* || ! smsIdentity.isValid() */ || ! code.equals(smsIdentity.getCode())) {
      throw new StarServiceException(ApiCode.SMS_ERROR);
    }
  }

  public void deleteSmsIdentity(String mobile, Integer tag) {
    this.smsIdentityCache.deleteSmsIdentity(mobile, tag);
  }
}
