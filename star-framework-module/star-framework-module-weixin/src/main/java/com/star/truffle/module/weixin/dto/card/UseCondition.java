/**create by liuhua at 2019年1月24日 上午10:53:04**/
package com.star.truffle.module.weixin.dto.card;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UseCondition {

  //不可与其他优惠共享false 可与其他优惠共享true
  private Boolean canUseWithOtherDiscount;
  //购买xx可用类型门槛，仅用于兑换 ，填入后自动拼写购买xxx可用。
  private String objectUseFor;
  //满减门槛字段，可用于兑换券和代金券 ，填入后将在全面拼写消费满xx元可用。
  private Integer leastCost;
  
  //指定可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写适用于xxx
  private String acceptCategory;
  //指定不可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写不适用于xxxx
  private String rejectCategory;
}
