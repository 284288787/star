/**create by liuhua at 2018年10月25日 下午2:57:14**/
package com.star.truffle.module.member.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "分销商申请", createTable = true, tableName = "distributor_apply")
public class DistributorApply {

  @StarField(caption = "id", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long id;
  
  @StarField(caption = "手机号", dsType = DsType.VARCHAR, dsLength = 11)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String mobile;                  //手机号
  
  @StarField(caption = "姓名", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String name;                    //姓名
  
  @StarField(caption = "店铺名称", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private String shopName;                //店铺名称
  
  @StarField(caption = "身份证1", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldList(inputType = InputType.text)
  private String idCardPic1;              //身份证正面
  
  @StarField(caption = "身份证2", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldList(inputType = InputType.text)
  private String idCardPic2;              //反面
  
  @StarField(caption = "门店照片", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldList(inputType = InputType.text)
  private String shopPic;                 //门店照片
  
  @StarField(caption = "微信", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldList(inputType = InputType.text)
  private String weixinPic;               //微信二维码
  
  @StarField(caption = "省", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long provinceId;                //省id
  
  @StarField(caption = "省", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private String provinceName;            //省名字
  
  @StarField(caption = "市", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long cityId;                    //市id
  
  @StarField(caption = "市", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private String cityName;                //市名称
  
  @StarField(caption = "区县", dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long areaId;                    //区县id 
  
  @StarField(caption = "区县", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private String areaName;                //区县名称
  
  @StarField(caption = "详细地址", dsType = DsType.VARCHAR, dsLength = 100)
  @StarFieldList(inputType = InputType.text)
  private String address;                 //详细地址
  
  @StarField(caption = "营业执照", dsType = DsType.VARCHAR, dsLength = 100)
  @StarFieldList(inputType = InputType.text)
  private String businessLicense;         //营业执照名称+号码
  
  @StarField(caption = "营业执照", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldList(inputType = InputType.text)
  private String businessLicensePic;      //营业执照图片
  
  @StarField(caption = "食品流通许可证", dsType = DsType.VARCHAR, dsLength = 100)
  @StarFieldList(inputType = InputType.text)
  private String foodAllowanceLicense;    //食品流通许可证名称+号码
  
  @StarField(caption = "食品流通许可证", dsType = DsType.VARCHAR, dsLength = 200)
  @StarFieldList(inputType = InputType.text)
  private String foodAllowanceLicensePic; //食品流通许可证图片
  
  @StarField(caption = "营业面积", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private String acreage;                 //营业面积
  
  @StarField(caption = "银行名", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private String bankName;                //银行名
  
  @StarField(caption = "开户行", dsType = DsType.VARCHAR, dsLength = 60)
  @StarFieldList(inputType = InputType.text)
  private String bankAddress;             //开户行
  
  @StarField(caption = "开户名", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private String bankCardName;            //开户名
  
  @StarField(caption = "银行卡号", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldList(inputType = InputType.text)
  private String bankCardNo;              //卡号
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d H:i:s")
  private Date createTime;
  
  @StarField(caption = "openId", dsType = DsType.VARCHAR, dsLength = 40)
  @StarFieldList(inputType = InputType.text)
  private String openId;
}
