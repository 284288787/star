package com.star.truffle.module.weixin.service;

import org.springframework.stereotype.Service;

import com.star.truffle.module.weixin.intf.WeixinConfigIntf;

@Service("weixinConfig")
public class WeixinConst extends WeixinConfigIntf {

  private String appId = "wx43fd4135600dcee3";
  private String appSecret = "364f293b4715ee0e107450eab8fda8e7";
  private String key = "98iujhrw3f6b87hhko09876421qaxdse";
  private String mchId = "1513883741";
  private String tradeType = "JSAPI";
  private String notifyUrl = "http://47.105.38.227/weixin/pay/callback";
  private String spBillCreateIP = "47.105.38.227";

  @Override
  public String getAppId() {
    return appId;
  }

  @Override
  public String getAppSecret() {
    return appSecret;
  }

  @Override
  public String getMchId() {
    return mchId;
  }

  @Override
  public String getNotifyUrl() {
    return notifyUrl;
  }

  @Override
  public String getServerIp() {
    return spBillCreateIP;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getTradeType() {
    return tradeType;
  }
}
