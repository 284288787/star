/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.res;

import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.OrderAfterSale;

@Getter
@Setter
public class OrderAfterSaleResponseDto extends OrderAfterSale {

  // 分销商姓名
  private String name;
  // 分销商手机号
  private String mobile;
  // 快递费 单位分
  private Integer despatchMoney;
  // 总金额 不包含运费
  private Integer totalMoney;
  // 总提成
  private Integer totalBrokerage;
}