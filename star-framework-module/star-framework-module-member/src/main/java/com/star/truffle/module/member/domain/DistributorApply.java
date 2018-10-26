/**create by framework at 2018年10月26日 09:40:50**/
package com.star.truffle.module.member.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class DistributorApply {

  // id
  private Long id;
  // 手机号
  private String mobile;
  // 姓名
  private String name;
  // 店铺名称
  private String shopName;
  // 身份证1
  private String idCardPic1;
  // 身份证2
  private String idCardPic2;
  // 门店照片
  private String shopPic;
  // 微信
  private String weixinPic;
  // 省
  private Long provinceId;
  // 省
  private String provinceName;
  // 市
  private Long cityId;
  // 市
  private String cityName;
  // 区县
  private Long areaId;
  // 区县
  private String areaName;
  // 详细地址
  private String address;
  // 营业执照
  private String businessLicense;
  // 营业执照
  private String businessLicensePic;
  // 食品流通许可证
  private String foodAllowanceLicense;
  // 食品流通许可证
  private String foodAllowanceLicensePic;
  // 营业面积
  private String acreage;
  // 银行名
  private String bankName;
  // 开户行
  private String bankAddress;
  // 开户名
  private String bankCardName;
  // 银行卡号
  private String bankCardNo;
  // 创建日期
  private Date createTime;
  // openId
  private String openId;
}