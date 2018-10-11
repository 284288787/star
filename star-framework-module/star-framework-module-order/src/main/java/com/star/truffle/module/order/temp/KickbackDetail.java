/**create by liuhua at 2018年10月11日 上午9:57:30**/
package com.star.truffle.module.order.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;

@StarDomainName(caption = "提成明细", createTable = true, tableName = "kickback_detail", addPage = false, editPage = false)
public class KickbackDetail {

  private String id;
  private Long distributorId;       // 分销商id
  private Date pointTime;           // 提成计算日期节点
  private Integer totalMoney;       // 可提成总金额
  private Date createTime;          // 提成计算时间
  private Integer state;            // 状态： 1审核中 2汇款中 3未通过 4已完成
  private String reject;            // 未通过的原因
}
