/**create by liuhua at 2018年9月18日 上午10:33:06**/
package com.star.truffle.module.order.temp;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.constant.DsType;

@StarDomainName(caption = "收货地址", createTable = true, tableName = "delivery_address", listPage = false, addPage = false, editPage = false)
public class DeliveryAddress {

  @StarField(caption = "主键", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  private Long id;
  
  @StarField(caption = "用户ID", dsType = DsType.BIGINT, dsLength = 20)
  private Long memberId;
  
  @StarField(caption = "收件人", dsType = DsType.VARCHAR, dsLength = 10)
  private String name;
  
  @StarField(caption = "收件人电话", dsType = DsType.VARCHAR, dsLength = 11)
  private String mobile;
  
  @StarField(caption = "省", dsType = DsType.BIGINT, dsLength = 20)
  private Long provinceId;
  
  @StarField(caption = "市", dsType = DsType.BIGINT, dsLength = 20)
  private Long cityId;
  
  @StarField(caption = "区县", dsType = DsType.BIGINT, dsLength = 20)
  private Long areaId;
  
  @StarField(caption = "详细地址", dsType = DsType.VARCHAR, dsLength = 50)
  private String address;
  
  @StarField(caption = "是否默认", dsType = DsType.TINYINT, dsLength = 1)
  private Integer def;     //是否默认 1是 0否
}
