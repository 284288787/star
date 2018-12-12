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
  private String sign_type;   //1
  private String result_code; // SUCCESS/FAIL 
  private String err_code;
  private String err_code_des;

  private String device_info;

  //业务返回的具体数据（以下字段在return_code 和result_code 都为SUCCESS 的时候有返回）
  private String openid;
  private String is_subscribe;
  private String trade_type;
  private String bank_type;
  private Long total_fee;                  //订单总金额，单位为分
  private String coupon_fee;
  private Integer coupon_count;
  private String coupon_type_0;         //CASH
  private String coupon_type_1;         //NO_CASH
  private String coupon_id_0; 
  private String coupon_id_1; 
  private Integer coupon_fee_0; 
  private Integer coupon_fee_1; 
  private Integer settlement_total_fee; //1  应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
  private String fee_type;                  //货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
  private String transaction_id;
  private String out_trade_no;
  private String attach;
  private String time_end;
  private Long cash_fee;           //现金支付金额订单现金支付金额，详见支付金额
  private String cash_fee_type;
}
