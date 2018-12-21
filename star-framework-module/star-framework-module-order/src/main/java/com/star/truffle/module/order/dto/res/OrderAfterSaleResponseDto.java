/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.res;

import java.util.Date;

import com.star.truffle.module.order.domain.OrderAfterSale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAfterSaleResponseDto extends OrderAfterSale {

  private Long productId;
  private String title;
  private String mainPictureUrl;
  private Integer price;
  private Integer brokerage;
  private Integer brokerageFirst;
  private Integer detailCount;
  private Integer days;
  private Date orderCreateTime;
  private Long distributorId;
  private String distributorName;
  private String distributorMobile;
  private Long memberId;
  private String memberName;
  private String memberMobile;
}