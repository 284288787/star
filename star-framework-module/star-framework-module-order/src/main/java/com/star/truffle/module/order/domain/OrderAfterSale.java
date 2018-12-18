/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class OrderAfterSale {

  // 主键
  private Long id;
  // 订单ID
  private Long orderId;
  // 订单详情Id
  private Long detailId;
  // 售后类型 1退货 2换货
  private Integer type;
  // 需要处理的件数
  private Integer count;
  // 售后单号
  private String afterCode;
  // 申请备注
  private String remark;
  // 售后状态 1待处理 2通过 3不通过 4已寄件 5处理中 6完成 7已取消 8已删除
  // 0查询可以售后的列表
  private Integer state;
  // openid
  private String reason;
  // 创建日期
  private Date createTime;
  // 更新日期
  private Date updateTime;
  
  private String addressee;         //收件人
  private String addresseeMobile;   //收件人电话
  private String addresseeAddress;  //收件人地址
  private Date effectiveTime;       //寄件有效截止时间
  
  private String expressageCompany; //快递公司
  private String expressageNumber;  //快递单号
  private Date expressageTime;      //快递信息填写时间
}