package com.star.truffle.module.weixin.dto.req;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.star.truffle.module.weixin.util.Signature;

/**
 * 提交的数据
 */
public class PayReqData {

  private String appid;
  private String mch_id;
  private String nonce_str;
  private String sign;
  private String notify_url;
  private String trade_type;

  private String body;
  private String attach;
  private String out_trade_no;
  private int total_fee = 0;
  private String spbill_create_ip;
  private String time_start;
  private String openid;

  /**
   * @param body
   *          商品信息
   * @param attach
   *          附加信息
   * @param outTradeNo
   *          订单号
   * @param totalFee
   *          金额 分
   * @param spBillCreateIP
   *          客户ip
   * @param timeStart
   *          交易起始时间
   */
  public PayReqData(String body, String attach, String outTradeNo, int totalFee, Date timeStart, String appId, String key, String mchId, String url, String ip, String tradeType, String openId) {
    // 微信分配的公众号ID（开通公众号之后可以获取到）
    setAppid(appId);

    // 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    setMch_id(mchId);

    setNotify_url(url);

    setTrade_type(tradeType);

    // 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
    setBody(body);

    // 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回，有助于商户自己可以注明该笔消费的具体内容，方便后续的运营和记录
    setAttach(attach);

    // 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
    setOut_trade_no(outTradeNo);

    // 订单总金额，单位为“分”，只能整数
    setTotal_fee(totalFee);

    // 订单生成的机器IP
    setSpbill_create_ip(ip);

    // 订单生成时间， 格式为yyyyMMddHHmmss，如2009年12 月25 日9 点10 分10
    // 秒表示为20091225091010。时区为GMT+8 beijing。该时间取自商户服务器
    setTime_start(timeStart);

    // 随机字符串，不长于32 位
    setNonce_str(UUID.randomUUID().toString().replace("-", ""));

    // 根据API给的签名规则进行签名
    setOpenid(openId);
    String sign = Signature.getSign(toMap(), key);
    setSign(sign);// 把签名数据设置到Sign这个属性中

  }

  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getMch_id() {
    return mch_id;
  }

  public void setMch_id(String mch_id) {
    this.mch_id = mch_id;
  }

  public String getNonce_str() {
    return nonce_str;
  }

  public void setNonce_str(String nonce_str) {
    this.nonce_str = nonce_str;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getAttach() {
    return attach;
  }

  public void setAttach(String attach) {
    this.attach = attach;
  }

  public String getOut_trade_no() {
    return out_trade_no;
  }

  public void setOut_trade_no(String out_trade_no) {
    this.out_trade_no = out_trade_no;
  }

  public int getTotal_fee() {
    return total_fee;
  }

  public void setTotal_fee(int total_fee) {
    this.total_fee = total_fee;
  }

  public String getSpbill_create_ip() {
    return spbill_create_ip;
  }

  public void setSpbill_create_ip(String spbill_create_ip) {
    this.spbill_create_ip = spbill_create_ip;
  }

  public String getTime_start() {
    return time_start;
  }

  public void setTime_start(Date timeStart) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    this.time_start = sdf.format(timeStart);
  }

  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<String, Object>();
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field field : fields) {
      Object obj;
      try {
        obj = field.get(this);
        if (obj != null) {
          map.put(field.getName(), obj);
        }
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return map;
  }

  public String getNotify_url() {
    return notify_url;
  }

  public void setNotify_url(String notify_url) {
    this.notify_url = notify_url;
  }

  public String getTrade_type() {
    return trade_type;
  }

  public void setTrade_type(String trade_type) {
    this.trade_type = trade_type;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

}
