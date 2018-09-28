/**create by liuhua at 2018年9月7日 上午9:56:24**/
package com.star.truffle.module.weixin.dto.res;

import lombok.Data;

@Data
public class WeixinUserInfo {

  private String access_token;
  private Integer expires_in;
  private String refresh_token;
  private String openid;
  private String scope;
  private String unionid;
  private String nickname;
  private String headimgurl;
  private Long createTime = System.currentTimeMillis();

//  public String toString() {
//    return "\topenId: " + openid + "\n\taccessToken: " + access_token + "\n\tsubscribe: " + subscribe;
//  }

  /**
   * ture 有效 false 无效
   * @return
   */
  public boolean isExpires() {
    if (null == expires_in || createTime + expires_in > System.currentTimeMillis()) {
      return false;
    }
    return true;
  }
}
