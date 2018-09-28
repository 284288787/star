/**create by liuhua at 2018年9月25日 下午3:32:51**/
package com.star.truffle.module.weixin.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayDetailInfo {

  private Long id;
  //协议层
  private String return_code;
  private String return_msg;

  //协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回）
  private String appid;
  private String mch_id;
  private String nonce_str;
  private String sign;
  private String result_code; // SUCCESS/FAIL 
  private String err_code;
  private String err_code_des;

  private String device_info;

  //业务返回的具体数据（以下字段在return_code 和result_code 都为SUCCESS 的时候有返回）
  private String openid;
  private String is_subscribe;
  private String trade_type;
  private String bank_type;
  private Long total_fee;
  private String coupon_fee;
  private Integer coupon_count;
  private String fee_type;
  private String transaction_id;
  private String out_trade_no;
  private String attach;
  private String time_end;
  private Long cash_fee;
  private String cash_fee_type;
}
