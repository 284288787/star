/**create by liuhua at 2017年12月7日 下午1:50:01**/
package com.star.truffle.module.weixin.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.okhttp.StarOkHttpClient;
import com.star.truffle.core.util.DateUtils;
import com.star.truffle.module.weixin.domain.CardList;
import com.star.truffle.module.weixin.domain.WeixinConfig;
import com.star.truffle.module.weixin.dto.req.PayReqData;
import com.star.truffle.module.weixin.dto.res.PayResData;
import com.star.truffle.module.weixin.dto.res.WeixinUserInfo;
import com.star.truffle.module.weixin.intf.WeixinConfigIntf;
import com.star.truffle.module.weixin.service.WeixinConst;
import com.star.truffle.module.weixin.util.HttpsRequest;
import com.star.truffle.module.weixin.util.Signature;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeiXinApiDao {

  public static void main(String[] args) throws UnsupportedEncodingException {
    // getCardList("ozT4zw5HIGCokurpgLIlR3ItrNWE");
    // modifystock("pzT4zw4L3dnxvatggCwa0M8FdHIY", 10);
    // Map<String, Object> map = getCardDetail("pzT4zw7dU_LVes-WuJ0s5t8BJd6E");
    // System.out.println(map);
    // System.out.println(map.get("cardType"));
    // String[] keys = (String[]) map.get("keys");
    // System.out.println(keys);
    // for (String key : keys) {
    // System.out.println(key + "\t" + map.get(key));
    // }
    // System.out.println("11->" + canConsume("999869921918",
    // Arrays.asList("pzT4zw7dU_LVes-WuJ0s5t8BJd6E")));
    String url = "http://yx.jytzn.com/wx.html";
    String newUrl = URLEncoder.encode(url, "UTF-8");
    WeixinConst weixinConst = new WeixinConst();
    String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + weixinConst.getAppId() + "&redirect_uri=" + newUrl + "&response_type=code&scope=snsapi_userinfo&state=yx#wechat_redirect";
    System.out.println(redirectUrl);
  }

//  public static final String weixinConst.getAppId() = "wx43fd4135600dcee3";
//  public static final String weixinConst.getAppSecret() = "364f293b4715ee0e107450eab8fda8e7";
//  public static final String key = "98iujhrw3f6b87hhko09876421qaxdse";
//  public static final String mchId = "1513883741";
//  public static final String tradeType = "JSAPI";
//  public static final String notifyUrl = "http://47.105.38.227/api/weixin/pay/callback";
//  public static final String spBillCreateIP = "47.105.38.227";

  private final String accessTokenUrlByCode = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
  private final String refreshAccessTokenUrlByCode = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

  private static Map<String, WeixinUserInfo> accessTokens = new HashMap<>();

  private final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
  private final String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
  private final String apiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=wx_card";
  private final String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
  private final String getUserInfoUrl2 = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

  private final String getCardListUrl = "https://api.weixin.qq.com/card/user/getcardlist?access_token=%s";
  private final String modifystockUrl = "https://api.weixin.qq.com/card/modifystock?access_token=%s";
  private final String getCardDetailUrl = "https://api.weixin.qq.com/card/get?access_token=%s";
  private final String checkCardUrl = "https://api.weixin.qq.com/card/code/get?access_token=%s";
  private final String consumeCardUrl = "https://api.weixin.qq.com/card/code/consume?access_token=%s";

  // 统一下单 地址
  private static String unifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

  private AccessToken accessToken = null;
  private JsapiTicket jsapiTicket = null;
  private ApiTicket apiTicket = null;

  @Autowired
  private StarOkHttpClient starOkHttpClient;
  @Autowired
  private StarJson starJson;
  @Autowired
  @Qualifier("weixinConfig")
  private WeixinConfigIntf weixinConfig;
  
  public PayResData unifiedOrderGZH(PayReqData data) {
    try {
      String resXml = new HttpsRequest().sendPost(unifiedOrderUrl, data);
      System.out.println(resXml);
      // String resXml =
      // "<com.star.mami.vo.PayResData><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wxfe0f67d8612b2d5f]]></appid><mch_id><![CDATA[1261895701]]></mch_id><nonce_str><![CDATA[IUYYclitLuf1JOPp]]></nonce_str><sign><![CDATA[5806341F0096E8DFC0EED05CA2646785]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx20151018174522e7ee4e96960029475260]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></com.star.mami.vo.PayResData>";
      resXml = resXml.replace("<xml>", "<com.star.truffle.module.weixin.dto.res.PayResData>").replace("</xml>", "</com.star.truffle.module.weixin.dto.res.PayResData>");
      System.out.println(resXml);
      // 解决XStream对出现双下划线的bug
      XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
      // 将要提交给API的数据对象转换成XML格式数据Post给API
      PayResData obje = (PayResData) xStreamForRequestPostData.fromXML(resXml);
      Date now = DateUtils.toDate(data.getTime_start(), "yyyyMMddHHmmss");
      String noncestr = UUID.randomUUID().toString().replace("-", "");
      Map<String, Object> dd = new HashMap<>();
      dd.put("appId", weixinConfig.getAppId());
      dd.put("signType", "MD5");
      dd.put("package", "prepay_id=" + obje.getPrepay_id());
      dd.put("nonceStr", noncestr);
      dd.put("timeStamp", now.getTime() / 1000);
      data.setTime_start(now);
      obje.setSign(Signature.getSign(dd, weixinConfig.getKey()));
      obje.setNonce_str(noncestr);
      obje.setTimeStamp(now.getTime() / 1000);
      return obje;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public PayResData unifiedOrder(PayReqData data) {
    try {
      String resXml = new HttpsRequest().sendPost(unifiedOrderUrl, data);
      System.out.println(resXml);
      // String resXml =
      // "<com.star.mami.vo.PayResData><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wxfe0f67d8612b2d5f]]></appid><mch_id><![CDATA[1261895701]]></mch_id><nonce_str><![CDATA[IUYYclitLuf1JOPp]]></nonce_str><sign><![CDATA[5806341F0096E8DFC0EED05CA2646785]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx20151018174522e7ee4e96960029475260]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></com.star.mami.vo.PayResData>";
      resXml = resXml.replace("<xml>", "<com.star.truffle.module.weixin.dto.res.PayResData>").replace("</xml>", "</com.star.truffle.module.weixin.dto.res.PayResData>");
      System.out.println(resXml);
      // 解决XStream对出现双下划线的bug
      XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
      // 将要提交给API的数据对象转换成XML格式数据Post给API
      PayResData obje = (PayResData) xStreamForRequestPostData.fromXML(resXml);
      Date now = new Date();
      String noncestr = UUID.randomUUID().toString().replace("-", "");
      Map<String, Object> dd = new HashMap<>();
      dd.put("appid", weixinConfig.getAppId());
      dd.put("partnerid", weixinConfig.getMchId());
      dd.put("prepayid", obje.getPrepay_id());
      dd.put("package", "Sign=WXPay");
      dd.put("noncestr", noncestr);
      dd.put("timestamp", now.getTime() / 1000);
      data.setTime_start(now);
      obje.setSign(Signature.getSign(dd, weixinConfig.getKey()));
      obje.setNonce_str(noncestr);
      obje.setTimeStamp(now.getTime() / 1000);
      return obje;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public WeixinUserInfo getWeixinUserInfo(String code) throws IOException {
    WeixinUserInfo weixinUserInfo = accessTokens.get(code);
    if (null == weixinUserInfo) {
      String temp = starOkHttpClient.get(String.format(accessTokenUrlByCode, weixinConfig.getAppId(), weixinConfig.getAppSecret(), code));
      weixinUserInfo = starJson.str2obj(temp, WeixinUserInfo.class);
      Map<String, Object> info = getWxUserInfo(weixinUserInfo.getAccess_token(), weixinUserInfo.getOpenid());
      weixinUserInfo.setNickname(info.get("nickname").toString());
      weixinUserInfo.setHeadimgurl(info.get("headimgurl").toString());
      accessTokens.put(code, weixinUserInfo);
    } else if (!weixinUserInfo.isExpires()) {
      String temp = starOkHttpClient.get(String.format(refreshAccessTokenUrlByCode, weixinConfig.getAppId(), weixinUserInfo.getRefresh_token()));
      weixinUserInfo = starJson.str2obj(temp, WeixinUserInfo.class);
      Map<String, Object> info = getWxUserInfo(weixinUserInfo.getAccess_token(), weixinUserInfo.getOpenid());
      weixinUserInfo.setNickname(info.get("nickname").toString());
      weixinUserInfo.setHeadimgurl(info.get("headimgurl").toString());
      accessTokens.put(code, weixinUserInfo);
    }
    return weixinUserInfo;
  }

  public synchronized String getAccessToken() throws IOException {
    if (null == accessToken || !accessToken.isExpires()) {
      String res = this.starOkHttpClient.get(String.format(accessTokenUrl, weixinConfig.getAppId(), weixinConfig.getAppSecret()));
      accessToken = starJson.str2obj(res, AccessToken.class);
    }
    return accessToken.getAccess_token();
  }

  public Map<String, Object> getWxUserInfo(String accessToken, String openId) throws IOException {
    String temp = this.starOkHttpClient.get(String.format(getUserInfoUrl2, accessToken, openId));
    Map<String, Object> map = starJson.str2obj(temp, new TypeReference<Map<String, Object>>() {
    });
    Map<String, Object> result = new HashMap<>();
    Object nickname = "";
    Object headimgurl = "";
    if (null != map) {
      nickname = map.get("nickname");
      headimgurl = map.get("headimgurl");
      if (null == nickname) {
        nickname = "";
      }
      if (null == headimgurl) {
        headimgurl = "";
      }
    }
    result.put("nickname", nickname);
    result.put("headimgurl", headimgurl);
    return result;
  }

  /**
   * 公众号内的用户信息
   * 
   * @param openId
   * @return
   * @throws IOException
   */
  public Map<String, Object> getWxUserInfo(String openId) throws IOException {
    String temp = this.starOkHttpClient.get(String.format(getUserInfoUrl, getAccessToken(), openId));
    Map<String, Object> map = starJson.str2obj(temp, new TypeReference<Map<String, Object>>() {
    });
    Map<String, Object> result = new HashMap<>();
    boolean bool = false;
    Object nickname = "";
    Object headimgurl = "";
    if (null != map) {
      Object subscribe = map.get("subscribe");
      nickname = map.get("nickname");
      headimgurl = map.get("headimgurl");
      if (null != subscribe) {
        if (Double.valueOf(subscribe.toString()).intValue() != 0) {
          bool = true;
        }
      }
      if (null == nickname) {
        nickname = "";
      }
      if (null == headimgurl) {
        headimgurl = "";
      }
    }
    result.put("subscribe", bool);
    result.put("nickname", nickname);
    result.put("headimgurl", headimgurl);
    return result;
  }

  /**
   * 是否可以核销
   * 
   * @param code
   * @throws IOException
   */
  public boolean canConsume(String code) throws IOException {
    String temp = this.starOkHttpClient.postJson(String.format(checkCardUrl, getAccessToken()), "{\"code\": \"" + code + "\", \"check_consume\": false}");
    boolean bool = false;
    if (temp.indexOf("card") != -1) {
      Map<String, Object> map = starJson.str2obj(temp, new TypeReference<Map<String, Object>>() {
      });
      bool = Boolean.parseBoolean(map.get("can_consume").toString());
    }
    return bool;
  }

  /**
   * 是否可以核销 return 0 无效的code 1 有效的code，并且cardId没有填 2 有效的code，并且属于cardId 3
   * 有效的code，但不属于cardId
   */
  public String canConsume(String code, List<String> cardIds) throws IOException {
    String temp = this.starOkHttpClient.postJson(String.format(checkCardUrl, getAccessToken()), "{\"code\": \"" + code + "\", \"check_consume\": false}");
    String cardIdRes = "";
    @SuppressWarnings("unused")
    int res = 0;
    if (temp.indexOf("card") != -1) {
      Map<String, Object> map = starJson.str2obj(temp, new TypeReference<Map<String, Object>>() {
      });
      boolean bool = Boolean.parseBoolean(map.get("can_consume").toString());
      if (bool) {
        res = 1;
        if (null != cardIds && !cardIds.isEmpty()) {
          @SuppressWarnings("unchecked")
          Map<String, Object> cardObject = (Map<String, Object>) map.get("card");
          for (String cardId : cardIds) {
            if (StringUtils.isNotBlank(cardId)) {
              if (cardId.equals(cardObject.get("card_id"))) {
                res = 2;
                cardIdRes = cardId;
                break;
              } else {
                res = 3;
              }
            }
          }
        }
      }
    }
    return cardIdRes;
  }

  /**
   * 核销卡券
   * 
   * @param code
   * @throws IOException
   */
  public boolean consumeCard(String code) throws IOException {
    String temp = this.starOkHttpClient.postJson(String.format(consumeCardUrl, getAccessToken()), "{\"code\": \"" + code + "\"}");
    System.out.println(String.format("核销卡券，code：%s，result: %s", code, temp));
    if (temp.indexOf("card") != -1) {
      return true;
    }
    return false;
  }

  /**
   * 所有卡券
   * 
   * @param openId
   * @throws IOException
   */
  public CardList getCardList(String openId) throws IOException {
    String temp = this.starOkHttpClient.postJson(String.format(getCardListUrl, getAccessToken()), "{\"openid\": \"" + openId + "\"}");
    CardList cardList = starJson.str2obj(temp, new TypeReference<CardList>() {
    });
    return cardList;
  }

  /**
   * 卡详情
   * 
   * @param cardId
   * @return
   * @throws IOException
   */
  public Map<String, Object> getCardDetail(String cardId) throws IOException {
    Map<String, Object> res = new HashMap<>();
    String data = "{\"card_id\": \"" + cardId + "\"}";
    String temp = this.starOkHttpClient.postJson(String.format(getCardDetailUrl, getAccessToken()), data);
    if (temp.indexOf("card") != -1) {
      Map<String, Object> map = starJson.str2obj(temp, new TypeReference<Map<String, Object>>() {
      });
      String cardStr = starJson.obj2string(map.get("card"));
      map = starJson.str2obj(cardStr, new TypeReference<Map<String, Object>>() {
      });
      // 团购券：GROUPON; 折扣券：DISCOUNT; 礼品券：GIFT; 代金券：CASH; 通用券：GENERAL_COUPON;
      // 会员卡：MEMBER_CARD;
      // 景点门票：SCENIC_TICKET ；电影票：MOVIE_TICKET； 飞机票：BOARDING_PASS；
      // 会议门票：MEETING_TICKET； 汽车票：BUS_TICKET;
      String cardType = map.get("card_type").toString();
      Object object = map.get(cardType.toLowerCase());
      if (null == object) {
        System.out.println("券详情错误：" + object);
      }
      cardStr = starJson.obj2string(object);
      map = starJson.str2obj(cardStr, new TypeReference<Map<String, Object>>() {
      });
      @SuppressWarnings("unchecked")
      Map<String, Object> obj = (Map<String, Object>) map.get("base_info");
      res.put("cardType", cardType);
      res.put("title", obj.get("title"));
      res.put("getLimit", obj.get("get_limit"));
      switch (cardType) {
      case "GROUPON": // 团购券
        String detail = map.get("deal_detail").toString(); // 团购券专用，团购详情
        res.put("keys", new String[] { "detail" });
        res.put("detail", detail);
        break;
      case "DISCOUNT": // 折扣券
        int discount = Integer.parseInt(map.get("discount").toString()); // 折扣券专用，表示打折额度（百分比）。填30就是七折
        res.put("keys", new String[] { "discount" });
        res.put("discount", discount);
        break;
      case "GIFT": // 礼品券 兑换券
        String gift = map.get("gift").toString(); // 兑换券专用，填写兑换内容的名称
        res.put("keys", new String[] { "gift" });
        res.put("gift", gift);
        break;
      case "CASH": // 代金券
        int leastCost = Integer.parseInt(map.get("least_cost").toString()); // 起用金额
                                                                            // 分
        int reduceCost = Integer.parseInt(map.get("reduce_cost").toString()); // 减免金额
                                                                              // 分
        res.put("keys", new String[] { "leastCost", "reduceCost" });
        res.put("leastCost", leastCost);
        res.put("reduceCost", reduceCost);
        break;
      case "GENERAL_COUPON": // 通用券
        String general = map.get("default_detail").toString(); // 优惠券专用，填写优惠详情
        res.put("keys", new String[] { "general" });
        res.put("general", general);
        break;
      default:
        System.out.println("不支持的卡券类型");
        break;
      }
    }
    return res;
  }

  /**
   * 修改库存
   * 
   * @param cardId
   * @param num
   * @throws IOException
   */
  public void modifystock(String cardId, int num) throws IOException {
    String key = "increase_stock_value";
    if (num < 0) {
      key = "reduce_stock_value";
    }
    String data = "{\"card_id\": \"" + cardId + "\", \"" + key + "\": " + Math.abs(num) + "}";
    String temp = this.starOkHttpClient.postJson(String.format(modifystockUrl, getAccessToken()), data);
    System.out.println(temp);
  }

  public synchronized String getJsapiTicket() throws IOException {
    if (null == jsapiTicket || !jsapiTicket.isExpires()) {
      String accessToken = getAccessToken();
      // System.out.println(Thread.currentThread().getName() +
      // "请求微信。。JsapiTicket");
      String url = String.format(jsapiTicketUrl, accessToken);
      String res = this.starOkHttpClient.get(url);
      jsapiTicket = starJson.str2obj(res, JsapiTicket.class);
    }
    return jsapiTicket.getTicket();
  }

  public synchronized String getApiTicket() throws IOException {
    if (null == apiTicket || !apiTicket.isExpires()) {
      String accessToken = getAccessToken();
      // System.out.println(Thread.currentThread().getName() +
      // "请求微信。。JsapiTicket");
      String url = String.format(apiTicketUrl, accessToken);
      String res = this.starOkHttpClient.get(url);
      apiTicket = starJson.str2obj(res, ApiTicket.class);
    }
    return apiTicket.getTicket();
  }

  public WeixinConfig weixinConfig(String url) {
    try {
      String jsapiTicket = getJsapiTicket();
      String noncestr = UUID.randomUUID().toString().replace("-", "");
      long now = System.currentTimeMillis() / 1000;
      String string1 = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
      string1 = String.format(string1, jsapiTicket, noncestr, now, url);
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(string1.getBytes("UTF-8"));
      String signature = byteToHex(crypt.digest());
      WeixinConfig config = new WeixinConfig(weixinConfig.getAppId(), noncestr, now, signature);
      return config;
    } catch (IOException e) {
      log.error("", e);
    } catch (NoSuchAlgorithmException e) {
      log.error("", e);
    }
    return null;
  }

  private String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }
}

// class Temp implements Runnable{
//
// @Override
// public void run() {
// int len = 100;
// for (int i = 0; i < len; i++) {
// WeixinConfig config = WeiXinUtil.weixinConfig("http://m1.cnhnkj.cn");
// }
// }
// }

class ApiTicket {
  private Integer errcode;
  private String errmsg;
  private String ticket;
  private Long expires_in;
  private Long createTime = System.currentTimeMillis();

  /**
   * 是否有效 true 有效 false 无效
   * 
   * @return
   */
  public boolean isExpires() {
    if (createTime + expires_in * 1000 > System.currentTimeMillis()) {
      return true;
    }
    return false;
  }

  public Integer getErrcode() {
    return errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public String getTicket() {
    return ticket;
  }

  public Long getExpires_in() {
    return expires_in;
  }

  public void setErrcode(Integer errcode) {
    this.errcode = errcode;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public void setExpires_in(Long expires_in) {
    this.expires_in = expires_in;
  }
}

class JsapiTicket {
  private Integer errcode;
  private String errmsg;
  private String ticket;
  private Long expires_in;
  private Long createTime = System.currentTimeMillis();

  /**
   * 是否有效 true 有效 false 无效
   * 
   * @return
   */
  public boolean isExpires() {
    if (null != expires_in && createTime + expires_in * 1000 > System.currentTimeMillis()) {
      return true;
    }
    return false;
  }

  public Integer getErrcode() {
    return errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public String getTicket() {
    return ticket;
  }

  public Long getExpires_in() {
    return expires_in;
  }

  public void setErrcode(Integer errcode) {
    this.errcode = errcode;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public void setExpires_in(Long expires_in) {
    this.expires_in = expires_in;
  }
}

class AccessToken {
  private String access_token;
  private Long expires_in;
  private Long createTime = System.currentTimeMillis();

  /**
   * 是否有效 true 有效 false 无效
   * 
   * @return
   */
  public boolean isExpires() {
    if (null != expires_in && createTime + expires_in * 1000 > System.currentTimeMillis()) {
      return true;
    }
    return false;
  }

  public String getAccess_token() {
    return access_token;
  }

  public Long getExpires_in() {
    return expires_in;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public void setExpires_in(Long expires_in) {
    this.expires_in = expires_in;
  }
}
