/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class KickbackDetail {

  // 主键
  private Long id;
  // 分销商
  private Long distributorId;
  // 日期节点
  private Date pointTime;
  // 总金额
  private Integer totalMoney;
  // 创建日期
  private Date createTime;
  // 订单状态  1审核中 2汇款中 3未通过 4已完成
  private Integer state;
  // 未通过原因
  private String reject;
}