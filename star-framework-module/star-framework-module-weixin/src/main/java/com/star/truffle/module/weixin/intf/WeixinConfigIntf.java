package com.star.truffle.module.weixin.intf;

public abstract class WeixinConfigIntf {

  public abstract String getAppId();

  public abstract String getAppSecret();

  public abstract String getKey();

  public abstract String getMchId();

  public abstract String getNotifyUrl();

  public abstract String getServerIp();

  public abstract String getTradeType();
}
