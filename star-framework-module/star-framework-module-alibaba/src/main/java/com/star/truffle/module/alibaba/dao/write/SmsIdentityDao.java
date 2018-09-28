/**create by liuhua at 2018年9月19日 下午5:18:24**/
package com.star.truffle.module.alibaba.dao.write;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.alibaba.domain.SmsIdentity;

public interface SmsIdentityDao {

  public int saveSmsIdentity(SmsIdentity smsIdentity);
  
  public SmsIdentity getSmsIdentity(@Param("mobile") String mobile, @Param("tag") Integer tag);
  
  public int deleteSmsIdentity(@Param("mobile") String mobile, @Param("tag") Integer tag);
}
