/**create by liuhua at 2018年9月7日 上午9:57:21**/
package com.star.truffle.module.weixin.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.module.weixin.dao.WeiXinApiDao;
import com.star.truffle.module.weixin.domain.WeixinConfig;
import com.star.truffle.module.weixin.dto.res.WeixinUserInfo;

@Service
public class TicketService {

  @Autowired
  private WeiXinApiDao weiXinApiDao;

  public WeixinUserInfo getWeixinUserInfo(String code) throws IOException {
    return weiXinApiDao.getWeixinUserInfo(code);
  }

  private final String token = "nifury";
  public boolean checkSignature(String signature, String timestamp, String nonce) {
    String[] arr = new String[] { token, timestamp, nonce };
    // 排序
    Arrays.sort(arr);
    // 生成字符串
    StringBuffer content = new StringBuffer();
    for (int i = 0; i < arr.length; i++) {
      content.append(arr[i]);
    }
    // sha1加密
    String temp = getSha1(content.toString());
    return temp.equals(signature);
  }

  public static String getSha1(String str) {
    if (null == str || 0 == str.length()) {
      return null;
    }
    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    try {
      MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
      mdTemp.update(str.getBytes("UTF-8"));

      byte[] md = mdTemp.digest();
      int j = md.length;
      char[] buf = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
        buf[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(buf);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
  }

  public WeixinConfig jssdk(String url) {
    WeixinConfig config = this.weiXinApiDao.weixinConfig(url);
    return config;
  }
}
