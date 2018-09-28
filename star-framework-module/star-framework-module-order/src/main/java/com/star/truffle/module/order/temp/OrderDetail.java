/**create by liuhua at 2018年9月3日 下午2:33:06**/
package com.star.truffle.module.order.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.constant.DsType;

@StarDomainName(caption = "订单明细", createTable = true, tableName = "order_detail", listPage = false, addPage = false, editPage = false)
public class OrderDetail {

  @StarField(caption = "主键", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  private Long id;
  
  @StarField(caption = "订单ID", dsType = DsType.BIGINT, dsLength = 20)
  private Long orderId;
  
  @StarField(caption = "供应ID", dsType = DsType.BIGINT, dsLength = 20)
  private Long productId;
  
  @StarField(caption = "供应标题", dsType = DsType.VARCHAR, dsLength = 50)
  private String title;
  
  @StarField(caption = "供应主图", dsType = DsType.VARCHAR, dsLength = 200)
  private String mainPictureUrl;
  
  @StarField(caption = "供应原价", dsType = DsType.INT, dsLength = 10)
  private Integer originalPrice;     //原价
  
  @StarField(caption = "供应售价", dsType = DsType.INT, dsLength = 10)
  private Integer price;
  
  @StarField(caption = "单件提成", dsType = DsType.INT, dsLength = 10)
  private Integer brokerage;         //单件提成
  
  @StarField(caption = "提货时间", dsType = DsType.DATETIME, dsLength = 0)
  private Date pickupTime;           //提货时间
  
  @StarField(caption = "供应规格", dsType = DsType.VARCHAR, dsLength = 80)
  private String specification;      //规格 例如：1袋
  
  @StarField(caption = "购买数量", dsType = DsType.INT, dsLength = 5)
  private Integer count;             //数量
  
  @StarField(caption = "供应信息", dsType = DsType.LONGTEXT, dsLength = 0)
  private String productInfo;        //购买时供应的json
}
