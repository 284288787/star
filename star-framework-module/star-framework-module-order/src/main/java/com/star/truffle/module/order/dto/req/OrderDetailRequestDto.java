/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.dto.req;

import java.util.Date;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.OrderDetail;

@Getter
@Setter
public class OrderDetailRequestDto extends OrderDetail {

  private Page pager;
  
  private String states;
  private String transportStates;
  private Date beginCreateTime;
  private Date endCreateTime;
  private Long distributorId;
}