/**create by liuhua at 2018年9月19日 下午5:20:20**/
package com.star.truffle.module.alibaba.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.star.truffle.module.alibaba.dao.write.SmsIdentityDao;
import com.star.truffle.module.alibaba.domain.SmsIdentity;

@Service
public class SmsIdentityCache {

  @Autowired
  private SmsIdentityDao smsIdentityDao;
  
  @CachePut(value = "smsidentity_info", key = "'smsidentity_info_mobile_'+#smsIdentity.mobile+'_tag_'+#smsIdentity.tag")
  public SmsIdentity saveSmsIdentity(SmsIdentity smsIdentity) {
    this.smsIdentityDao.saveSmsIdentity(smsIdentity);
    return smsIdentity;
  }
  
  @Cacheable(value = "smsidentity_info", key = "'smsidentity_info_mobile_'+#mobile+'_tag_'+#tag", condition = "#mobile != null and #tag != null")
  public SmsIdentity getSmsIdentity(String mobile, Integer tag) {
    return this.smsIdentityDao.getSmsIdentity(mobile, tag);
  }
  
  @Cacheable(value = "smsidentity_info", key = "'smsidentity_info_mobile_'+#mobile+'_tag_'+#tag", condition = "#mobile != null and #tag != null")
  public void deleteSmsIdentity(String mobile, Integer tag) {
    this.smsIdentityDao.deleteSmsIdentity(mobile, tag);
  }
}
